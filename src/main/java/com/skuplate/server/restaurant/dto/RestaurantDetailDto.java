package com.skuplate.server.restaurant.dto;

import com.skuplate.server.menu.dto.MenuListDto;
import com.skuplate.server.menu.entity.Menu;
import com.skuplate.server.restaurant.entity.Restaurant;
import com.skuplate.server.restaurant.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// 레스토랑 상세 정보에 들어갔을때 나오는 정보들
@Data
@AllArgsConstructor
@Builder
public class RestaurantDetailDto {
    public long restaurantId;
    public String image;
    public String restaurantName;
    public Restaurant.menuCategory menuCategory;

    public String content;
    public float starRateAvg;
    public boolean reservation;
    public boolean wifi;
    public boolean parking;
    public boolean packaging;
    public String phoneNumber;
    public String time;

    public List<MenuListDto> menu;


    public RestaurantDetailDto(Restaurant theRestaurant){
        this.restaurantId = theRestaurant.getRestaurantId();
        this.image = theRestaurant.getImage();
        this.restaurantName = theRestaurant.getName();
        this.menuCategory = theRestaurant.getMenuCategory();
        this.content = theRestaurant.getContent();
        this.starRateAvg = theRestaurant.getStarRateAvg();
        this.reservation = theRestaurant.isReservation();
        this.wifi = theRestaurant.isWifi();
        this.parking = theRestaurant.isParking();
        this.packaging = theRestaurant.isPackaging();
        this.phoneNumber = theRestaurant.getPhoneNumber();
        this.time = theRestaurant.getTime();
    }
}
