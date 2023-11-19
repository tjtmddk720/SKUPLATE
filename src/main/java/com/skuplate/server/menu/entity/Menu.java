package com.skuplate.server.menu.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skuplate.server.restaurant.entity.Restaurant;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@ApiModel(value = "메뉴 정보")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    private String mainName;
    private int cost;
    private String image;
    private String sideName;

    @JsonIgnore
    private String category;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;

    @JsonIgnore
    private String categoryForMenu;
}
