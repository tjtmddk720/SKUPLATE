package com.skuplate.server.restaurant.dto;

import com.skuplate.server.restaurant.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

// 메인페이지, 음식카테고리 페이지에서 보이는 레스토랑 정보
@Data
@AllArgsConstructor
@Builder
public class RestaurantDto {
    public long restaurantId;
    public String image;
    public String restaurantName;
    public Restaurant.menuCategory menuCategory;

    public RestaurantDto(Restaurant theRestaurant){
        this.restaurantId = theRestaurant.getId();
        this.image = theRestaurant.getImage();
        this.restaurantName = theRestaurant.getName();
        this.menuCategory = theRestaurant.getMenuCategory();
    }

}
