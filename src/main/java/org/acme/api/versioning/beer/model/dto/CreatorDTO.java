package org.acme.api.versioning.beer.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatorDTO {

    private Long id;

    private String lastName;

    private String firstName;
}
