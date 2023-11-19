package com.skuplate.server.menu.dto;

import com.skuplate.server.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class MenuListDto {
    public String category;
    public String categoryImage;
    public List<MenuDto> items;

}
