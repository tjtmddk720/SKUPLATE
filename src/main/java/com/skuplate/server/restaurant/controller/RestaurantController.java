package com.skuplate.server.restaurant.controller;

import com.skuplate.server.restaurant.dto.RestaurantDetailDto;
import com.skuplate.server.restaurant.dto.RestaurantDto;
import com.skuplate.server.restaurant.entity.Restaurant;
import com.skuplate.server.restaurant.service.RestaurantService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "식당 정보 조회")
    @GetMapping("/restaurant/{theId}")
    public Restaurant getRestaurantById(@PathVariable long theId){
//        RestaurantDetailDto theRestaurantDetailDto;
        return restaurantService.GetRestaurantById(theId);
    }

    @ApiOperation(value = "식당 정보 탭 조회")
    @GetMapping("/restaurant/{theId}/info")
    public RestaurantDetailDto getRestaurantInfoById(@PathVariable long theId){
        return restaurantService.GetRestaurantDetailById(theId);
    }

    @ApiOperation(value = "모든 레스토랑 조회")
    @GetMapping("/restaurant")
    public List<RestaurantDto> getAllRestaurant(){
        return restaurantService.GetAllRestaurant();
    }

    @ApiOperation(value = "카테고리별 레스토랑 조회")
    @GetMapping("/restaurant/{category}")
    public List<RestaurantDto> getAllRestaurantByCategory(@PathVariable String theCategory){
        return restaurantService.GetAllRestaurantByCategory(theCategory);
    }

    @PostMapping("/restaurant/add")
    public void addRestaurant(Restaurant theRestaurant){
        restaurantService.saveRestaurant(theRestaurant);
    }
}
