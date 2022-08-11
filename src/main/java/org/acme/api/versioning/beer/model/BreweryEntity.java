package org.acme.api.versioning.beer.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Brewery entity
 */
@Getter
@Setter
@Entity
public class BreweryEntity extends PanacheEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private BreweryType type;

    private String country;

    @OneToMany(mappedBy = "brewery", fetch = FetchType.EAGER)
    private List<BeerEntity> beers;
}
