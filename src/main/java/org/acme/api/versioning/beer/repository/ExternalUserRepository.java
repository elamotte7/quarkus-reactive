package org.acme.api.versioning.beer.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import org.acme.api.versioning.beer.model.CreatorEntity;

import javax.enterprise.context.ApplicationScoped;

/**
 * External user repository
 */
@ApplicationScoped
public class ExternalUserRepository implements PanacheRepository<CreatorEntity> {
}
