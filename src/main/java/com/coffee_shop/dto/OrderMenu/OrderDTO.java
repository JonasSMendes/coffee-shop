package com.coffee_shop.dto.OrderMenu;
import com.coffee_shop.domain.Order;

public record OrderDTO(
                       String itemMenuName,
                       String itemMenuImage,
                       String itemMenuDescription,
                       double price) {

    public OrderDTO(Order order) {
        this(
                order.getItemMenu().getName(),
                order.getItemMenu().getImage(),
                order.getItemMenu().getDescription(),
                order.getItemMenu().getPrice()
        );
    }


}


