package org.acme.api.versioning.beer.service.impl;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import org.acme.api.versioning.beer.model.dto.BreweryDTO;
import org.acme.api.versioning.beer.model.mapper.BeerMapper;
import org.acme.api.versioning.beer.repository.BreweryRepository;
import org.acme.api.versioning.beer.service.BreweryService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;

/**
 * Default implementation of brewery service
 */
@ApplicationScoped
public class BreweryServiceImpl implements BreweryService {

    public static final String BREWERY_NOT_FOUND = "No brewery found with id '%d'";

    private final BreweryRepository breweryRepository;

    private final BeerMapper beerMapper;

    @Inject
    public BreweryServiceImpl(BreweryRepository breweryRepository,
                              BeerMapper beerMapper) {
        this.breweryRepository = breweryRepository;
        this.beerMapper = beerMapper;
    }

    @Override
    @ReactiveTransactional
    public Uni<BreweryDTO> createBrewery(BreweryDTO breweryDTO) {
        return breweryRepository.persist(beerMapper.toEntity(breweryDTO)).onItem().transform(
                beerMapper::toDto
        );
    }

    @Override
    public Uni<Boolean> updateBrewery(BreweryDTO breweryDTO) {
        return this.findById(breweryDTO.getId()).onItem().transform(
                beerMapper::toEntity
        ).onItem().transformToUni( beerEntity ->
                breweryRepository.persist(beerEntity)
        ).onItem().transform( beerEntity ->
                Boolean.TRUE
        );
    }

    @Override
    public Uni<Boolean> deleteBrewery(long id) {
        return this.findById(id).onItem().transformToUni(
                beerDTO -> breweryRepository.deleteById(beerDTO.getId())
        );
    }

    @Override
    public Uni<List<BreweryDTO>> findByName(String name) {
        return this.breweryRepository.findByName(name).onItem().transform( list ->
                list.stream().map(beerMapper::toDto).toList()
        );
    }

    @Override
    public Uni<BreweryDTO> findById(long id) {
        return this.breweryRepository.findById(id).onItem().ifNotNull().transform(
                beerMapper::toDto
        ).onItem().ifNull().failWith(
                new NotFoundException(String.format(BREWERY_NOT_FOUND, id))
        );
    }

    @Override
    public Uni<List<BreweryDTO>> findAll() {
        return breweryRepository.listAll().onItem().transform( list ->
                list.stream().map(beerMapper::toDto).toList()
        );
    }
}
