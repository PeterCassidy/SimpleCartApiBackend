package org.pcass.simple_shopping_cart_api.service;

import lombok.RequiredArgsConstructor;
import org.pcass.simple_shopping_cart_api.model.OfferDTO;
import org.pcass.simple_shopping_cart_api.model.OfferEntity;
import org.pcass.simple_shopping_cart_api.repository.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;

    public List<OfferDTO> getAllOffers() {
        List<OfferEntity> offerEntities = offerRepository.findAll();

        return offerEntities.stream().map(OfferDTO::fromEntity).toList();
    }

}
