package org.pcass.simple_shopping_cart_api.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductCreateRequest {
    @NotBlank(message = "Name is required.")
    private String name;
    
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0.0")
    @Digits(integer = 10, fraction = 2, message = "Price must have 2 decimal places")
    private BigDecimal price;
}
