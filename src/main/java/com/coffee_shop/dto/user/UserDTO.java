package com.coffee_shop.dto.user;

import com.coffee_shop.domain.User;

public record UserDTO(String name, String email) {
    public UserDTO(User user) {
        this(user.getName(), user.getEmail());
    }
}
