package EPIONE.JAVAEE.presentation.mbeans.resource;

import EPIONE.JAVAEE.entities.Demande;
import EPIONE.JAVAEE.entities.User;
import EPIONE.JAVAEE.services.interfaces.DemandeServiceLocal;
import EPIONE.JAVAEE.services.interfaces.UserServiceLocal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/admin")
@RequestScoped
public class AdminResource {

    @EJB
    DemandeServiceLocal demandeServiceLocal;

    @EJB
    UserServiceLocal userServiceLocal;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/demandes")
    public Response displayAllDemandes() {
        System.out.println("done!");
       // return demandeServiceLocal.getAllDemandes();
        return Response.ok(demandeServiceLocal.getAllDemandes()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getonedemand")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getonedemand(Demande demande) {
        Response.ResponseBuilder builder = null;
//        System.out.println(demandeServiceLocal.getDemande(demande));
        List<Demande> demandes = demandeServiceLocal.getDemande(demande);

        if (demandes.isEmpty()) {

            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("Not Exist: ", "Demand does not exist");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);


        } else {
            builder = Response.ok(demandes.get(0));

        }

        return builder.build();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/adddemande")
    @Produces(MediaType.TEXT_PLAIN)
    public Response addDemande(Demande demande) {
        System.out.println("ok");
        Response.ResponseBuilder builder = null;
        System.out.println(demandeServiceLocal.getDemande(demande));
        // Demande exist= demandeServiceLocal.getDemande(demande);

        if (demandeServiceLocal.getDemande(demande).isEmpty()) {

            User doc = new User();
            doc.setFirstName(demande.getFirstName());
            doc.setLastName(demande.getLastName());
            doc.setEmail(demande.getEmail());
            doc.setSpeciality(demande.getSpeciality());
            doc.setState(demande.getState());
            //    int ValidateDoctors(String firstName, String lastName, String speciality, String state, String email);


            //System.out.println(userServiceLocal.getDoctor(doc).isEmpty());

                int ok = userServiceLocal.ValidateDoctors(doc.getFirstName(),
                        doc.getLastName(),
                        doc.getSpeciality(),
                        doc.getState(),
                        doc.getEmail());
                System.out.println("okk: ********* " + ok);
                if(ok == (-1)){

                    Map<String, String> responseObj = new HashMap<>();
                    responseObj.put("Not Found: ", "Account does not exist in doctolib");
                    builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
                }
                else {

                    if (userServiceLocal.getDoctor(doc).isEmpty()) {
                        int id = demandeServiceLocal.addDemande(demande);
                        builder = Response.ok(id);
                    }
                    else {
                        Map<String, String> responseObj = new HashMap<>();
                        responseObj.put("Forbbiden: ", "Account already created !");
                        builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
                    }


                }

           // }


        } else {

            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("Duplicated: ", "Demand already exist");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);


        }
      //  return builder.build();
        return builder.build();

    }



    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/deletedemande")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteDemande(Demande demande) {
        Response.ResponseBuilder builder = null;
//        System.out.println(demandeServiceLocal.getDemande(demande));
        List<Demande> demandes = demandeServiceLocal.getDemande(demande);

        if (demandes.isEmpty()) {

            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("Not Exist: ", "Demand does not exist");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);


        } else {
            demandeServiceLocal.deleteDemande(demandes.get(0));
            builder = Response.ok("deleted");

        }

        return builder.build();
    }
}

