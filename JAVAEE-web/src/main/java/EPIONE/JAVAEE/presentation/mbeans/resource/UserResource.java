package EPIONE.JAVAEE.presentation.mbeans.resource;

import EPIONE.JAVAEE.entities.User;
import EPIONE.JAVAEE.services.implementation.UserService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/users")
public class UserResource {
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/doctors")
    public String test(){
        return "success";
    }




//    public List<User> scrapingAllDoctors()  {
//        UserService userService= new UserService();
//         List<User> listDoc= new ArrayList<User>();
//        listDoc=userService.scrapingAllDoctors();
//        return listDoc;
//    }
}
