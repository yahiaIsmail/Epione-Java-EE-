package EPIONE.JAVAEE.services.interfaces;

import javax.ejb.Remote;

@Remote
public interface ConversationServiceRemote {
    public void addSimpleConversation();
}
