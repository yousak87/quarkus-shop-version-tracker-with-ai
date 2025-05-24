package com.skrill.resource;

import com.skrill.model.Shop;
import com.skrill.services.ShopService;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/crawling")
@Produces(MediaType.APPLICATION_JSON)
public class CrawlingResource {

    private static final Logger log = LoggerFactory.getLogger(CrawlingResource.class);

    @Inject
    ShopService shopService;

    @Inject
    Mailer mailer;

    @GET()
    @Path("/shop/list")
    public List<Shop> getShopList() {

        shopService.updateShopVersion();

        return Shop.listAll();
    }

}
