package com.skuplate.server.review.entity;

import com.skuplate.server.audit.Auditable;
import com.skuplate.server.member.entity.Member;
import com.skuplate.server.restaurant.entity.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String content;

    private String image;

    @NotNull(message = "starRate must not be null")
    @Min(value = 1, message = "starRate must be greater than or equal to 1")
    @Max(value = 5, message = "starRate must be less than or equal to 5")
    private long starRate;

    @ManyToOne
    @JoinColumn(name = "restaurant_id") // review_id는 restaurant 테이블의 PK에 대한 FK로 연결
    private Restaurant restaurant;
}
