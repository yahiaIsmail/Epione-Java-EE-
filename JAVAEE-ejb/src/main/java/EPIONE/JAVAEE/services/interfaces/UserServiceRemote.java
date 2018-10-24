package EPIONE.JAVAEE.services.interfaces;

import EPIONE.JAVAEE.entities.User;

import javax.ejb.Remote;
import java.io.IOException;
import java.util.List;

@Remote
public interface UserServiceRemote {
    List<User>scrapingAllDoctors();
}
