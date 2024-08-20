package com.coffee_shop.repository;

import com.coffee_shop.domain.Order;
import com.coffee_shop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByIdAndUser(Long id, User user);
}
