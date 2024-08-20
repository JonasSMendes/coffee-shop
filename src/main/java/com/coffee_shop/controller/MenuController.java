package com.coffee_shop.controller;

import com.coffee_shop.domain.ItemMenu;
import com.coffee_shop.dto.ItemMenu.ItemMenuDTO;
import com.coffee_shop.repository.ItemMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/item")
public class MenuController {

    @Autowired
    ItemMenuRepository itemMenuRepository;

    @GetMapping
    public String pong (){
        return "pong";
    }

    @PostMapping("/upload")
    public ResponseEntity upload (@RequestBody ItemMenuDTO dto){

            ItemMenu itemMenu = new ItemMenu();
            itemMenu.setName(dto.name());
            itemMenu.setDescription(dto.description());
            itemMenu.setPrice(dto.price());
            itemMenu.setImage(dto.image());


            ItemMenuDTO itemMenuDTO = new ItemMenuDTO(itemMenu);

            itemMenuRepository.save(itemMenu);

            return ResponseEntity.ok(new ItemMenuDTO(itemMenu));
    }


    @GetMapping("/list")
    public ResponseEntity<List<ItemMenuDTO>> itemList (){
        List<ItemMenu> listMenu = itemMenuRepository.findAll();

        List<ItemMenuDTO> dtoList = listMenu
                .stream()
                .map(e -> new ItemMenuDTO(e))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }
}
