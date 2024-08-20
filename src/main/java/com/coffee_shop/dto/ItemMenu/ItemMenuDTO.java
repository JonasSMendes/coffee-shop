package com.coffee_shop.dto.ItemMenu;

import com.coffee_shop.domain.ItemMenu;

public record ItemMenuDTO(String name, String description, double price, String image) {
    public ItemMenuDTO(ItemMenu itemMenu) {
        this(
                itemMenu.getName(),
                itemMenu.getDescription(),
                itemMenu.getPrice(),
                itemMenu.getImage()
        );
    }
}
