package org.pcass.simple_shopping_cart_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OfferDTO {
    private Long id;
    private Long productId;
    private Long productQuantity;
    private BigDecimal offerPrice;

    public static OfferDTO fromEntity(OfferEntity entity) {
        return new OfferDTO(entity.getId(), entity.getProductId(), entity.getProductQuantity(), entity.getOfferPrice());
    }

}
