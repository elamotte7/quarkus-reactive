package org.acme.api.versioning.beer.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import org.acme.api.versioning.beer.model.BreweryEntity;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * Beer repository
 */
@ApplicationScoped
public class BreweryRepository implements PanacheRepository<BreweryEntity> {

    public Uni<List<BreweryEntity>> findByName(String name) {
        return list("name", name);
    }
}
