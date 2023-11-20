package com.skuplate.server.bookmark.controller;

import com.skuplate.server.bookmark.service.BookmarkService;
import com.skuplate.server.restaurant.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookmark")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    // 즐겨찾기 생성
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/bookmarks")
    public ResponseEntity<Void> bookmarkAdd(@RequestParam Long memberId, @RequestParam Long restaurantId) {
        bookmarkService.addBookmark(memberId, restaurantId);
        return ResponseEntity.ok().build();
    }

    // 즐겨찾기 삭제
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{memberId}/{restaurantId}")
    public ResponseEntity<Void> bookmarkDelete(@PathVariable Long memberId, @PathVariable Long restaurantId) {
        bookmarkService.removeBookmark(memberId, restaurantId);
        return ResponseEntity.ok().build();
    }

    // 즐겨찾기 조회
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/member/{id}")
    public ResponseEntity<List<Restaurant>> findByMemberId(@PathVariable Long memberId) {
        List<Restaurant> bookmarkedRestaurants = bookmarkService.getBookmarkedRestaurantsByMemberId(memberId);
        return ResponseEntity.ok(bookmarkedRestaurants);
    }
}
