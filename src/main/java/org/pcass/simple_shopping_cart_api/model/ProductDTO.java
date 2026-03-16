package org.pcass.simple_shopping_cart_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private BigDecimal price;

    ProductEntity toEntity() {
        return new ProductEntity(this.id, this.name, this.price);
    }

    public static ProductDTO fromEntity(ProductEntity productEntity) {
        return new ProductDTO(productEntity.getId(), productEntity.getName(), productEntity.getPrice());
    }
}
