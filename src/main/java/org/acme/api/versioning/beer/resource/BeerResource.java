package org.acme.api.versioning.beer.resource;

import io.smallrye.mutiny.Uni;
import org.acme.api.versioning.beer.model.dto.BeerDTO;
import org.acme.api.versioning.beer.service.BeerService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Resource for beers
 */
@Path("/beers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Beers", description = "Manage beers")
public class BeerResource {

    private final BeerService beerService;

    @Inject
    public BeerResource(BeerService beerService) {
        this.beerService = beerService;
    }

    @GET
    @Operation(summary = "Get all beers")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully retrieve beers",
                    content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = BeerDTO.class)))
    })
    public Uni<Response> getBeers() {
        return this.beerService.findAll().onItem().transform(
                Response::ok
        ).onItem().transform(
                Response.ResponseBuilder::build
        );
    }

    @GET
    @Path("{id}")
    @Operation(summary = "Get a beer by id")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully retrieve beer",
                    content = @Content(schema = @Schema(implementation = BeerDTO.class))),
            @APIResponse(responseCode = "404", description = "Resource beer not found")
    })
    public Uni<Response> getBeer(@PathParam("id") long id) {
        return this.beerService.findById(id).onItem().transform(
                Response::ok
        ).onItem().transform(
                Response.ResponseBuilder::build
        );
    }

    @GET
    @Path("search/{name}")
    @Operation(summary = "Get a beer by name")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Successfully retrieve beer",
                    content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = BeerDTO.class))),
            @APIResponse(responseCode = "404", description = "Resource beer not found")
    })
    public Uni<Response> getBeer(@PathParam("name") String name) {
        return this.beerService.findByName(name).onItem().transform(
                Response::ok
        ).onItem().transform(
                Response.ResponseBuilder::build
        );
    }

    @POST
    @Operation(summary = "Create a beer")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Beer successfully created",
                    content = @Content(schema = @Schema(implementation = BeerDTO.class)))
    })
    public Uni<Response> createBeer(@RequestBody BeerDTO beerDTO) {
        return beerService.createBeer(beerDTO).onItem().transform(beerDTO1 ->
                Response.created(URI.create("/beers/" + beerDTO1.getId())).build()
        );
    }

    @PATCH
    @Path("{id}")
    @Operation(summary = "Update beer name")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Beer name successfully updated",
                    content = @Content(schema = @Schema(implementation = BeerDTO.class))),
            @APIResponse(responseCode = "404", description = "Resource beer not found")
    })
    public Uni<Response> updateBeerName(@PathParam("id") Long id, @RequestBody String name) {
        return beerService.findById(id).onItem().transform(beer -> {
                    beer.setName(name);
                    return beerService.updateBeer(beer);
                }
        ).onItem().transform(
                Response::ok
        ).onItem().transform(
                Response.ResponseBuilder::build
        );
    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Update beer")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Beer successfully updated",
                    content = @Content(schema = @Schema(implementation = BeerDTO.class))),
            @APIResponse(responseCode = "404", description = "Resource beer not found")
    })
    public Uni<Response> updateBeer(@PathParam("id") Long id, @RequestBody BeerDTO beerDTO) {
        return beerService.updateBeer(beerDTO).onItem().transform(
                Response::ok
        ).onItem().transform(
                Response.ResponseBuilder::build
        );
    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Delete beer")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Beer successfully deleted",
                    content = @Content(schema = @Schema(implementation = BeerDTO.class))),
            @APIResponse(responseCode = "404", description = "Resource beer not found")
    })
    public Uni<Response> deleteBeer(@PathParam("id") Long id) {
        return beerService.deleteBeer(id).onItem().transform(aBoolean ->
                aBoolean ? Response.noContent().build() : Response.notModified().build()
        );
    }
}
