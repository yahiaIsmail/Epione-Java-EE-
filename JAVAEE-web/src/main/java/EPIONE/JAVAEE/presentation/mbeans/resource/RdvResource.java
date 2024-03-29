package EPIONE.JAVAEE.presentation.mbeans.resource;

import EPIONE.JAVAEE.entities.RDV;
import EPIONE.JAVAEE.entities.User;
import EPIONE.JAVAEE.filters.Secured;
import EPIONE.JAVAEE.presentation.mbeans.util.SendMail;
import EPIONE.JAVAEE.services.interfaces.RdvServiceLocal;
import EPIONE.JAVAEE.services.interfaces.UserServiceLocal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Path("/rdv")
@RequestScoped
public class RdvResource {
    @EJB
    UserServiceLocal userServiceLocal;
    @EJB
    RdvServiceLocal rdvServiceLocal;


    @POST
    @Path("/takeRdv/{emailPatient}/{emailDoctor}/{motifId}/{year}/{month}/{day}/{hour}/{minutes}")
    @Produces(MediaType.APPLICATION_JSON)
    public String takeRdv(@PathParam(value = "emailPatient") String emailPatient, @PathParam(value = "emailDoctor") String emailDoctor, @PathParam(value = "motifId") int motifId, @PathParam(value = "year") int year, @PathParam(value = "month") int month, @PathParam(value = "hour") int day, @PathParam(value = "hour") int hour, @PathParam(value = "minutes") int minutes) {
        int takeRdvResponse = rdvServiceLocal.takeRvdPatient(emailPatient, emailDoctor, motifId, year, month, day, hour, minutes);
        String responce = "failed";
        if (takeRdvResponse != 0) {
            User patient = userServiceLocal.getUserByEmail(emailPatient);
            User doctor = userServiceLocal.getUserByEmail(emailDoctor);
            String bodyPatient = "This mail is Sent to patient to inform him that he took a rendez-vous<br>, " +
                    "Please confirm your attendency by clicking link below.<br>" +
                    "<a href=" + "htttp://localhost:18080/JAVAEE-web/rest/rdv/user/confirmRDV?Token=" + patient.getConfirmationToken() + "&rdvId=" + takeRdvResponse + ">Click here</a>";
            String bodyDoctor = "This mail is Sent to Doctor to inform him that a Patient he took a rendez-vous<br>," +
                    "                    Please confirm your attendency by clicking link below.<br>" +
                    "                    <a href=" + "htttp://localhost:18080/JAVAEE-web/rest/rdv/doctor/confirmRDV?Token=" + patient.getConfirmationToken() + "&rdvId=" + takeRdvResponse + ">Click here</a>";
            if (SendMail.mail(emailPatient, "Confirm RDV", bodyPatient))
                responce = "sent";
            else
                responce = "failed to send email patient";
            if (SendMail.mail(emailDoctor, "Confirm RDV", bodyDoctor))
                responce = "sent";
            else
                responce = "failed to send email doctor";
        }
        return responce;
    }

    @GET
    @Path("user/confirmRDV")
    @Produces(MediaType.APPLICATION_JSON)
    public String confirmRdvPatient(@DefaultValue("empty") @QueryParam(value = "Token") String token, @DefaultValue("1") @QueryParam(value = "rdvId") int rdvId) {
        if (rdvServiceLocal.confirmRdvPatient(token, rdvId))
            return token + " done";
        return token;
    }

    @GET
    @Path("doctor/confirmRDV")
    @Produces(MediaType.APPLICATION_JSON)
    public String confirmRdvDoctor(@DefaultValue("empty") @QueryParam(value = "Token") String token, @DefaultValue("1") @QueryParam(value = "rdvId") int rdvId) {
        if (rdvServiceLocal.confirmRdvDoctor(token, rdvId))
            return token + " done";
        return token;
    }

    @POST
    @Path("modify/date/{id}/{year}/{month}/{day}/{hour}/{minutes}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String modifyRdvDate(@PathParam(value = "id") int id, @PathParam(value = "year") int year, @PathParam(value = "month") int month, @PathParam(value = "hour") int day, @PathParam(value = "hour") int hour, @PathParam(value = "minutes") int minutes) {
        if (rdvServiceLocal.modifyRdvDate(id, year, month, day, hour, minutes))
            return "modified";
        return "failed";
    }

    @Secured
    @GET
    @Path("modify/motif")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String modifyRdvDate(@QueryParam(value = "rdvId") int rdvId, @QueryParam(value = "motifId") int motifId) {
        if (rdvServiceLocal.modifyRdvMotif(rdvId, motifId))
            return "modified";
        return "failed";
    }

    @GET
    @Path("/cancel")
//    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String cancelRdv(@QueryParam(value = "rdvId") int rdvId) {
        Map<String, User> map = rdvServiceLocal.cancelRdv(rdvId);
        if (map == null)
            return "failed";
        String body = "Patient " + map.get("patient").getFirstName() + " " + map.get("patient").getLastName() + " a annule son Rdv avec Medecin" +
                " " + map.get("doctor").getFirstName() + " " + map.get("doctor").getLastName();
        if (SendMail.mail(map.get("doctor").getEmail(), "Canceled RDV", body) && SendMail.mail(map.get("doctor").getEmail(), "Canceled RDV", body))
            return "canceld";
        return "failed to send mail";
    }

    @GET
    @Path("/complete")
//    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String completeRdv(@QueryParam(value = "rdvId") int rdvId) {
        Map<String, User> map = rdvServiceLocal.confirmRdv(rdvId);
        if (map == null)
            return "failed";
        String body = "Patient " + map.get("patient").getFirstName() + " " + map.get("patient").getLastName() + " a confirme son Rdv avec Medecin" +
                " " + map.get("doctor").getFirstName() + " " + map.get("doctor").getLastName();
        if (SendMail.mail(map.get("doctor").getEmail(), "Confirmed RDV", body) && SendMail.mail(map.get("doctor").getEmail(), "Canceled RDV", body))
            return "confirmed";
        return "failed to send mail";
    }

    @Secured
    @GET
    @Path("/motif/change")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String selectRdvMotif(@QueryParam(value = "rdvId") int rdvId, @QueryParam(value = "motifId") int motifId) {
        User doctor = rdvServiceLocal.selectRdvMotif(rdvId, motifId);
        if (doctor == null)
            return "Error";
        if (SendMail.mail(doctor.getEmail(), "Modification de motif", "Un patient a changer de motif pour son Rdv"))
            return "Motif modifier";
        return "error send mail";
    }

    @GET
    @Path("/doctor")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RDV> searchRdvByDoctor(@QueryParam(value = "doctorId") int doctorId) {
        List<RDV> rdvs = rdvServiceLocal.searchRdvByDoctor(doctorId);
        return rdvs;
    }

    @GET
    @Path("/patient")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RDV> searchRdvByPatient(@QueryParam(value = "patientId") int patientId) {
        List<RDV> rdvs = rdvServiceLocal.searchRdvByPatient(patientId);
        return rdvs;
    }

    @GET
    @Path("/confirmed")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RDV> searchRdvConfirmed() {
        List<RDV> rdvs = rdvServiceLocal.searchRdvConfirmed();
        return rdvs;
    }
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RDV> getAllRdv(){
        return rdvServiceLocal.getAllRdv();
    }


}
