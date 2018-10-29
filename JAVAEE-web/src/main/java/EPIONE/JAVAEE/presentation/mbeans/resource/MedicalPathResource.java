package EPIONE.JAVAEE.presentation.mbeans.resource;

import EPIONE.JAVAEE.entities.MedicalPath;
import EPIONE.JAVAEE.entities.MedicalVisit;
import EPIONE.JAVAEE.entities.PathDoctors;
import EPIONE.JAVAEE.entities.RDV;
import EPIONE.JAVAEE.services.interfaces.MedicalPathServiceLocal;


import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Status;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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
        if (PathgenId != -1) {
            return Response.ok().entity("Id of the generated Medical Path :" + PathgenId).build();
        } else
            return Response.status(Response.Status.BAD_REQUEST).build();

    }
    /****END Adding new path for patient Web****/
    /**
     *
     */
    /************getPathById************************/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getPathById/{pathId}")
    public Response getPathById(@PathParam(value = "pathId") int pathId) {
        MedicalPath path = medipath.getPathById(pathId);
        // return Response.ok(medipath.getPathById(pathId)).build();
        if (path != null) {
            return Response.ok().entity(path).build();
        } else
            return Response.status(Response.Status.NO_CONTENT).build();
    }

    /************UpdatePathById************************/
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/updatePath/{pathId}")
    public Response updateMedicalPath(@PathParam(value = "pathId") int pathId, MedicalPath path) {
        // MedicalPath path=medipath.getPathById(pathId);
        medipath.updatePathComponent(pathId, path);
        return Response.status(Status.STATUS_COMMITTED).build();
    }

    /****Adding new Doctor for patient path Web****/
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addDoctorToPath/{pathId}/{doctorId}/{description}")
    public Response addDoctorToPatientPath(@PathParam(value = "pathId") int pathId,
                                           @PathParam(value = "doctorId") int doctorId,
                                           @PathParam(value = "description") String description,
                                           PathDoctors pathDoctors
    ) {

        String x = medipath.addDoctorsToPath(pathId, doctorId, pathDoctors, description);
        if (!x.equals("Patient")) {
            if (x.equals("Added Doctor to path Successfully !")) {
                return Response.ok().entity("Added Doctor to path Successfully !" + " DoctorId\n" + doctorId + " \n" + "PathId" + pathId).build();
            } else
                return Response.status(Response.Status.FOUND).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
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

        String x = medipath.removeDoctorFromPath(pathId, doctorId);
        if (x.equals("ok"))
            return Response.status(Response.Status.ACCEPTED).build();
        else
            return Response.status(Response.Status.BAD_REQUEST).build();

    }
    /****END Removing Doctor from patient path Web****/
    /**
     *
     */
    /******Update MedicalVisitStatus************************/

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/updateMedicalVisitStatus/{pathDocId}")
    public Response updateMedicalPath(@PathParam(value = "pathDocId") int pathDocId, MedicalVisit medicalVisit) {
        // MedicalPath path=medipath.getPathById(pathId);
        medipath.updateMedicalVisitStatus(pathDocId, medicalVisit);
        return Response.status(Status.STATUS_COMMITTED).build();
    }
    /***************************** get All RDV Patient ********************************************/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllRdvPatient/{idPatient}")
    public Response getAllRdvPatientList(@PathParam(value = "idPatient") int idPatient)
    {
        List<RDV> list =medipath.getAllRDVPatient(idPatient);
        return Response.ok().entity(list).build();
    }
}
