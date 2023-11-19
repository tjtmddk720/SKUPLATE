package com.skuplate.server.menu.controller;

import com.skuplate.server.menu.dto.MenuListDto;
import com.skuplate.server.menu.entity.Menu;
import com.skuplate.server.menu.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "메뉴관련 컨트롤러")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @ApiOperation(value = "레스토랑의 모든 메뉴 조회")
    @GetMapping("/restaurant/{theId}/menu")
    public List<MenuListDto> GetAllMenuByRestaurant(@PathVariable long theId){
        return menuService.findAllMenuByRestaurantId(theId);

    }

    @ApiOperation(value = "ID 기반으로 단일 메뉴 조회")
    @GetMapping("/menu/{theId}")
    public Menu GetMenuById(@PathVariable long theId){
        return menuService.findMenuById(theId);
    }
}
