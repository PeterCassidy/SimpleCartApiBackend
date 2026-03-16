package org.pcass.simple_shopping_cart_api.service;

import lombok.RequiredArgsConstructor;
import org.pcass.simple_shopping_cart_api.model.ProductCreateRequest;
import org.pcass.simple_shopping_cart_api.model.ProductDTO;
import org.pcass.simple_shopping_cart_api.model.ProductEntity;
import org.pcass.simple_shopping_cart_api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> productEntities = productRepository.findAll();

        return productEntities.stream().map(ProductDTO::fromEntity).toList();
    }

    public ProductDTO createProduct(ProductCreateRequest productCreateRequest) {
        ProductEntity newProduct = new ProductEntity(
                null,
                productCreateRequest.getName(),
                productCreateRequest.getPrice()
        );
        return ProductDTO.fromEntity(productRepository.save(newProduct));
    }

}
