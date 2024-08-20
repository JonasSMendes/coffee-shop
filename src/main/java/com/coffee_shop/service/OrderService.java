package com.coffee_shop.service;

import com.coffee_shop.domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private HttpSession httpSession;

    public double addItemToOrder(double itemMenu){
        Double somaPrice = (Double) httpSession.getAttribute("somaPrice");

        if (somaPrice == null){
            somaPrice = 0.0;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user =  (User) authentication.getPrincipal();


        if (user.getOrderList().isEmpty()){
            somaPrice = 0.0;
        }

        somaPrice += itemMenu;
        httpSession.setAttribute("somaPrice", somaPrice);

        return somaPrice;
     }

     public double remove(double itemMenu){
         Double subPrice = (Double) httpSession.getAttribute("subPrice");

         if (subPrice == null){
             subPrice = 0.0;
         }

         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         User user =  (User) authentication.getPrincipal();

         if (user.getOrderList().isEmpty()){
             subPrice = 0.0;
         }

         subPrice -= itemMenu;
         httpSession.setAttribute("subPrice", subPrice);

         return subPrice;
     }
}
