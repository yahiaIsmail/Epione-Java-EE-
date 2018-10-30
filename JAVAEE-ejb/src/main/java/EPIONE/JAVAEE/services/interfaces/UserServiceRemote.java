package EPIONE.JAVAEE.services.interfaces;

import EPIONE.JAVAEE.entities.User;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface UserServiceRemote {
    List<User> scrapingAllDoctors(String speciality);

    int addDoctors(String firstName, String lastName, String speciality, String state, String email, String password);

    List<User> getDoctor(User user);

    List<User> getAllDoctors();

    int addPatient(User user);

    public boolean activatePatient(String activationToken);

    int takeRvdPatient(String emailPatient, String emailDoctor, int motif, int year, int month, int day, int hour, int minutes);
    public boolean login(User u) throws Exception;

    boolean confirmRdvPatient(String token);

    boolean confirmRdvDoctor(String token);

}
