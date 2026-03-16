package org.pcass.simple_shopping_cart_api.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductCreateRequest {
    private String name;
    private BigDecimal price;
}
