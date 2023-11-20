package com.skuplate.server.review.controller;


import com.skuplate.server.member.entity.Member;
import com.skuplate.server.member.service.MemberService;
import com.skuplate.server.review.entity.Review;
import com.skuplate.server.review.form.ReviewForm;
import com.skuplate.server.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequestMapping("/review")
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final MemberService memberService;

    // 리뷰 생성
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/reviews")
    public ResponseEntity<String> reviewCreate(@Valid @RequestBody ReviewForm reviewForm, Principal principal){
        try {
            Review review = new Review();
            Member member = this.memberService.getName(principal.getName());
            this.reviewService.createReview(reviewForm.getStarRate(), reviewForm.getContent(), review.getImage(), member);
            return ResponseEntity.ok("Review created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating review: " + e.getMessage());
        }
    }
    // 멤버 id기준 리뷰 조회

    @GetMapping("/member/{id}")
    public ResponseEntity<List<Review>> reviewFindByMemberIdList(@PathVariable Long id) {
        List<Review> reviews = this.reviewService.findByMemberId(id);

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
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/reviews/{id}")
    public ResponseEntity<String> reviewModify(@PathVariable("id") Long id, @RequestBody ReviewForm reviewForm,
                                               Principal principal) {
        try {
            Review review = this.reviewService.findById(id);
            if(!review.getMember().getName().equals(principal.getName())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
            }
            reviewService.modifyReview(review, reviewForm.getContent(),reviewForm.getStarRate(), review.getImage());
            return ResponseEntity.ok("Review modified successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error modifying review: " + e.getMessage());
        }
    }
    // 리뷰 삭제
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<String> reviewDelete(@PathVariable("id") Long id, Principal principal) {
        try {
            Review review = this.reviewService.findById(id);
            if(!review.getMember().getName().equals(principal.getName())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
            }
            this.reviewService.deleteReview(id);
            return ResponseEntity.ok("Review deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting review: " + e.getMessage());
        }
    }
}



