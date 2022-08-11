package org.acme.api.versioning.beer.repository;

import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import org.acme.api.versioning.beer.model.BeerEntity;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * Beer repository
 */
@ApplicationScoped
public class BeerRepository implements PanacheRepository<BeerEntity> {
    public PanacheQuery<BeerEntity> findAll() {
        return find("from BeerEntity p left join fetch p.ingredients");
    }

    public Uni<List<BeerEntity>> findByName(String name) {
        return list("name", name);
    }
}
