package services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
public class UserService {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello")
    public String testRest() {
        return "test";
    }
}
