package EPIONE.JAVAEE.services.implementation;

import EPIONE.JAVAEE.entities.Address;
import EPIONE.JAVAEE.entities.Conversation;
import EPIONE.JAVAEE.entities.User;
import EPIONE.JAVAEE.services.interfaces.UserServiceRemote;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Base64;
import java.util.List;

@Stateful
public class UserService implements UserServiceRemote {
    @PersistenceContext(unitName = "JAVAEE-ejb")
    EntityManager em;

    @Override
    public int addUser(User user) {
        user.setConfirmation("0");
        user.setAddress(null);
        user.setEnabled(true);
        String token = Base64.getEncoder().encodeToString(user.getUsername().getBytes()) + Base64.getEncoder().encodeToString(user.getPassword().getBytes()) + Base64.getEncoder().encodeToString(user.getEmail().getBytes());
        user.setConfirmationToken(token);
        em.persist(user);
        return user.getId();
    }

    @Override
    public boolean activateUser(String activationToken) {
        try {
            User usr = (User) em.createQuery(
                    "SELECT u FROM User u WHERE u.confirmationToken = :token")
                    .setParameter("token", activationToken)
                    .getSingleResult();
            usr.setConfirmation("1");
            em.merge(usr);
            return true;
        } catch (javax.persistence.NoResultException exp) {
            return false;
        }
    }

    @Override
    public boolean deleteUser(int id) {
        try {
            User usr = (User) em.createQuery("SELECT u FROM User u WHERE u.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
            usr.setEnabled(false);
            em.merge(usr);
            return true;
        } catch (javax.persistence.NoResultException exp) {
            return false;
        }
    }

    @Override
    public boolean addAddress(Address address) {
        try {
            User usr = (User) em.createQuery("SELECT u FROM User u WHERE u.id = :id")
                    .setParameter("id", address.getUser().getId())
                    .getSingleResult();
            usr.setAddress(address);
            em.persist(address);
            em.merge(usr);
            return true;
        } catch (javax.persistence.NoResultException exp) {
            return false;
        }
    }

    @Override
    public User getUserById(int id) {
        try {
            User usr = (User) em.createQuery("SELECT u FROM User u WHERE u.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
            // bech matsirch lazy hibernate exception
            if (!usr.getConversations().isEmpty())
                usr.setConversations(usr.getConversations());

            if (!usr.getMessageDoctors().isEmpty())
                usr.setMessageDoctors(usr.getMessageDoctors());

                return usr;
        } catch (javax.persistence.NoResultException exp) {
            return null;
        }
    }
}
