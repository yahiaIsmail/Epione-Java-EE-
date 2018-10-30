package EPIONE.JAVAEE.services.interfaces;

import EPIONE.JAVAEE.entities.SentMessage;
import EPIONE.JAVAEE.entities.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ConversationServiceLocal {
    void addNewConversationMessage( int idDoctor , int idPatient);
    void addMessageToConversation(int idUser , int idConversation , String message);
    List<SentMessage> getMessagesByConvId(int idConversation);
}
