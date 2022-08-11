package org.acme.api.versioning.beer.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * External use entity : for demo purpose we store it in the same application as
 * the beers and breweries, but it should be in another application
 */
@Getter
@Setter
@Entity
public class CreatorEntity extends PanacheEntity {

    private String lastName;

    private String firstName;
}
