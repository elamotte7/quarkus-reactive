package org.acme.api.versioning.beer.model.mapper;

import org.acme.api.versioning.beer.model.BeerEntity;
import org.acme.api.versioning.beer.model.BreweryEntity;
import org.acme.api.versioning.beer.model.CreatorEntity;
import org.acme.api.versioning.beer.model.dto.BeerDTO;
import org.acme.api.versioning.beer.model.dto.BreweryDTO;
import org.acme.api.versioning.beer.model.dto.CreatorDTO;
import org.acme.api.versioning.core.mapper.QuarkusMapperConfig;
import org.mapstruct.Mapper;

/**
 * Mapper interface for Beer/Brewery operations
 */
@Mapper(
        config = QuarkusMapperConfig.class
)
public interface BeerMapper {

    BeerDTO toDto(BeerEntity entity);

    BreweryDTO toDto(BreweryEntity entity);

    CreatorDTO toDto(CreatorEntity entity);

    CreatorEntity toEntity(CreatorDTO dto);

    BeerEntity toEntity(BeerDTO dto);

    BreweryEntity toEntity(BreweryDTO dto);
}
