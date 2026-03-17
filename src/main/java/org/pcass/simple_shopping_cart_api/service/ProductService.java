package org.pcass.simple_shopping_cart_api.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.JoinFormula;
import org.pcass.simple_shopping_cart_api.model.ProductCreateRequest;
import org.pcass.simple_shopping_cart_api.model.ProductDTO;
import org.pcass.simple_shopping_cart_api.model.ProductEntity;
import org.pcass.simple_shopping_cart_api.model.ProductNotFoundException;
import org.pcass.simple_shopping_cart_api.repository.OfferRepository;
import org.pcass.simple_shopping_cart_api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final OfferRepository offerRepository;

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

    @Transactional
    public ProductDTO updateProduct(Long id, ProductCreateRequest request){

        ProductEntity updatedProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No product found for id: " + id));

        offerRepository.deleteAllByProductId(id);

        updatedProduct.setName(request.getName());
        updatedProduct.setPrice(request.getPrice());
        return ProductDTO.fromEntity(productRepository.save(updatedProduct));
    }

    @Transactional
    public void deleteProduct(Long id) {

        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("No product found for id: " + id);
        }

        offerRepository.deleteAllByProductId(id);
        productRepository.deleteById(id);
    }

    public Boolean productExistsById(Long id) {
        return productRepository.existsById(id);
    }

    public ProductDTO getProductById(Long id) {

        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No product found for id: " + id));

        return ProductDTO.fromEntity(product);
    }

}
