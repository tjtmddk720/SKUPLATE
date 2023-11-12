package com.skuplate.server.review.controller;


import com.skuplate.server.review.entity.Review;
import com.skuplate.server.review.form.ReviewForm;
import com.skuplate.server.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/review")
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping("/reviews")
    public ResponseEntity<String> reviewCreate(@Valid @RequestBody ReviewForm reviewForm){
        try {
            Review review = new Review();
            reviewService.createReview(reviewForm.getStarRate(), reviewForm.getContent(), review.getImage());
            return ResponseEntity.ok("Review created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating review: " + e.getMessage());
        }
    }
    // 멤버 id기준 리뷰 조회
    @GetMapping("/member/{id}")
    public ResponseEntity<List<Review>> reviewFindByMemberIdList(@PathVariable Long id) {
        List<Review> reviews = reviewService.findByMemberId(id);
        return ResponseEntity.ok(reviews);
    }
    // 레스토랑 id기준 리뷰 조회
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<Review>> reviewFindByRestaurantIdList(@PathVariable Long id) {
        List<Review> reviews = reviewService.findByRestaurantId(id);
        return ResponseEntity.ok(reviews);

    }
    // 리뷰 id기준 조회
    @GetMapping("/reviews/{id}")
    public ResponseEntity<Review> reviewFindById(@PathVariable Long id){
        Review review = reviewService.findById(id);
        if (review != null) {
            return ResponseEntity.ok(review);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // 리뷰 수정
    @PatchMapping("/reviews/{id}")
    public ResponseEntity<String> reviewModify(@PathVariable Long id, @RequestBody ReviewForm reviewForm) {
        try {
            Review review = new Review();
            reviewService.modifyReview(id, reviewForm.getContent(),reviewForm.getStarRate(), review.getImage());
            return ResponseEntity.ok("Review modified successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error modifying review: " + e.getMessage());
        }
    }
    // 리뷰 삭제
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<String> reviewDelete(@PathVariable Long id) {
        try {
            reviewService.deleteReview(id);
            return ResponseEntity.ok("Review deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting review: " + e.getMessage());
        }
    }
}



