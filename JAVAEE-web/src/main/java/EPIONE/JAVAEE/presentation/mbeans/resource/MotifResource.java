package EPIONE.JAVAEE.presentation.mbeans.resource;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import EPIONE.JAVAEE.entities.Motif;
import EPIONE.JAVAEE.services.interfaces.MotifServiceLocal;

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
    @Path("/add/{description}")
    public Response ajouterMotif(@PathParam(value = "description")String description) {

		System.out.println(description);
		Motif motif = new Motif();
		motif.setDescription(description);
		ms.ajouterMotif(motif);

        return Response.ok().entity("New motif created "+description).build();

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


}
