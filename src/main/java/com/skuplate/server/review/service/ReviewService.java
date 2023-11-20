package com.skuplate.server.review.service;


import com.skuplate.server.DataNotFoundException;
import com.skuplate.server.member.entity.Member;
import com.skuplate.server.review.entity.Review;
import com.skuplate.server.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // 식당 id기준으로 리뷰 리스트 조회 필요
    public List<Review> findByRestaurantId(Long restaurantId){
        return reviewRepository.findByRestaurantId(restaurantId);
    }

    // 멤버 id기준으로 리뷰 리스트 조회 필요
    public List<Review> findByMemberId(Long memberId){
        return reviewRepository.findByMemberId(memberId);
    }
    // id로 리뷰 조회
    public Review findById(Long reviewId){
        Optional<Review> review = this.reviewRepository.findById(reviewId);
        if(review.isPresent()){
            return review.get();
        }
        else {
            throw new DataNotFoundException("Review not found");
        }
    }

    //전체 리뷰 조회
    public List<Review> findReview(){
        return this.reviewRepository.findAll();
    }

    //리뷰 작성
    public void createReview(long starRate, String content, String image, Member member){
        Review review = new Review();

        review.setStarRate(starRate);
        review.setContent(content);
        review.setImage(image);
        review.setCreatedAt(LocalDateTime.now());
        review.setMember(member);
        this.reviewRepository.save(review);
    }

    // 리뷰 수정
    public void modifyReview(Review review,String content, long starRate, String image){

        review.setStarRate(starRate);
        review.setContent(content);
        review.setImage(image);
        review.setModifiedAt(LocalDateTime.now());
        this.reviewRepository.save(review);
    }

    // 리뷰 삭제
    public void deleteReview(Long reviewId){
        this.reviewRepository.deleteById(reviewId);
    }


}
