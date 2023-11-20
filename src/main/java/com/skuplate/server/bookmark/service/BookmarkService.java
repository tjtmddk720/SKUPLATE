package com.skuplate.server.bookmark.service;

import com.skuplate.server.DataNotFoundException;
import com.skuplate.server.bookmark.entity.Bookmark;
import com.skuplate.server.bookmark.repository.BookmarkRepository;
import com.skuplate.server.member.entity.Member;
import com.skuplate.server.member.repository.MemberRepository;
import com.skuplate.server.restaurant.entity.Restaurant;
import com.skuplate.server.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;

    // 즐겨찾기 추가
    @Transactional
    public void addBookmark(Long memberId, Long restaurantId) {
        Member member = new Member();
        member.setMemberId(memberId);

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new DataNotFoundException("Restaurant not found with id: " + restaurantId));

        Bookmark bookmark = new Bookmark();
        bookmark.setMember(member);
        bookmark.setRestaurant(restaurant);

        bookmarkRepository.save(bookmark);
    }
    // 즐겨찾기 삭제
    @Transactional
    public void removeBookmark(Long memberId, Long restaurantId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new DataNotFoundException("Member not found with id: " + memberId));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new DataNotFoundException("Restaurant not found with id: " + restaurantId));

        bookmarkRepository.deleteByMemberAndRestaurant(member, restaurant);
    }

    // 즐겨찾기 멤버id기준으로 리스트식당 조회
    @Transactional(readOnly = true)
    public List<Restaurant> getBookmarkedRestaurantsByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new DataNotFoundException("Member not found with id: " + memberId));

        return bookmarkRepository.findByMember(member);
    }

}
