package com.example.springjerseyrest;


import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/user")
public class UserController {


    @GET
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(User user){

        List<User> users = new ArrayList<>();

        users.add(new User("jes","1234", SpringJerseyRestApplication.ROLES.ADMIN));
        users.add(new User("sej","1234", SpringJerseyRestApplication.ROLES.NONADMIN));
        users.add(new User("maj","1234", SpringJerseyRestApplication.ROLES.ADMIN));
        users.add(new User("rig","1234", SpringJerseyRestApplication.ROLES.NONADMIN));

        return Response.status(Response.Status.ACCEPTED).entity(users).type(MediaType.APPLICATION_JSON).build();

    }
}
