package EPIONE.JAVAEE.presentation.mbeans.websocket;

import EPIONE.JAVAEE.services.interfaces.RdvServiceLocal;
import javafx.scene.control.Alert;

import javax.ejb.EJB;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.Path;

@ServerEndpoint("/example")
//@Path("/wsendpoint")
public class WebsocketEndpoint {

    @EJB
    RdvServiceLocal rdvServiceLocal;

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
        System.out.println("Recieve from client "+message);
        //String replyMessage = null;
        /*if(message.contains("%s/%s/%s %s-%s")){*/
            String yearString = message.substring(0,4);
            int year = Integer.parseInt(yearString);
            String monthString = message.substring(5,7);
            int month = Integer.parseInt(monthString);
            String dayString = message.substring(8,10);
            int day = Integer.parseInt(dayString);

            String hourString = message.substring(11,13);
            int hour = Integer.parseInt(hourString);
            String minuteString = message.substring(14 , 16);
            int minute = Integer.parseInt(minuteString);
            System.out.println(yearString+" "+monthString+" "+dayString);
            System.out.println(hourString+" "+minuteString);

            rdvServiceLocal.modifyRdvDate(1,year,month,day,hour,minute);

            String replyMessage = "RDV changed";
            System.out.println("send to client "+replyMessage);
            return replyMessage;
       /* }
        else{
            String replyMessage1 = "Error";
            System.out.println("send to client "+replyMessage1);
            return replyMessage1;
        }*/

    }
}
