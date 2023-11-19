package com.skuplate.server.restaurant.repository;

import com.skuplate.server.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    public Restaurant findById(long theId);
    public List<Restaurant> findAllByMenuCategory(String category);
}
