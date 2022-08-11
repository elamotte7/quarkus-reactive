package org.acme.api.versioning.beer.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Beer DTO
 */
@Getter
@Setter
public class BeerDTO {

    private Long id;
    private String name;
    private float alcoholLevel;
    private List<String> ingredients;
    private BreweryDTO brewery;
    private CreatorDTO creator;

}
