package org.pcass.simple_shopping_cart_api.service;

import lombok.RequiredArgsConstructor;
import org.pcass.simple_shopping_cart_api.model.ProductCreateRequest;
import org.pcass.simple_shopping_cart_api.model.ProductDTO;
import org.pcass.simple_shopping_cart_api.model.ProductEntity;
import org.pcass.simple_shopping_cart_api.model.ProductNotFoundException;
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

    public ProductDTO updateProduct(Long id, ProductCreateRequest request){
        ProductEntity updatedProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No product found for id: " + id));
        updatedProduct.setName(request.getName());
        updatedProduct.setPrice(request.getPrice());
        return ProductDTO.fromEntity(productRepository.save(updatedProduct));
    }

    public void deleteProduct(Long id){
        ProductEntity entityForDeletion= productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No product found for id: " + id));
        productRepository.delete(entityForDeletion);
    }

}
