package EPIONE.JAVAEE.presentation.mbeans.resource;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.print.Doc;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import EPIONE.JAVAEE.entities.DoctorData;
import EPIONE.JAVAEE.entities.Motif;
import EPIONE.JAVAEE.entities.User;
import EPIONE.JAVAEE.services.interfaces.MotifServiceLocal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sun.plugin2.util.PojoUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("/motif")
@RequestScoped
public class MotifResource {

    @PersistenceContext
    EntityManager em;

    @EJB
    MotifServiceLocal ms ;


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/get")
    public String hello() {

        return "hello";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response ajouterMotif(Motif motif) {

		ms.ajouterMotif(motif);

        return Response.ok().entity("New motif created ").build();

	}

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update/{id}/{description}")
    public Response modifierMotif(@PathParam(value = "id")int id, @PathParam(value = "description")String description) {

        ms.modifierMotif(id,description);
        Motif motif = em.find(Motif.class,id);
        if(motif.getDescription().equals(description))
        {
            return Response.ok().entity("The description is now "+description).build();
        }
        else
        {
            return Response.noContent().entity("The description didn't change to "+description).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/affect/{idMotif}/{idDoc}")
    public Response affecterMotif(@PathParam(value = "idMotif")int idMotif,@PathParam(value = "idDoc")int idDoc) {

        ms.affecterMotifDoctor(idMotif,idDoc);
        Motif motif =em.find(Motif.class,idMotif);
        if(motif.getDoctor()!=null)
        {
            return Response.ok().entity("The motif "+idMotif+" is affected to doctor "+idDoc).build();
        }
        else
        {
            return Response.noContent().entity("The motif "+idMotif+" is not affected to doctor "+idDoc).build();
        }

    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response affecterMotif(@PathParam(value = "id")int id) {

        ms.supprimerMotif(id);
        Motif motif =em.find(Motif.class,id);
        if(motif==null)
        {
            return Response.ok().entity("The motif was deleted").build();
        }
        else
        {
            return Response.noContent().entity("The motif could not be deleted").build();
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/motifDoctor/{idDoc}")
    public Response listerMotifByDocResource(@PathParam(value = "idDoc")int idDoc) {
        User d = em.find(User.class,idDoc);

        if(ms.listerMotifByDoc(d)!=null)
        {
            return Response.ok(ms.listerMotifByDoc(d)).build();
        }
        else
        {
            return Response.noContent().entity("No motif found ").build();
        }
    }

}
