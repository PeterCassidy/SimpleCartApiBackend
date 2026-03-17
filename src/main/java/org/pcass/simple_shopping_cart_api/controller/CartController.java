package org.pcass.simple_shopping_cart_api.controller;

import lombok.RequiredArgsConstructor;
import org.pcass.simple_shopping_cart_api.model.CartResponseDTO;
import org.pcass.simple_shopping_cart_api.service.CartService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;


    @PostMapping("/")
    public CartResponseDTO calculateCart(@RequestBody List<Long> shoppingList) {

        return cartService.calculateCart(shoppingList);

    }
}
