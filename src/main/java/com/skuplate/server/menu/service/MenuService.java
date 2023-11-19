package com.skuplate.server.menu.service;

import com.skuplate.server.menu.dto.MenuDto;
import com.skuplate.server.menu.dto.MenuListDto;
import com.skuplate.server.menu.entity.Menu;
import com.skuplate.server.menu.repository.MenuRepository;
import com.skuplate.server.restaurant.dto.RestaurantDto;
import com.skuplate.server.restaurant.entity.Restaurant;
import com.skuplate.server.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public List<MenuListDto> findAllMenuByRestaurantId(long theId){
        // 모든 메뉴를 저장해두는 리스트
        List<Menu> AllMenu = menuRepository.findAllByRestaurant(restaurantRepository.findById(theId));
        // 이름과 가격만을 가지고 있는 dto로 변환
        List<MenuDto> menuDtoList = new ArrayList<>();
        AllMenu.forEach(menu -> {
            menuDtoList.add(MenuDto.builder().CategoryForMenu(menu.getCategoryForMenu()).name(menu.getMainName()).price(menu.getCost()).build());
        });
        // 메뉴 카테고리들을 저장해두는 리스트(ex. 전체메뉴, 인기메뉴 등등)
        List<String> menuCategory = new ArrayList<>();
        // MenuListDto에 들어갈 items를 저장해두는 리스트
        List<List<MenuDto>> items_in_MenuListDto = new ArrayList<>();
        // 최종 결과
        List<MenuListDto> menuListDtos = new ArrayList<>();

        // 모든 카테고리들을 수집하는 for문
        for(int i = 0; i < menuDtoList.size(); i++){
            // menuCategory에 없던 원소라면, 추가해야 됨
            if(!menuCategory.contains(menuDtoList.get(i).getCategoryForMenu())){
                menuCategory.add(menuDtoList.get(i).getCategoryForMenu());
            }
        }

        // i는 메뉴 카테고리, j는 모든 메뉴를 순회하는 변수
        for(int i = 0; i < menuCategory.size(); i++){
            List<MenuDto> temp = new ArrayList<>();
            for(int j = 0; j < menuDtoList.size(); j++){
                if(Objects.equals(menuDtoList.get(j).getCategoryForMenu(), menuCategory.get(i))){
                    temp.add(menuDtoList.get(j));
                }
            }
            items_in_MenuListDto.add(temp);
        }

        for(int i = 0; i < items_in_MenuListDto.size(); i++){
            MenuListDto temp = MenuListDto.builder().category(items_in_MenuListDto.get(i).get(0).getCategoryForMenu()).categoryImage("temp").items(items_in_MenuListDto.get(i)).build();
            menuListDtos.add(temp);
        }

        return menuListDtos;
    }

    public Menu findMenuById(long theId){
        return menuRepository.findById(theId);
    }
}
