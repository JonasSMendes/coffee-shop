package com.coffee_shop.dto.OrderMenu;

import com.coffee_shop.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public record UserOrderGetDTO(List<OrderDTO> orders, double value) {
    public UserOrderGetDTO(Optional<User> newUser, double value) {
        this(
                newUser.get().getOrderList()
                        .stream()
                        .map(OrderDTO::new)
                        .collect(Collectors.toList()),

                value
        );
    }
}
