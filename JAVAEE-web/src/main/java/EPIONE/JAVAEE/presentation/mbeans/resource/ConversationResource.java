package EPIONE.JAVAEE.presentation.mbeans.resource;

import EPIONE.JAVAEE.entities.Conversation;
import EPIONE.JAVAEE.services.interfaces.ConversationServiceLocal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/conversation")
@RequestScoped
public class ConversationResource {
    @PersistenceContext
    EntityManager em;

    @EJB
    ConversationServiceLocal conv;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response addConversation(int idDoctor ) {

        conv.addNewConversationMessage(idDoctor,1);

        return Response.ok().entity("New conversation created ").build();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addMessage/{idConv}")
    public Response addMessageToConv(@PathParam("idConv") int idConv , String message) {

        conv.addMessageToConversation(6 , idConv , message);

        return Response.ok().entity("New conversation created ").build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/displayConv/{idConv}")
    public Response getAllMessagesByConv(@PathParam("idConv") int idConv){
        return Response.ok(conv.getMessagesByConvId(idConv)).build();

    }
}
