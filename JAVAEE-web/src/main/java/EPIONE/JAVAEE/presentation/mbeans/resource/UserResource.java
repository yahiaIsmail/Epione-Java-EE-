package EPIONE.JAVAEE.presentation.mbeans.resource;

import EPIONE.JAVAEE.entities.Demande;
import EPIONE.JAVAEE.entities.User;
import EPIONE.JAVAEE.services.implementation.UserService;
import EPIONE.JAVAEE.services.interfaces.DemandeServiceLocal;
import EPIONE.JAVAEE.services.interfaces.UserServiceLocal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


@Path("/users")
@RequestScoped
public class UserResource {


    @EJB
    UserServiceLocal userServiceLocal;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/doctors/{speciality}")
    public List<User> scrapingAllDoctors(
            @PathParam(value = "speciality") String speciality
    ) {
        List<User> listDoc = new ArrayList<User>();
        listDoc = userServiceLocal.scrapingAllDoctors(speciality);
        if (listDoc.isEmpty()) {
            return null;
        } else
            return listDoc;

    }
//    @POST
//    @Path("/adddoctor/{fullName}/{speciality}/{state}/{email}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response addDoctor(@PathParam(value = "fullName")String fullName,
//                          @PathParam(value="speciality")String speciality,
//                          @PathParam(value = "state")String state,
//                              @PathParam(value="email")String email
//    ){

    @POST
    @Path("/adddoctor")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDoctor(User doc) throws UnsupportedEncodingException {

        String password = UUID.randomUUID().toString();
        Response.ResponseBuilder builder = null;
        System.out.println(password + " base password");
        String encode=Base64.getEncoder().encodeToString(password.getBytes());
        System.out.println(encode + " password encrypted");
        System.out.println(new String(Base64.getDecoder().decode(encode),"UTF-8") + " password dencrypted");
        //System.out.println(userServiceLocal.getDoctor(doc));
        if (userServiceLocal.getDoctor(doc).isEmpty()) {
            try {
                int id = userServiceLocal.addDoctors(doc.getFirstName(),
                        doc.getLastName(),
                        doc.getSpeciality(),
                        doc.getState(),
                        doc.getEmail(),
                        encode
                );
                if (id == -1) {
                    sendMail(doc.getEmail(),
                            "Account not added",
                            "you're account has not been added please check your informations in your demand");
                    Map<String, String> responseObj = new HashMap<>();
                    responseObj.put("Unvalid data:", "cannot scrap doctor, please enter a valid doctor data ");
                    builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
                } else {

                    sendMail(doc.getEmail(),
                            "Account added",
                            "you're account has been added your password is:" + password);

                    builder = Response.ok(id);
                }

            } catch (Exception e) {

                Map<String, String> responseObj = new HashMap<>();
                responseObj.put("error", e.getMessage());
                builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
            }
        } else {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("Duplicated: ", " Doctor already registred !");
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }


        return builder.build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/registereddoctors")
    public List<User> displayRegistredDoctors() {
        return userServiceLocal.getAllDoctors();
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
                    return new PasswordAuthentication("haddadmoezPI@gmail.com", "PI_mail_test");
                }
            };
            Session session = Session.getInstance(properties, auth);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("haddadmoezPI@gmail.com"));
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


    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/auth")
    public Response authenticateUser(User u) throws Exception {
        // Authenticate the user using the credentials provided
        if(authenticate(u) == false){
            System.out.println("Auth failed, Exiting with FORBIDDEN status");
            return Response.status(Response.Status.FORBIDDEN).entity("Authentification failed !").build();
        }
        System.out.println("Authentification passed!");
        //User user = userServiceLocal.findUser(u);
        //String token = issueToken(user);
        //System.out.println("Our token is now : "+token);

     //   return Response.ok(token).header("Authorization", token).build();
        return Response.ok("Authentification passed successfully !").build();
    }

    private boolean authenticate(User u) throws Exception{
        if(userServiceLocal.login(u) == false)
            return false;
        return true;
    }

    @Path("logOut")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response logOut(@QueryParam(value = "idUser") int idUser) {
      //  return Response.ok(userService.logOut(idUser)).build();
        return Response.ok().build();
    }

    @POST
    @Path("/addPatient")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    public String addPatient(User user) {
        int responce = userServiceLocal.addPatient(user);
        if (responce == 0)
            return " error";
        return "added";
    }

    @GET
    @Path("user/activate")
    @Produces(MediaType.APPLICATION_JSON)
    public String activatePatient(@DefaultValue("empty") @QueryParam(value = "activationToken") String activationToken) {
        if (userServiceLocal.activatePatient(activationToken))
            return "activated";
        return "failed";
    }

    @POST
    @Path("/takeRdv/{emailPatient}/{emailDoctor}/{motifId}/{year}/{month}/{day}/{hour}/{minutes}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String takeRdv(@PathParam(value = "emailPatient") String emailPatient, @PathParam(value = "emailDoctor") String emailDoctor, @PathParam(value = "motifId") int motifId, @PathParam(value = "year") int year, @PathParam(value = "month") int month, @PathParam(value = "hour") int day, @PathParam(value = "hour") int hour, @PathParam(value = "minutes") int minutes) {
        int takeRdvResponse = userServiceLocal.takeRvdPatient(emailPatient, emailDoctor, motifId, year, month, day, hour, minutes);
        if (takeRdvResponse != 0) {
            return "added";
        }
        return "failed";
    }
}
