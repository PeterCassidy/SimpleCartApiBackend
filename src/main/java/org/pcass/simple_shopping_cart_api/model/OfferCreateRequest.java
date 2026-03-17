package org.pcass.simple_shopping_cart_api.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OfferCreateRequest {

    @NotNull(message = "Product ID is required.")
    private Long productId;

    @NotNull(message = "Product Quantity is required.")
    private Long productQuantity;

    @NotNull(message = "Offer price is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Offer price must be greater than 0.00")
    @Digits(integer = 10, fraction = 2, message = "Offer price must have 2 decimal places")
    private BigDecimal offerPrice;
}
