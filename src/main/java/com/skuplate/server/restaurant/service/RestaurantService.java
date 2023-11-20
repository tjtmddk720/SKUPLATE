package com.skuplate.server.restaurant.service;

import com.skuplate.server.menu.dto.MenuListDto;
import com.skuplate.server.menu.entity.Menu;
import com.skuplate.server.restaurant.dto.RestaurantDetailDto;
import com.skuplate.server.restaurant.dto.RestaurantDto;
import com.skuplate.server.restaurant.entity.Restaurant;
import com.skuplate.server.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public void saveRestaurant(Restaurant theRestaurant){
        restaurantRepository.save(theRestaurant);
    }
    
    // 모든 식당 찾기
    public List<RestaurantDto> GetAllRestaurant(){
        List<Restaurant> allRestaurants = restaurantRepository.findAll();
        List<RestaurantDto> restaurantDtoList = new ArrayList<>();
        allRestaurants.forEach(restaurant -> {
            restaurantDtoList.add(RestaurantDto.builder().restaurantId(restaurant.getId()).image(restaurant.getImage()).restaurantName(restaurant.getName()).menuCategory(restaurant.getMenuCategory()).build());
        });
        return restaurantDtoList;
    }

    // 식당 분류에 따라 식당 찾기(한식, 중식, 양식, 일식 등)
    public List<RestaurantDto> GetAllRestaurantByCategory(String theCategory){
        List<Restaurant> allRestaurants = restaurantRepository.findAllByMenuCategory(theCategory);
        List<RestaurantDto> restaurantDtoList = new ArrayList<>();
        allRestaurants.forEach(restaurant -> {
            restaurantDtoList.add(RestaurantDto.builder().restaurantId(restaurant.getId()).image(restaurant.getImage()).restaurantName(restaurant.getName()).menuCategory(restaurant.getMenuCategory()).build());
        });
        return restaurantDtoList;
    }
    
    // ID 기반으로 식당 찾기(조회수 증가 -> Transactional)
    @Transactional
    public Restaurant GetRestaurantById(long theId){
        restaurantRepository.findById(theId).setViewCount(restaurantRepository.findById(theId).getViewCount() + 1);
        return restaurantRepository.findById(theId);

    }

    // ID 기반으로 식당 찾아서, detailDto의 형태로 넘겨주기
    public RestaurantDetailDto GetRestaurantDetailById(long theId){
        RestaurantDetailDto restaurantDetailDto = new RestaurantDetailDto(restaurantRepository.findById(theId));
//        List<MenuListDto> menuListDtos;
//        menuListDtos.add()
//        restaurantDetailDto.setMenu(MenuListDto.builder()..build());
        return restaurantDetailDto;
    }
}
