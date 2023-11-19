package com.skuplate.server.menu.repository;

import com.skuplate.server.menu.entity.Menu;
import com.skuplate.server.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    public List<Menu> findAllByRestaurant(Restaurant restaurant);

    public Menu findById(long theId);
}
