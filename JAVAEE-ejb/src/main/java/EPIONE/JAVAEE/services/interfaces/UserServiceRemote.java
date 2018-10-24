package EPIONE.JAVAEE.services.interfaces;

import EPIONE.JAVAEE.entities.Address;
import EPIONE.JAVAEE.entities.User;

import javax.ejb.Remote;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Remote
public interface UserServiceRemote {
    public int addUser(User user);

    public boolean activateUser(String activationToken);

    public boolean deleteUser(int id);

    public boolean addAddress(Address address);


    public User getUserById(int id);


    public String testRest();

}
