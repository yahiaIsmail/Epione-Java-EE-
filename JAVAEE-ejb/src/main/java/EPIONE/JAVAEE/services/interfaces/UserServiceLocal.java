package EPIONE.JAVAEE.services.interfaces;

import EPIONE.JAVAEE.entities.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserServiceLocal {
    List<User> scrapingAllDoctors(String speciality);
    int addDoctors(String firstName,String lastName,String speciality, String state,String email,String password);
}
