package org.pcass.simple_shopping_cart_api.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.pcass.simple_shopping_cart_api.model.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductService productService;
    private final OfferService offerService;

    public CartResponseDTO calculateCart(List<Long> shoppingList) {

        //aggregate shopping list into items
        Map<Long, Long> aggregatedShoppingList =
                shoppingList.stream()
                        .collect(Collectors.groupingBy(
                                productId -> productId,
                                Collectors.counting()
                        ));

        //check products exist for each id, throw exception for missing products;
        Set<Long> missingProducts = aggregatedShoppingList.keySet().stream()
                .filter(productId -> !productService.productExistsById(productId))
                .collect(Collectors.toSet());

        if (!missingProducts.isEmpty()) {
            throw new InvalidCartException("Products do not exist for following productIds: " + missingProducts);
        }

        //apply offers to cart
        List<ReceiptItem> receiptItemsFromOffers = applyOffers(aggregatedShoppingList);
        List<ReceiptItem> receiptItemsFromRemainingItems = convertItemsToReceiptItems(aggregatedShoppingList);

        List<ReceiptItem> outputList = new ArrayList<>();
        outputList.addAll(receiptItemsFromOffers);
        outputList.addAll(receiptItemsFromRemainingItems);

        return new CartResponseDTO(outputList, calculateTotalCost(outputList));
    }

    private @NonNull List<ReceiptItem> applyOffers(Map<Long, Long> aggregatedShoppingList) {
        List<OfferDTO> offers = offerService.getValidOffers();
        List<ReceiptItem> receiptItems = new ArrayList<>();

        //iterate offers
        offers.forEach(
                offer -> {
                    Long productId = offer.getProductId();
                    Long productsInList = aggregatedShoppingList.get(productId);
                    if(productsInList == null){
                        //if no offer products, break loop.
                        return;
                    }
                    long offerApplications = productsInList / offer.getProductQuantity();
                    ProductDTO product = productService.getProductById(productId);
                    //add each application of the offer to the receipt
                    for (int i = 0; i < offerApplications; i++) {
                        receiptItems.add(new ReceiptItem(
                                String.format("%s x %d", product.getName(), offer.getProductQuantity()),
                                offer.getOfferPrice()
                        ));
                    }
                    //remove offer applications from list
                    aggregatedShoppingList.put(productId, aggregatedShoppingList.get(productId) - (offerApplications * offer.getProductQuantity()));
                }
        );
        return receiptItems;
    }

    private List<ReceiptItem> convertItemsToReceiptItems(Map<Long, Long> aggregatedShoppingList) {
        List<ReceiptItem> receiptItems = new ArrayList<>();
        aggregatedShoppingList.keySet().forEach(
                key -> {
                    Long count = aggregatedShoppingList.get(key);
                    ProductDTO product = productService.getProductById(key);
                    for (int i = 0; i < count; i++) {
                        receiptItems.add(new ReceiptItem(product.getName(), product.getPrice()));
                    }
                }
        );

        return receiptItems;
    }

    private BigDecimal calculateTotalCost(List<ReceiptItem> receiptItems) {
        return receiptItems.stream()
                .map(ReceiptItem::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
