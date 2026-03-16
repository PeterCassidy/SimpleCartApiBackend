package org.pcass.simple_shopping_cart_api.repository;

import org.pcass.simple_shopping_cart_api.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
