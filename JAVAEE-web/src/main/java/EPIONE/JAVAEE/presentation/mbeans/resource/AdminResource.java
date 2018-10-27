package EPIONE.JAVAEE.presentation.mbeans.resource;

import EPIONE.JAVAEE.entities.Demande;
import EPIONE.JAVAEE.services.interfaces.DemandeServiceLocal;
import EPIONE.JAVAEE.services.interfaces.UserServiceLocal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/admin")
@RequestScoped
public class AdminResource {

    @EJB
    DemandeServiceLocal demandeServiceLocal;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/demandes")
    public List<Demande> displayAllDemandes(){
        return demandeServiceLocal.getAllDemandes();
    }
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/adddemande")
    @Produces(MediaType.TEXT_PLAIN)
    public Response addDemande(Demande demande){
        System.out.println(demande);
        Response.ResponseBuilder builder = null;
        System.out.println(demandeServiceLocal.getDemande(demande));
       // Demande exist= demandeServiceLocal.getDemande(demande);
//        if(exist==null){
//            int id= demandeServiceLocal.addDemande(demande);
//            builder= Response.ok(id);
//        }
//        else {
//            Map<String, String> responseObj = new HashMap<>();
//            responseObj.put("Duplicated: ", "Demand already exist");
//            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
//
//        }

        return builder.build();
    }

}

