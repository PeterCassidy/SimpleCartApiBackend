package org.pcass.simple_shopping_cart_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pcass.simple_shopping_cart_api.model.ProductCreateRequest;
import org.pcass.simple_shopping_cart_api.model.ProductDTO;
import org.pcass.simple_shopping_cart_api.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    ProductDTO createProduct(@Valid @RequestBody ProductCreateRequest request){
        return productService.createProduct(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ProductDTO updateProduct(@PathVariable Long id, @Valid @RequestBody ProductCreateRequest request){
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }
}
