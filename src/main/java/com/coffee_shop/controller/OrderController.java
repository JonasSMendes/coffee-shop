package com.coffee_shop.controller;

import com.coffee_shop.domain.ItemMenu;
import com.coffee_shop.domain.Order;
import com.coffee_shop.domain.User;
import com.coffee_shop.dto.OrderMenu.UserOrderDTO;
import com.coffee_shop.repository.ItemMenuRepository;
import com.coffee_shop.repository.OrderRepository;
import com.coffee_shop.repository.UserRepository;
import com.coffee_shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemMenuRepository itemMenuRepository;

    @Autowired
    OrderService orderService;

    @PostMapping("/{id}")
    public ResponseEntity order (@PathVariable ItemMenu id){
        Optional<ItemMenu> optionalItemMenu  = itemMenuRepository.findById(id.getId());

        if (optionalItemMenu.isEmpty() ){
            throw new RuntimeException("Item-menu not found");
        }

        ItemMenu itemMenu = optionalItemMenu.get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user =  (User) authentication.getPrincipal();

        double somaPrice = orderService.addItemToOrder(itemMenu.getPrice());

        Order order = new Order();
        order.setItemMenu(itemMenu);
        order.setUser(user);
        order.setValue(somaPrice);

        orderRepository.save(order);
        UserOrderDTO userOrderDTO = new UserOrderDTO(user, somaPrice);


        return ResponseEntity.status(HttpStatus.CREATED).body(userOrderDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<UserOrderDTO> listOrder (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user =  (User) authentication.getPrincipal();

        Optional<User> newUser = userRepository.findByEmail(user.getEmail());


        if (newUser.isEmpty()){
            throw new RuntimeException("User not fund");
        }

        Order order = new Order();
        order.setUser(newUser.get());

        double price = orderService.addItemToOrder(order.getValue());
        System.out.println("price: " + price);

        UserOrderDTO userOrderDTO = new UserOrderDTO(newUser, price);

        return ResponseEntity.status(HttpStatus.OK).body(userOrderDTO);
    }


    @DeleteMapping("/remove/{id}")
    public ResponseEntity remove(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user =  (User) authentication.getPrincipal();

        Optional<Order> orderOptional = orderRepository.findByIdAndUser(id, user);

        if (orderOptional.isEmpty()){
            throw new RuntimeException("order not found");
        }

        Order order = orderOptional.get();

        orderService.remove(order.getItemMenu().getPrice());
        orderRepository.delete(order);

        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
