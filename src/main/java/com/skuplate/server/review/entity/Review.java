package com.skuplate.server.review.entity;

import com.skuplate.server.audit.Auditable;
import com.skuplate.server.member.entity.Member;
import com.skuplate.server.restaurant.entity.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String content;

    private String image;

    private long starRate;

    @ManyToOne
    @JoinColumn(name = "restaurant_id") // review_id는 restaurant 테이블의 PK에 대한 FK로 연결
    private Restaurant restaurant;
}
