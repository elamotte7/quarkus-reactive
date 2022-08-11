package org.acme.api.versioning.beer.service;

import io.smallrye.mutiny.Uni;
import org.acme.api.versioning.beer.model.dto.BreweryDTO;

import java.util.List;

/**
 * Brewery service
 */
public interface BreweryService {

    Uni<BreweryDTO> createBrewery(BreweryDTO breweryDTO);

    Uni<Boolean> updateBrewery(BreweryDTO breweryDTO);

    Uni<Boolean> deleteBrewery(long id);

    Uni<BreweryDTO> findById(long id);

    Uni<List<BreweryDTO>> findAll();

    Uni<List<BreweryDTO>> findByName(String nameQuery);
}
