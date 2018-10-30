package EPIONE.JAVAEE.services.implementation;

import EPIONE.JAVAEE.entities.Conversation;
import EPIONE.JAVAEE.entities.SentMessage;
import EPIONE.JAVAEE.entities.User;
import EPIONE.JAVAEE.services.interfaces.ConversationServiceLocal;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Stateless
public class ConversationService implements ConversationServiceLocal {

    @PersistenceContext
    EntityManager em;

/*    public void addNewConversationMessage(SentMessage message, List<User> users) {
        Conversation conversation = new Conversation();
        List<Conversation> conversations = new ArrayList<Conversation>();
        em.persist(conversation);
        em.flush();
        System.out.println("ConversationService.addNewConversation()");

        for (User user : users){
            User e = new User();
            System.out.println(conversation);
            conversations.add(conversation);
            e.setConversations(conversations);
            System.out.println(user);
        }
        message.setConversation(conversation);
        em.persist(message);

    }*/
///////////////////////////// Add a new Conversation ////////////////////////////////////////////
    @Override
    public void addNewConversationMessage( int idDoctor, int idPatient) {
        User doctor = em.find(User.class, idDoctor);
        User patient = em.find(User.class , idPatient);

        Conversation conversation = new Conversation();
        Set<User> users = new HashSet<>();
        users.add(doctor);
        users.add(patient);
        conversation.setUsers(users);

        em.persist(conversation);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////
   ////////////////// Add a message to a conversation ///////////////////////////////////////////
    @Override
    public void addMessageToConversation(int idUser, int idConversation, String message) {
        User user = em.find(User.class , idUser);
        Conversation conversation = em.find(Conversation.class , idConversation);
        SentMessage sentMessage = new SentMessage();

        sentMessage.setSender(user);
        sentMessage.setContent(message);
        sentMessage.setConversation(conversation);

        em.persist(sentMessage);
        em.flush();
    }

    //////////////////// Display Conversation by id /////////////////////////////////////////////

    @Override
    public List<SentMessage> getMessagesByConvId(int idConversation) {
        List<SentMessage> messages = null;
        //Query query = em.createQuery("select g from SendMessage g where g.groupeDisc.idGroupe=:groupe", SentMessage.class);
        //query.setParameter("groupe", idConversation);
        return messages;
    }

}
