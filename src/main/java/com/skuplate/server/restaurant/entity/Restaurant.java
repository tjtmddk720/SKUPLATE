package com.skuplate.server.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skuplate.server.bookmark.entity.Bookmark;
import com.skuplate.server.menu.entity.Menu;
import com.skuplate.server.review.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.REMOVE)
    private List<Review> starRateList = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.REMOVE)
    private List<Menu> menuList = new ArrayList<>();

    @OneToOne(mappedBy = "restaurant",cascade = CascadeType.REMOVE)
    private Bookmark bookmark;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE)
    private List<Review> reviewList = new ArrayList<>();

    private String name;
    private String image;
    private Long viewCount;
    private String content;
    private float starRateAvg;
    private boolean reservation;
    private boolean wifi;
    private boolean parking;
    private boolean packaging;
    private String phoneNumber;
    private String time;

    @Enumerated(value = EnumType.STRING)
    private menuCategory menuCategory;
    public enum menuCategory {
        KOREA("한식"),
        CHINA("중식"),
        JAPAN("일식"),
        WESTON("양식"),
        ETC("기타"),
        DESSERT("디저트"),
        PUB("술집");
        private String category;
        menuCategory(String category){
            this.category = category;
        }
        public String getCategory(){
            return category;
        }
    }

}