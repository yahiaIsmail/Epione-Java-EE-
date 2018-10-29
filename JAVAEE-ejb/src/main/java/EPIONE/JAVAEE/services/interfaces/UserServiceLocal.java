package EPIONE.JAVAEE.services.interfaces;

import EPIONE.JAVAEE.entities.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserServiceLocal {
    List<User> scrapingAllDoctors(String speciality);
    int addDoctors(String firstName,String lastName,String speciality, String state,String email,String password);
    List<User> getDoctor(User user);
    List<User>  getAllDoctors();
    int addPatient(String firstName, String lastName, String username,String email, String Password);
    public boolean activatePatient(String activationToken);
    int takeRvdPatient(String emailPatient, String emailDoctor);
}
