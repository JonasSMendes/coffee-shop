package com.coffee_shop.repository;

import com.coffee_shop.domain.ItemMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemMenuRepository extends JpaRepository<ItemMenu , Long> {
}
