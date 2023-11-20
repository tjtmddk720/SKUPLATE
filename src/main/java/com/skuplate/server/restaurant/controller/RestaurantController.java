package com.skuplate.server.restaurant.controller;

import com.skuplate.server.restaurant.dto.RestaurantDetailDto;
import com.skuplate.server.restaurant.dto.RestaurantDto;
import com.skuplate.server.restaurant.entity.Restaurant;
import com.skuplate.server.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    // 식당 상세정보 조회
    @GetMapping("/restaurant/{theId}")
    public Restaurant getRestaurantById(@PathVariable long theId){
//        RestaurantDetailDto theRestaurantDetailDto;
        return restaurantService.GetRestaurantById(theId);
    }

    @GetMapping("/restaurant/{theId}/info")
    public RestaurantDetailDto getRestaurantInfoById(@PathVariable long theId){
        return restaurantService.GetRestaurantDetailById(theId);
    }

    @GetMapping("/restaurant")
    public List<RestaurantDto> getAllRestaurant(){
        return restaurantService.GetAllRestaurant();
    }

    @GetMapping("/restaurant/{category}")
    public List<RestaurantDto> getAllRestaurantByCategory(@PathVariable String theCategory){
        return restaurantService.GetAllRestaurantByCategory(theCategory);
    }

    @PostMapping("/restaurant/add")
    public void addRestaurant(Restaurant theRestaurant){
        restaurantService.saveRestaurant(theRestaurant);
    }
}
