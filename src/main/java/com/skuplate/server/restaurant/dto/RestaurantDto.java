package com.skuplate.server.restaurant.dto;

import com.skuplate.server.restaurant.entity.Restaurant;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

// 메인페이지, 음식카테고리 페이지에서 보이는 레스토랑 정보
@ApiModel(value = "메인페이지, 음식카테고리 페이지(레스토랑이 리스트 형태로 존재하는 페이지)에서 보이는 레스토랑 정보")
@Data
@AllArgsConstructor
@Builder
public class RestaurantDto {
    public long restaurantId;
    public String image;
    public String restaurantName;
    public Restaurant.menuCategory menuCategory;

    public RestaurantDto(Restaurant theRestaurant){
        this.restaurantId = theRestaurant.getRestaurantId();
        this.image = theRestaurant.getImage();
        this.restaurantName = theRestaurant.getName();
        this.menuCategory = theRestaurant.getMenuCategory();
    }


}
