package org.pcass.simple_shopping_cart_api.repository;

import org.pcass.simple_shopping_cart_api.model.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Long> {

    void deleteAllByProductId(Long productId);
}
