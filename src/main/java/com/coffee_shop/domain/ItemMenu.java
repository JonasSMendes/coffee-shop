package com.coffee_shop.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "item_menu")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;

    @Lob
    private String image; // campo para armazenar a imagem


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemMenu itemMenu = (ItemMenu) o;
        return Objects.equals(id, itemMenu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
