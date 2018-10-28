package EPIONE.JAVAEE.presentation.mbeans.resource;

import EPIONE.JAVAEE.entities.MedicalPath;
import EPIONE.JAVAEE.services.interfaces.MedicalPathServiceLocal;


import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/medicalPath")
@RequestScoped
public class MedicalPathResource {

    @PersistenceContext
    EntityManager em;

    @EJB
    MedicalPathServiceLocal medipath;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get")
    public String hello() {
        String x = medipath.test();
        return x;
    }

    /****Adding new path for patient Web****/
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/CreatePath/{rdv}")
    public Response createPath(@PathParam(value = "rdv") int rdv, MedicalPath path) {

        int PathgenId;

        PathgenId = medipath.createPathForPatient(rdv, path);
        return Response.ok().entity(PathgenId).build();

    }
    /****END Adding new path for patient Web****/
    /**
     *
     */
    /****Adding new Doctor for patient path Web****/
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addDoctorToPath/{pathId}/{doctorId}")
    public Response addDoctorToPatientPath(@PathParam(value = "pathId") int pathId,
                                           @PathParam(value = "doctorId") int doctorId) {


        medipath.addDoctorsToPath(pathId, doctorId);
        return Response.ok().entity("Added Doctor to path Successfully !" + " DoctorId\n" + doctorId + " \n" + "PathId" + pathId).build();

    }
    /**** END Adding new Doctor for patient path Web****/
    /**
     *
     */
    /****Removing Doctor from patient path Web****/
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/removeDoctorFromPath/{pathId}/{doctorId}")
    public Response removeDoctorFromPath(@PathParam(value = "pathId") int pathId,
                                         @PathParam(value = "doctorId") int doctorId) {

        medipath.removeDoctorFromPath(pathId, doctorId);
        return Response.ok().entity("removed Doctor from patient path Successfully !").build();

    }
    /****END Removing Doctor from patient path Web****/
    /**
     *
     */


}
