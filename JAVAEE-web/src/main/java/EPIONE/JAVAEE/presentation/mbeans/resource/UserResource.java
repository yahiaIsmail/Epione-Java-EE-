package EPIONE.JAVAEE.presentation.mbeans.resource;

import EPIONE.JAVAEE.entities.Demande;
import EPIONE.JAVAEE.entities.User;
import EPIONE.JAVAEE.services.implementation.UserService;
import EPIONE.JAVAEE.services.interfaces.DemandeServiceLocal;
import EPIONE.JAVAEE.services.interfaces.UserServiceLocal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/users")
@RequestScoped
public class UserResource {


    @EJB
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
    public Response addDoctor(@PathParam(value = "fullName")String fullName,
                          @PathParam(value="speciality")String speciality,
                          @PathParam(value = "state")String state){

        Response.ResponseBuilder builder = null;
        try{
            int id =userServiceLocal.addDoctors(fullName,speciality,state);
            if(id==-1)
            {
                Map<String, String> responseObj = new HashMap<>();
                responseObj.put("Unvalid data:", "cannot scrap doctor, please enter a valid doctor data ");
                builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
            }
            else{
                builder= Response.ok(id);
            }

        }
        catch (Exception e){
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }


        return  builder.build();

    }




}
