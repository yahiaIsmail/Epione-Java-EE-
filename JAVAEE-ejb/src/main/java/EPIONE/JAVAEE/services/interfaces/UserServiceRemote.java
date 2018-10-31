package EPIONE.JAVAEE.services.interfaces;

import EPIONE.JAVAEE.entities.RDV;
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

    public boolean login(User u) throws Exception;

    boolean updateUserAddress(User user);

    boolean sendMessagePatient2Doctor(int doctorId, String object, String message);

    User getUserByEmail(String email);


}
