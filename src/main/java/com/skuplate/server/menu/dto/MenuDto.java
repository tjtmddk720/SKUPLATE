package com.skuplate.server.menu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MenuDto {
    @JsonIgnore
    private String CategoryForMenu;
    private String name;
    private int price;
}
