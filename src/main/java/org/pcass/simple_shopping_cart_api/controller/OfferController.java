package org.pcass.simple_shopping_cart_api.controller;

import lombok.RequiredArgsConstructor;
import org.pcass.simple_shopping_cart_api.model.OfferDTO;
import org.pcass.simple_shopping_cart_api.service.OfferService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @GetMapping("/")
    List<OfferDTO> getAllOffers() {
        return offerService.getAllOffers();
    }


}
