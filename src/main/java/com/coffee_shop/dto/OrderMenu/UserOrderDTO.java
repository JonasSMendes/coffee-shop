package com.coffee_shop.dto.OrderMenu;

import com.coffee_shop.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public record UserOrderDTO(String userId, String userName, List<OrderDTO> orders, double value) {

    public UserOrderDTO(User user, double value) {
        this(
                user.getId().toString(),
                user.getName(),
                user.getOrderList()
                        .stream()
                        .map(OrderDTO::new)
                        .collect(Collectors.toList()),
                value
        );
    }

    public UserOrderDTO(Optional<User> newUser, double value) {
        this(
            newUser.get().getId().toString(),
            newUser.get().getName(),
            newUser.get().getOrderList()
                    .stream()
                    .map(OrderDTO::new)
                    .collect(Collectors.toList()),
            value
        );
    }
}
