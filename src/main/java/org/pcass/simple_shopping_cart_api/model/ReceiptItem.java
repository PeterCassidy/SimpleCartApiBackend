package org.pcass.simple_shopping_cart_api.model;

import java.math.BigDecimal;

public record ReceiptItem(String description, BigDecimal price) {
}
