package EPIONE.JAVAEE.presentation.mbeans.websocket;

import EPIONE.JAVAEE.entities.Conversation;
import EPIONE.JAVAEE.entities.SentMessage;
import EPIONE.JAVAEE.services.interfaces.ConversationServiceLocal;
import EPIONE.JAVAEE.services.interfaces.ConversationServiceRemote;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@ManagedBean
@RequestScoped
public class ConversationBean {
    private Conversation conversation;

    private SentMessage message;
    private List<SentMessage> sentMessages;

    private String input ;
    Timer timer = new Timer();
    @EJB
    ConversationServiceRemote convs;

    @EJB
    ConversationServiceLocal convLocal;

    @PostConstruct
    public void init() {
        conversation = new Conversation();
        message= new SentMessage();
        sentMessages = convLocal.getMessagesByConvId(8);
        System.out.println(message);
        System.out.println(input);
        //timer.schedule((TimerTask) convLocal.getMessagesByConvId(8), 3000);
    }

    public void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

    public void addMessageToConv(int idConv ,String message){
        convLocal.addMessageToConversation(7,idConv,message);
       // timer.schedule((TimerTask) convLocal.getMessagesByConvId(8), 3000);
        System.out.println(Thread.currentThread().getName());

    }

    public void addConv(){
        try{
        //convs.addSimpleConversation();
            convLocal.addNewConversationMessage(6,7);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public SentMessage getMessage() {
        return message;
    }

    public void setMessage(SentMessage message) {
        this.message = message;
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

    public List<SentMessage> getSentMessages() {
        return sentMessages;
    }

    public ConversationServiceLocal getConvLocal() {
        return convLocal;
    }

    public void setConvLocal(ConversationServiceLocal convLocal) {
        this.convLocal = convLocal;
    }

    public void setSentMessages(List<SentMessage> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
