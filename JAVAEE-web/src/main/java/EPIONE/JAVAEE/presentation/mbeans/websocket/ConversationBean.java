package EPIONE.JAVAEE.presentation.mbeans.websocket;

import EPIONE.JAVAEE.entities.Conversation;
import EPIONE.JAVAEE.services.interfaces.ConversationServiceLocal;
import EPIONE.JAVAEE.services.interfaces.ConversationServiceRemote;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class ConversationBean {
    private Conversation conversation;

    @EJB
    ConversationServiceRemote convs;

    @PostConstruct
    public void init() {
        Conversation conv = new Conversation();

    }

    public void addConv(){
        try{
        convs.addSimpleConversation();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public ConversationServiceRemote getConvs() {
        return convs;
    }

    public void setConvs(ConversationServiceRemote convs) {
        this.convs = convs;
    }
}
