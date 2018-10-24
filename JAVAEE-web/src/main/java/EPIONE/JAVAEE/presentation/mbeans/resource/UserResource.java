package EPIONE.JAVAEE.presentation.mbeans.resource;

import EPIONE.JAVAEE.entities.User;
import EPIONE.JAVAEE.services.implementation.UserService;
import EPIONE.JAVAEE.services.interfaces.UserServiceRemote;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/users")
@RequestScoped
public class UserResource {



    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/doctors/{speciality}")
    public List<User> scrapingAllDoctors(
            @PathParam(value = "speciality")String speciality
            )  {
        UserService userService= new UserService();
        List<User> listDoc= new ArrayList<User>();
        listDoc=userService.scrapingAllDoctors(speciality);
        return listDoc;

    }
//    public String test(){
//        return "success";
//    }





}
