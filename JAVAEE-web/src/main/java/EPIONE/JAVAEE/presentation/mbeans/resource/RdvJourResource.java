package EPIONE.JAVAEE.presentation.mbeans.resource;

import EPIONE.JAVAEE.services.interfaces.RdvJourServiceLocal;


import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


/**
 * Created by Ellouze Skander on 27/10/2018.
 */

@Path("/rdvJour")
@RequestScoped
public class RdvJourResource {

    @PersistenceContext
    EntityManager em;

    @EJB
    RdvJourServiceLocal rs ;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/get")
    public String hello() {

        return "hello";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/programmeJourne/{id}/{year}/{month}/{day}")
    public Response ProgrammeJourne(@PathParam(value = "id")int id,
                                    @PathParam(value = "year")int year,
                                    @PathParam(value = "month")int month,
                                    @PathParam(value = "day")int day) {


        if(rs.ProgrammeJourneJ(id,year,month,day)!=null)
        {
            return Response.ok(rs.ProgrammeJourneJ(id,year,month,day)).build();
        }
        else
        {
            return Response.ok().entity("No appointment "+year+"/"+month+"/"+day).build();
        }

    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/programmeJourneJ/{id}")
    public Response ProgrammeJourneJ(@PathParam(value = "id")int id) throws IOException {
    //rs.generateExcel(id);
        return Response.ok().entity("okk").build();
    }
}
