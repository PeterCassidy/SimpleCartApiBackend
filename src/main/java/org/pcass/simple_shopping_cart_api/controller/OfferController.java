package org.pcass.simple_shopping_cart_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pcass.simple_shopping_cart_api.model.OfferCreateRequest;
import org.pcass.simple_shopping_cart_api.model.OfferDTO;
import org.pcass.simple_shopping_cart_api.service.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    OfferDTO createOffer(@Valid @RequestBody OfferCreateRequest request){
        return offerService.createOffer(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    OfferDTO updateOffer(@PathVariable Long id, @Valid @RequestBody OfferCreateRequest request){
        return offerService.updateOffer(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteOffer(@PathVariable Long id){
        offerService.deleteOffer(id);
    }

}
