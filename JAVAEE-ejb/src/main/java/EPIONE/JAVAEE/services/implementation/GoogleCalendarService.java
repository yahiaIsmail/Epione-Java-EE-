package EPIONE.JAVAEE.services.implementation;

import EPIONE.JAVAEE.services.interfaces.GoogleCalendarServiceLocal;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;

import javax.ejb.Stateless;
import java.io.IOException;

/**
 * Created by Ellouze Skander on 28/10/2018.
 */

@Stateless
public class GoogleCalendarService implements GoogleCalendarServiceLocal {

    @Override
    public void synchronizeGoogleCalendar(String adr) throws IOException {
        adr ="skanderellouze94@gmail.com";
        Event event = new Event()
                .setSummary("Google I/O 2015")
                .setLocation("800 Howard St., San Francisco, CA 94103")
                .setDescription("A chance to hear more about Google's developer products.");

     //   Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
     //           .setApplicationName(APPLICATION_NAME)
     //           .build();

        String calendarId = "primary";
        //event = service.events().insert(calendarId, event).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());

    }
}
