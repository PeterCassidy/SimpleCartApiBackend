package org.pcass.simple_shopping_cart_api.controller;

import lombok.RequiredArgsConstructor;
import org.pcass.simple_shopping_cart_api.model.ProductDTO;
import org.pcass.simple_shopping_cart_api.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    List<ProductDTO> getAllProducts(){
        return productService.getAllProducts();
    }
}
