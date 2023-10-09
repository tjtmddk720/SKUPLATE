package com.skuplate.server.bookmark.entity;

import com.skuplate.server.restaurant.entity.Restaurant;
import com.skuplate.server.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookmarkId;

    @OneToOne
    @JoinColumn(name = "RESTAURANT_ID") // restaurant_id는 실제 데이터베이스에서 해당 관계를 나타내는 외래 키 컬럼의 이름입니다.
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
