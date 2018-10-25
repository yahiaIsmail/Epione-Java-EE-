package EPIONE.JAVAEE.presentation.mbeans.resource;

import EPIONE.JAVAEE.entities.User;
import EPIONE.JAVAEE.services.implementation.UserService;
import EPIONE.JAVAEE.services.interfaces.UserServiceLocal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/users")
@RequestScoped
public class UserResource {


    @Inject
    UserServiceLocal userServiceLocal;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/doctors/{speciality}")
    public List<User> scrapingAllDoctors(
            @PathParam(value = "speciality")String speciality
            )  {
        List<User> listDoc= new ArrayList<User>();
        listDoc=userServiceLocal.scrapingAllDoctors(speciality);
        if(listDoc.isEmpty())
        {return null;}
        else
            return listDoc;

    }
    @POST
    @Path("/adddoctor/{fullName}/{speciality}/{state}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public int addDoctor(@PathParam(value = "fullName")String fullName,
                          @PathParam(value="speciality")String speciality,
                          @PathParam(value = "state")String state){


        userServiceLocal.addDoctors(fullName,speciality,state);
        Response.ResponseBuilder builder = null;
        return  1;

    }
//    public String test(){
//        return "success";
//    }





}
