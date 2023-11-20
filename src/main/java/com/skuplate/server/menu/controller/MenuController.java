package com.skuplate.server.menu.controller;

import com.skuplate.server.menu.dto.MenuListDto;
import com.skuplate.server.menu.entity.Menu;
import com.skuplate.server.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/restaurant/{theId}/menu")
    public List<MenuListDto> GetAllMenuByRestaurant(@PathVariable long theId){
        return menuService.findAllMenuByRestaurantId(theId);

    }

    @GetMapping("/menu/{theId}")
    public Menu GetMenuById(@PathVariable long theId){
        return menuService.findMenuById(theId);
    }
}
