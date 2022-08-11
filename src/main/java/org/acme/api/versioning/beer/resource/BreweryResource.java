package org.acme.api.versioning.beer.resource;

import io.smallrye.mutiny.Uni;
import org.acme.api.versioning.beer.model.dto.BreweryDTO;
import org.acme.api.versioning.beer.service.BreweryService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Resource for brewerys
 */
@Path("/breweries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Breweries", description = "Manage breweries")
public class BreweryResource {

    private final BreweryService breweryService;

    @Inject
    public BreweryResource(BreweryService breweryService) {
        this.breweryService = breweryService;
    }

    @GET
    @Operation(summary = "Get all breweries")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully retrieve brewerys",
                    content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = BreweryDTO.class)))
    })
    public Uni<Response> getBrewerys() {
        return this.breweryService.findAll().onItem().transform(
                Response::ok
        ).onItem().transform(
                Response.ResponseBuilder::build
        );
    }

    @GET
    @Path("{id}")
    @Operation(summary = "Get a brewery by id")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully retrieve brewery",
                    content = @Content(schema = @Schema(implementation = BreweryDTO.class))),
            @APIResponse(responseCode = "404", description = "Resource brewery not found")
    })
    public Uni<Response> getBrewery(@PathParam("id") long id) {
        return this.breweryService.findById(id).onItem().transform(
                Response::ok
        ).onItem().transform(
                Response.ResponseBuilder::build
        );
    }

    @POST
    @Operation(summary = "Create a brewery")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Brewery successfully created",
                    content = @Content(schema = @Schema(implementation = BreweryDTO.class)))
    })
    public Uni<Response> createBrewery(@RequestBody BreweryDTO breweryDTO) {
        return breweryService.createBrewery(breweryDTO).onItem().transform( breweryDTO1 ->
                Response.created(URI.create("/brewerys/" + breweryDTO1.getId())).build()
        );
    }

    @PATCH
    @Path("{id}")
    @Operation(summary = "Update brewery name")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Brewery name successfully updated",
                    content = @Content(schema = @Schema(implementation = BreweryDTO.class))),
            @APIResponse(responseCode = "404", description = "Resource brewery not found")
    })
    public Uni<Response> updateBreweryName(@PathParam("id") Long id, @RequestBody String name) {
        return breweryService.findById(id).onItem().transform(beer -> {
                    beer.setName(name);
                    return breweryService.updateBrewery(beer);
                }
        ).onItem().transform(
                Response::ok
        ).onItem().transform(
                Response.ResponseBuilder::build
        );
    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Update brewery")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Brewery successfully updated",
                    content = @Content(schema = @Schema(implementation = BreweryDTO.class))),
            @APIResponse(responseCode = "404", description = "Resource brewery not found")
    })
    public Uni<Response> updateBrewery(@PathParam("id") Long id, @RequestBody BreweryDTO breweryDTO) {
        return breweryService.updateBrewery(breweryDTO).onItem().transform(
                Response::ok
        ).onItem().transform(
                Response.ResponseBuilder::build
        );
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @Operation(summary = "Delete brewery")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Brewery successfully deleted",
                    content = @Content(schema = @Schema(implementation = BreweryDTO.class))),
            @APIResponse(responseCode = "404", description = "Resource brewery not found")
    })
    public Uni<Response> deleteBrewery(@PathParam("id") Long id) {
        return breweryService.deleteBrewery(id).onItem().transform( aBoolean ->
                aBoolean ? Response.noContent().build() : Response.notModified().build()
        );
    }
}
