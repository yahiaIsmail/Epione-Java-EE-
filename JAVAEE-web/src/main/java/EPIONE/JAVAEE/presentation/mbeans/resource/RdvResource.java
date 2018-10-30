package EPIONE.JAVAEE.presentation.mbeans.resource;

import EPIONE.JAVAEE.presentation.mbeans.util.SendMail;
import EPIONE.JAVAEE.services.interfaces.RdvServiceLocal;
import EPIONE.JAVAEE.services.interfaces.UserServiceLocal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/rdv")
@RequestScoped
public class RdvResource {
    @EJB
    UserServiceLocal userServiceLocal;
    @EJB
    RdvServiceLocal rdvServiceLocal;
    @POST
    @Path("/takeRdv/{emailPatient}/{emailDoctor}/{motifId}/{year}/{month}/{day}/{hour}/{minutes}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String takeRdv(@PathParam(value = "emailPatient") String emailPatient, @PathParam(value = "emailDoctor") String emailDoctor, @PathParam(value = "motifId") int motifId, @PathParam(value = "year") int year, @PathParam(value = "month") int month, @PathParam(value = "hour") int day, @PathParam(value = "hour") int hour, @PathParam(value = "minutes") int minutes) {
        int takeRdvResponse = rdvServiceLocal.takeRvdPatient(emailPatient, emailDoctor, motifId, year, month, day, hour, minutes);
        String responce = "failed";
        if (takeRdvResponse != 0) {
            String bodyPatient = "This mail is Sent to patient to inform him that he took a rendez-vous<br>, " +
                    "Please confirm your attendency by clicking link below.<br>" +
                    "<a href=></a>";
            String bodyDoctor = "This mail is Sent to Doctor to inform him that a Patient he took a rendez-vous<br>," +
                    "                    Please confirm your attendency by clicking link below.<br>" +
                    "                    <a href=></a>";
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
    public String confirmRdvPatient(@DefaultValue("empty") @QueryParam(value = "Token") String token) {
        if (rdvServiceLocal.confirmRdvPatient(token))
            return token + " done";
        return token;
    }

    @GET
    @Path("doctor/confirmRDV")
    @Produces(MediaType.APPLICATION_JSON)
    public String confirmRdvDoctor(@DefaultValue("empty") @QueryParam(value = "Token") String token) {
        if (rdvServiceLocal.confirmRdvDoctor(token))
            return token + " done";
        return token;
    }


}
