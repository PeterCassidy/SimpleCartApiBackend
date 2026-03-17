package org.pcass.simple_shopping_cart_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class CartResponseDTO {
    List<ReceiptItem> cartItems;
    BigDecimal total;
}
