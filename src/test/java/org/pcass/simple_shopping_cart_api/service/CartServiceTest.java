package org.pcass.simple_shopping_cart_api.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pcass.simple_shopping_cart_api.model.CartResponseDTO;
import org.pcass.simple_shopping_cart_api.model.OfferDTO;
import org.pcass.simple_shopping_cart_api.model.ProductDTO;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    ProductService productService;

    @Mock
    OfferService offerService;

    @InjectMocks
    CartService cartService;

    @Test
    public void emptyCart_returnsEmptyReceipt(){

        when(offerService.getValidOffers()).thenReturn(
                List.of(new OfferDTO(1L, 1L, 3L, BigDecimal.valueOf(1.30)))
        );

        CartResponseDTO response = cartService.calculateCart(List.of());

        assertThat(response.getCartItems()).isEmpty();
        assertThat(response.getTotal()).isEqualByComparingTo(BigDecimal.valueOf(0));

        verifyNoInteractions(productService);
    }

    @Test
    public void cartWithNoMatchingOffers_returnsReceiptWithItems() {

        when(offerService.getValidOffers()).thenReturn(
                List.of(new OfferDTO(1L, 1L, 3L, BigDecimal.valueOf(1.30d)))
        );
        when(productService.productExistsById(1L)).thenReturn(
                Boolean.TRUE
        );
        when(productService.getProductById(1L)).thenReturn(
                new ProductDTO(1L, "apple", BigDecimal.valueOf(0.30))
        );

        CartResponseDTO response = cartService.calculateCart(List.of(1L));

        assertThat(response.getCartItems()).hasSize(1);
        assertThat(response.getCartItems().getFirst().description()).isEqualTo("apple");
        assertThat(response.getCartItems().getFirst().price()).isEqualTo(BigDecimal.valueOf(0.30));
        assertThat(response.getTotal()).isEqualTo(BigDecimal.valueOf(0.30));

    }

    @Test
    public void cartWithOnlyMatchingOffers_returnsReceiptWithOffers(){

        when(offerService.getValidOffers()).thenReturn(
                List.of(new OfferDTO(1L, 1L, 3L, BigDecimal.valueOf(1.30)))
        );
        when(productService.productExistsById(1L)).thenReturn(
                Boolean.TRUE
        );
        when(productService.getProductById(1L)).thenReturn(
                new ProductDTO(1L, "apple", BigDecimal.valueOf(0.30))
        );

        CartResponseDTO response = cartService.calculateCart(List.of(1L,1L,1L));

        assertThat(response.getCartItems()).hasSize(1);
        assertThat(response.getCartItems().getFirst().description()).isEqualTo("apple x 3");
        assertThat(response.getCartItems().getFirst().price()).isEqualTo(BigDecimal.valueOf(1.30));
        assertThat(response.getTotal()).isEqualTo(BigDecimal.valueOf(1.30));

    }

    @Test
    public void cartWithMatchingOffersAndSpareItems_returnsReceiptWithOffersAndRemainingItems(){

        when(offerService.getValidOffers()).thenReturn(
                List.of(new OfferDTO(1L, 1L, 3L, BigDecimal.valueOf(1.30)),
                        new OfferDTO(2L, 2L, 2L, BigDecimal.valueOf(1.20)))
        );
        when(productService.productExistsById(1L)).thenReturn(
                Boolean.TRUE
        );
        when(productService.getProductById(1L)).thenReturn(
                new ProductDTO(1L, "apple", BigDecimal.valueOf(0.30))
        );
        when(productService.productExistsById(2L)).thenReturn(
                Boolean.TRUE
        );
        when(productService.getProductById(2L)).thenReturn(
                new ProductDTO(2L, "orange", BigDecimal.valueOf(0.35))
        );

        CartResponseDTO response = cartService.calculateCart(List.of(1L,2L,1L,1L,2L,1L,2L));

        assertThat(response.getCartItems()).hasSize(4);
        assertThat(response.getCartItems().getFirst().description()).isEqualTo("apple x 3");
        assertThat(response.getCartItems().getFirst().price()).isEqualTo(BigDecimal.valueOf(1.30));
        assertThat(response.getCartItems().get(1).description()).isEqualTo("orange x 2");
        assertThat(response.getCartItems().get(1).price()).isEqualTo(BigDecimal.valueOf(1.20));
        assertThat(response.getCartItems().get(2).description()).isEqualTo("apple");
        assertThat(response.getCartItems().get(2).price()).isEqualTo(BigDecimal.valueOf(0.30));
        assertThat(response.getCartItems().get(3).description()).isEqualTo("orange");
        assertThat(response.getCartItems().get(3).price()).isEqualTo(BigDecimal.valueOf(0.35));
        assertThat(response.getTotal()).isEqualTo(BigDecimal.valueOf(3.15));
    }

    @Test
    public void cartWithMultipleOffersForSameProduct_returnsReceiptWithOffersApplied(){

        when(offerService.getValidOffers()).thenReturn(
                List.of(new OfferDTO(1L, 1L, 3L, BigDecimal.valueOf(1.30)),
                        new OfferDTO(2L, 1L, 2L, BigDecimal.valueOf(0.75)))
        );
        when(productService.productExistsById(1L)).thenReturn(
                Boolean.TRUE
        );
        when(productService.getProductById(1L)).thenReturn(
                new ProductDTO(1L, "apple", BigDecimal.valueOf(0.30))
        );

        CartResponseDTO response = cartService.calculateCart(List.of(1L,1L,1L,1L,1L));

        assertThat(response.getCartItems()).hasSize(2);
        assertThat(response.getCartItems().getFirst().description()).isEqualTo("apple x 3");
        assertThat(response.getCartItems().getFirst().price()).isEqualTo(BigDecimal.valueOf(1.30));
        assertThat(response.getCartItems().get(1).description()).isEqualTo("apple x 2");
        assertThat(response.getCartItems().get(1).price()).isEqualTo(BigDecimal.valueOf(0.75));
        assertThat(response.getTotal()).isEqualTo(BigDecimal.valueOf(2.05));
    }

}
