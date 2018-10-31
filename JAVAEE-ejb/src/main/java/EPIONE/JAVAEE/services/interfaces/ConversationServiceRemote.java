package EPIONE.JAVAEE.services.interfaces;

import EPIONE.JAVAEE.entities.SentMessage;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface ConversationServiceRemote {
    public void addSimpleConversation();
    List<SentMessage> getListMessages();
}
