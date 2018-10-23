package EPIONE.JAVAEE.services.interfaces;

import EPIONE.JAVAEE.entities.Address;
import EPIONE.JAVAEE.entities.User;

import javax.ejb.Remote;

@Remote
public interface UserServiceRemote {
    public int addUser(User user);

    public boolean activateUser(String activationToken);

    public boolean deleteUser(int id);

    public boolean addAddress(Address address);

    public User getUserById(int id);

}
