package org.acme.api.versioning.beer.service.impl;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.api.versioning.beer.model.dto.BeerDTO;
import org.acme.api.versioning.beer.model.mapper.BeerMapper;
import org.acme.api.versioning.beer.repository.BeerRepository;
import org.acme.api.versioning.beer.service.BeerService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.List;

/**
 * Default implementation of beer service
 */
@ApplicationScoped
public class BeerServiceImpl implements BeerService {

    public static final String BEER_NOT_FOUND = "No beer found with id '%d'";

    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper;

    @Inject
    public BeerServiceImpl(BeerRepository beerRepository,
                           BeerMapper beerMapper) {
        this.beerRepository = beerRepository;
        this.beerMapper = beerMapper;
    }

    @Override
    public Uni<BeerDTO> findById(long id) {
        return this.beerRepository.findById(id).onItem().ifNotNull().transform(
                beerMapper::toDto
        ).onItem().ifNull().failWith(
                new NotFoundException(String.format(BEER_NOT_FOUND, id))
        );
    }

    @Override
    public Uni<List<BeerDTO>> findByName(String name) {
        return this.beerRepository.findByName(name).onItem().transform(
                list -> list.stream().map(beerMapper::toDto).toList()
        );
    }

    @Override
    @ReactiveTransactional
    public Uni<List<BeerDTO>> findAll() {
        return this.beerRepository.findAll().list().onItem().transform(list ->
                list.stream().map(
                        beerMapper::toDto
                        ).toList()
        );
//        return this.beerRepository.listAll().onItem().transform( list ->
//                list.stream().map(beerMapper::toDto).toList()
//        );
    }

    @Override
    @ReactiveTransactional
    public Uni<BeerDTO> createBeer(BeerDTO beerDTO) {
        return beerRepository.persist(beerMapper.toEntity(beerDTO)).onItem().transform(
                beerMapper::toDto
        );
    }

    @Override
    @ReactiveTransactional
    public Uni<Boolean> updateBeer(BeerDTO beerDTO) {
        return this.findById(beerDTO.getId()).onItem().transform(
                beerMapper::toEntity
        ).onItem().transformToUni( beerEntity ->
                    beerRepository.persist(beerEntity)
        ).onItem().transform( beerEntity ->
                Boolean.TRUE
        );
    }

    @Override
    @ReactiveTransactional
    public Uni<Boolean> deleteBeer(long id) {
        return this.findById(id).onItem().transformToUni(
                beerDTO -> beerRepository.deleteById(beerDTO.getId())
        );
    }

    @Override
    @ReactiveTransactional
    public Uni<List<BeerDTO>> findByCreatorName(String creator) {
        return beerRepository.list("creator.firstName = ?1", creator).onItem().transform(
                list -> list.stream().map(beerMapper::toDto).toList()
        );
    }

    @Override
    @ReactiveTransactional
    public Uni<List<BeerDTO>> findByCreatorFirstNameAndLastName(String firstName, String lastName) {
        return beerRepository.list("creator.firstName = ?1 and creator.lastName = ?2", firstName, lastName)
                .onItem().transform(
                        list -> list.stream().map(beerMapper::toDto).toList()
                );
    }
}
