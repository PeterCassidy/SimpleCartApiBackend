package org.pcass.simple_shopping_cart_api.service;

import lombok.RequiredArgsConstructor;
import org.pcass.simple_shopping_cart_api.model.*;
import org.pcass.simple_shopping_cart_api.repository.OfferRepository;
import org.pcass.simple_shopping_cart_api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final ProductRepository productRepository;
    private final OfferRepository offerRepository;

    public List<OfferDTO> getAllOffers() {
        List<OfferEntity> offerEntities = offerRepository.findAll();

        return offerEntities.stream().map(OfferDTO::fromEntity).toList();
    }

    public OfferDTO createOffer(OfferCreateRequest offerCreateRequest) {

        Long offerProductId = offerCreateRequest.getProductId();
        if(!productRepository.existsById(offerProductId)){
            throw new ProductNotFoundException("Could not create offer, Product with id "+ offerProductId  + " not found.");
        }

        OfferEntity newOffer = new OfferEntity(
                null,
                offerProductId,
                offerCreateRequest.getProductQuantity(),
                offerCreateRequest.getOfferPrice()
        );
        return OfferDTO.fromEntity(offerRepository.save(newOffer));
    }

    public OfferDTO updateOffer(Long id, OfferCreateRequest request) {

        Long offerProductId = request.getProductId();
        if(!productRepository.existsById(offerProductId)){
            throw new ProductNotFoundException("Could not update offer, Product with id "+ offerProductId  + " not found.");
        }

        OfferEntity updatedOffer = offerRepository.findById(id)
                .orElseThrow(() -> new OfferNotFoundException("No offer found for id: " + id));

        updatedOffer.setProductId(request.getProductId());
        updatedOffer.setProductQuantity(request.getProductQuantity());
        updatedOffer.setOfferPrice(request.getOfferPrice());

        return OfferDTO.fromEntity(offerRepository.save(updatedOffer));
    }

    public void deleteOffer(Long id) {

        if (!offerRepository.existsById(id)) {
            throw new ProductNotFoundException("No offer found for id: " + id);
        }

        offerRepository.deleteById(id);
    }
}
