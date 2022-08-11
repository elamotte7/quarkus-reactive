package org.acme.api.versioning.beer.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Beer entity
 */
@Getter
@Setter
@Entity
public class BeerEntity extends PanacheEntity {

    private String name;

    @ManyToOne
    @JoinColumn
    private BreweryEntity brewery;

    @ElementCollection
    private List<String> ingredients;

    private float alcoholLevel;

    @ManyToOne
    @JoinColumn
    private CreatorEntity creator;
}
