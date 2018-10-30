package EPIONE.JAVAEE.presentation.mbeans.websocket;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.Path;

@ServerEndpoint("/example")
//@Path("/wsendpoint")
public class WebsocketEndpoint {

    @OnOpen
    public void open(/*Session session*/) {
        //log.info("Open session:" + session.getId());
        System.out.println("Client is now connected !");
    }

    @OnClose
    public void close(/*Session session, CloseReason c*/) {
        //log.info("Closing:" + session.getId());
        System.out.println("Client is now disconnected !");
    }

    @OnMessage
    public String onMessage(String message){
        System.out.println("Recieve from client "+message);
        //Thread.currentThread();
        String replyMessage = "Please enter now the new date of the RDV you want to change ";
        System.out.println("send to client "+replyMessage);
        System.out.println("Recieve from client "+message);
        String replyMessage1 = "Please choose one of these dates ";
        System.out.println("send to client "+replyMessage1);
        return replyMessage;
    }
}
