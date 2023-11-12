package com.skuplate.server.bookmark.repository;

import com.skuplate.server.bookmark.entity.Bookmark;
import com.skuplate.server.member.entity.Member;
import com.skuplate.server.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    @Transactional
    void deleteByMemberAndRestaurant(Member member, Restaurant restaurant);

    List<Restaurant> findByMember(Member member);
}
