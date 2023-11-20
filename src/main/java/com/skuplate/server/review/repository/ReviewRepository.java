package com.skuplate.server.review.repository;

import com.skuplate.server.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByRestaurantId(Long restaurantId);

    List<Review> findByMemberId(Long memberId);

}
