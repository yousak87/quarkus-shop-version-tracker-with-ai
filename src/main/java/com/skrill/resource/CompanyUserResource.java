package com.skrill.resource;

import com.skrill.model.CompanyUser;
import com.skrill.model.Shop;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/company-user")
@Produces(MediaType.APPLICATION_JSON)
public class CompanyUserResource {

    private static final Logger log = LoggerFactory.getLogger(CompanyUserResource.class);

    @POST
    @Transactional
    public List<Shop> addOrUpdateCompanyUser(CompanyUser companyUser) {

        CompanyUser existingCompanyUser = CompanyUser.findByEmail(companyUser.emailAddress);

        if(existingCompanyUser != null) {
            existingCompanyUser = companyUser;
            existingCompanyUser.persist();
        }

        companyUser.persist();
        return CompanyUser.listAll();
    }

    @PUT
    @Transactional
    @Path("/{companyUserId}")
    public Response updateCompanyUser(@PathParam("companyUserId") String companyUserId, CompanyUser companyUser) {
        CompanyUser existingCompanyUser = CompanyUser.findById(companyUserId);

        if(existingCompanyUser != null) {
            log.info("this email address: {} exist, will update this data" , companyUser.emailAddress);
            existingCompanyUser = companyUser;
            existingCompanyUser.persist();
            return Response.ok(Shop.listAll()).build();
        }

        companyUser.persist();
        return Response.ok(existingCompanyUser).build();
    }

    @DELETE
    @Transactional
    @Path("/{companyUserId}")
    public Response deleteCompanyUser(String companyUserId) {
        CompanyUser existingCompanyUser = CompanyUser.findById(companyUserId);
        if(existingCompanyUser != null) {
            existingCompanyUser.delete();
        } else {
            log.info("no shop plugin with id {}", companyUserId);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID in path and body don't match")
                    .build();
        }
        return Response.ok(existingCompanyUser).build();
    }
}
