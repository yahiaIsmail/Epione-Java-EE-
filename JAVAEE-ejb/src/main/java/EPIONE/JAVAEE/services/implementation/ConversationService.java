package EPIONE.JAVAEE.services.implementation;

import EPIONE.JAVAEE.entities.Conversation;
import EPIONE.JAVAEE.entities.SentMessage;
import EPIONE.JAVAEE.entities.User;
import EPIONE.JAVAEE.services.interfaces.ConversationServiceLocal;
import EPIONE.JAVAEE.services.interfaces.ConversationServiceRemote;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.*;

@Stateless
public class ConversationService implements ConversationServiceLocal , ConversationServiceRemote {

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

        System.out.println(doctor.toString());
        Conversation conversation = new Conversation();
        //Set<User> users = new HashSet<User>();
        //users.add(doctor);
        //users.add(patient);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        conversation.setDateCreated(timestamp);
        //conversation.setUsers(users);

        List<Conversation> conversations = new ArrayList<Conversation>();
        conversations.add(conversation);
        doctor.setConversations(conversations);
//        patient.setConversations(conversations);

        System.out.println(conversation);
        em.persist(conversation);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////
   ////////////////// Add a message to a conversation ///////////////////////////////////////////
    @Override
    public void addMessageToConversation(int idUser, int idConversation, String message) {
        User user = em.find(User.class , idUser);
        Conversation conversation = em.find(Conversation.class , idConversation);
        SentMessage sentMessage = new SentMessage();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        sentMessage.setSender(user);
        sentMessage.setContent(message);
        sentMessage.setConversation(conversation);
        sentMessage.setDateCreated(timestamp);

        em.persist(sentMessage);
        em.flush();
    }

    //////////////////// Display Conversation by id /////////////////////////////////////////////

    @Override
    public List<SentMessage> getMessagesByConvId(int idConversation) {
        List<SentMessage> messages = null ;
       // TypedQuery<SentMessage> query = em.createQuery("select m from SentMessage m where m.conversation.id=:idConv", SentMessage.class);
       // query.setParameter("idConv", idConversation);
       // messages= query.getResultList();
        return messages;
    }

    ////////////////////////// Delete conversation ///////////////////////////////////////////////

    @Override
    public boolean deleteConversation(int idUser, int idConversation) {
        return false;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////Remote JSF ///////////////////////////////////////////////////////////////
    @Override
    public void addSimpleConversation() {
        Conversation conv = new Conversation();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //conv.setDateCreated(timestamp);
        em.persist(conv);
    }
}
