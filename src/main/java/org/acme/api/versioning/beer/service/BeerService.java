package org.acme.api.versioning.beer.service;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.api.versioning.beer.model.dto.BeerDTO;

import java.util.List;

/**
 * Beer service
 */
public interface BeerService {

    Uni<BeerDTO> findById(long id);

    Uni<List<BeerDTO>> findByName(String nameQuery);

    Uni<List<BeerDTO>> findAll();

    Uni<BeerDTO> createBeer(BeerDTO beerDTO);

    Uni<Boolean> updateBeer(BeerDTO beerDTO);

    Uni<Boolean> deleteBeer(long id);

    Uni<List<BeerDTO>> findByCreatorName(String creatorNameQuery);

    Uni<List<BeerDTO>> findByCreatorFirstNameAndLastName(String firstName, String lastName);
}
