package EPIONE.JAVAEE.presentation.mbeans.resource;

import EPIONE.JAVAEE.entities.*;
import EPIONE.JAVAEE.filters.Secured;
import EPIONE.JAVAEE.services.interfaces.MedicalPathServiceLocal;


import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.JsonObject;
import javax.jws.soap.SOAPBinding;
import javax.mail.*;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Status;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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
    public void sendMail(String mailTo, String subject, String body) {
        String returnStatement = null;
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        try {
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("amuari01@gmail.com", "mr8w8901g");
                }
            };
            Session session = Session.getInstance(properties, auth);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("amuari01@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
            message.setSentDate(new Date());
            message.setSubject(subject);
            message.setText(body);
            returnStatement = "The e-mail was sent successfully";
            System.out.println(returnStatement);
            Transport.send(message);
        } catch (Exception e) {
            returnStatement = "error in sending mail";
            e.printStackTrace();
        }

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
            RDV rdv1=em.find(RDV.class,rdv);

            sendMail(rdv1.getUsers().getEmail(),
                    "Your medical Path of the RDV :"+rdv+"",
                    " Your medical Path of the RDV :"+rdv+"has been created successfully wait till the doctor add doctors to you path"+PathgenId+ "!");
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
                MedicalPath user =em.find(MedicalPath.class,pathId);

                sendMail(user.getRendezVous().getUsers().getEmail(),
                        "Adding a Doctor to your path",
                        " Your medical Path was updated : the doctor "+user.getRendezVous().getUsers().getFirstName()+"," +user.getRendezVous().getUsers().getLastName()+"  with the speciality  :"+user.getRendezVous().getUsers().getSpeciality()+"has been added successfully !");
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
    /****************************** get path doc by id web********************************/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getPathDocById/{pathId}")
    public Response getPathDocById(@PathParam(value = "pathId") int pathId) {
        List<PathDoctors> path = medipath.getDoctorsInPathById(pathId);
        // return Response.ok(medipath.getPathById(pathId)).build();
        if (!path.isEmpty()) {
            return Response.ok().entity(path).build();
        } else
            return Response.status(Response.Status.NO_CONTENT).build();
    }
    /******************************************get all paths for patient************************/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getAllPathsPatient/{patientId}")
    public Response getAllpathsForPatient(@PathParam(value = "patientId") int patientId) {
        List<MedicalPath> path = medipath.allPathsForConnectedPatient(patientId);
        // return Response.ok(medipath.getPathById(pathId)).build();
        if (!path.isEmpty()) {
            return Response.ok().entity(path).build();
        } else
            return Response.status(Response.Status.NO_CONTENT).build();
    }
    /************************************** multi search ***********************************/
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/smartSearch/{patientId}")
    public Response smartSearchPatientPath(@PathParam(value = "patientId") int patientId,MedicalPath medicalPath) {
        List<MedicalPath> path = medipath.searchPathByOption(patientId,medicalPath);
        // return Response.ok(medipath.getPathById(pathId)).build();
        if (!path.isEmpty()) {
            return Response.ok().entity(path).build();
        } else
            return Response.status(Response.Status.NO_CONTENT).build();
    }
    /************************** doctor visits *************************************/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getDoctorVisits/{idDoctor}")
    public Response getDoctorVisits(@PathParam(value = "idDoctor") int idDoctor) {
        List<MedicalVisit> path = medipath.getDoctorAllvisits(idDoctor);
        // return Response.ok(medipath.getPathById(pathId)).build();
        if (!path.isEmpty()) {
            return Response.ok().entity(path).build();
        } else
            return Response.status(Response.Status.NO_CONTENT).build();
    }
    /***************************doctors in path they can update path *******************************************/
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/updatePathByDoctorPath/{justif}/{idPath}")
    public Response updatePathByDoctorPath(@PathParam("justif") String justif,@PathParam("idPath") int idPath, PathDoctors pathDoctors) {
            medipath.updateDoctorInPath(justif,idPath ,pathDoctors);
            // return Response.ok(medipath.getPathById(pathId)).build();


            return Response.ok().build();
    }
    /************************** doctor paths all *************************************/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/doctorPaths")
    public Response getDoctorVisits() {
        List<PathDoctors> path = medipath.getAllPathForDoctor();
        // return Response.ok(medipath.getPathById(pathId)).build();
        List<Integer> pathsid=new ArrayList<>();
        if (!path.isEmpty()) {
            for(PathDoctors p : path)
            {
                pathsid.add(p.getPath().getId());
            }
            return Response.ok().entity(path).build();
        } else
            return Response.status(Response.Status.NO_CONTENT).build();
    }
    /****************************** nearby doctors ***************************************/
    @Secured
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/nearbyDoctors/{el1}/{el2}/{idPatient}")
    public Response nearbyDoctors(@PathParam(value = "el1") double el1,@PathParam(value = "el2") double el2,@PathParam(value = "idPatient") int idPatient)
    {
        List<User> list =medipath.nearbyDoctors(el1,el2,idPatient);
        return Response.ok().entity(list).build();
    }
}
