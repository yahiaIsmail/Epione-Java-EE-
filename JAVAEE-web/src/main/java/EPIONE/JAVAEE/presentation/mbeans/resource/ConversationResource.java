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
    ConversationServiceLocal convLocal;

    ////////////////////////////// Add a new conversation /////////////////////////////////////
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response addConversation(int idDoctor ) {

        convLocal.addNewConversationMessage(idDoctor,7);

        return Response.ok().entity("New conversation created ").build();

    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////// Add a new message to a conversation //////////////////////////////

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addMessage/{idConv}")
    public Response addMessageToConv(@PathParam("idConv") int idConv , String message) {

        convLocal.addMessageToConversation(6 , idConv , message);

        return Response.ok().entity("New conversation created ").build();

    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    //////////////// Display all messages of a conversation ////////////////////////////////////

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/displayConv/{idConv}")
    public Response getAllMessagesByConv(@PathParam("idConv") int idConv){
        return Response.ok(convLocal.getMessagesByConvId(idConv)).build();

    }
    ///////////////////////////////////////////////////////////////////////////////////////////
}
