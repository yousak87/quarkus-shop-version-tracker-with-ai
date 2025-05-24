package com.skrill.resource;


import com.skrill.dto.ShopDTO;
import com.skrill.model.Shop;
import com.skrill.services.ShopService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/shop")
@Produces(MediaType.APPLICATION_JSON)
public class ShopResource {

    private static final Logger log = LoggerFactory.getLogger(ShopResource.class);

    @GET
    @Transactional
    public Response updateShop() {
        return Response.ok()
                .entity(Shop.listAll())
                .type(MediaType.APPLICATION_JSON)
                .build();

    }

    @POST
    @Transactional
    public Response addShop(ShopDTO shopDTO) {

        Shop existingShop = Shop.findByName(shopDTO.getName());

        if (existingShop != null) {
            log.info("shop already exist in db {}", shopDTO.getName());
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Data exist");

            return Response.status(Response.Status.CONFLICT) // HTTP 409 Conflict
                    .entity(response)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        existingShop = new Shop();

        log.info("existing shop name: {} exist, will update this data", shopDTO.name);
        existingShop.name = shopDTO.getName();
        existingShop.url = shopDTO.getUrl();
        existingShop.latest_version = shopDTO.getLatest_version();
        existingShop.running_state = shopDTO.getRunning_state();
        existingShop.repo_type = shopDTO.getRepo_type();
        existingShop.last_update = LocalDateTime.now();
        existingShop.last_run = LocalDateTime.now();

        existingShop.persist();

        return Response.ok(Shop.listAll()).build();
    }


    @PUT
    @Path("/{shopId}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateShop(
            @PathParam("shopId") Long shopId,
            ShopDTO shopDTO) {
        Shop existingShop = Shop.findById(shopId);

        if (existingShop != null) {
            log.info("shop exist in db {}", shopDTO.getName());

            existingShop.name = shopDTO.getName();
            existingShop.url = shopDTO.getUrl();
            existingShop.latest_version = shopDTO.getLatest_version();
            existingShop.running_state = shopDTO.getRunning_state();
            existingShop.repo_type = shopDTO.getRepo_type();
            existingShop.last_update = LocalDateTime.now();
            existingShop.last_run = LocalDateTime.now();

            existingShop.persist();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "data updated");

            return Response.ok()
                    .entity(response)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }


        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "data not found on db");

        return Response.status(Response.Status.NOT_FOUND)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }


    @DELETE
    @Transactional
    @Path("/{shopId}")
    public Response deleteShop(@PathParam("shopId") Long shopId) {
        Shop existingShop = Shop.findById(shopId);
        if (existingShop != null) {
            existingShop.delete();
        } else {
            log.info("no shop plugin with id {}", shopId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "data not found");

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(response)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "data deleted");

        return Response.status(Response.Status.NOT_FOUND)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
