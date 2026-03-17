package org.pcass.simple_shopping_cart_api.repository;

import org.pcass.simple_shopping_cart_api.model.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Long> {

    void deleteAllByProductId(Long productId);
    List<OfferEntity> findAllByOrderByProductIdAscProductQuantityDesc();
}
