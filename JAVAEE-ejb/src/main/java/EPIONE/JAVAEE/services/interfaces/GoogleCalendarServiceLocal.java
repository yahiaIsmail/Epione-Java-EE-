package EPIONE.JAVAEE.services.interfaces;

import javax.ejb.Local;
import java.io.IOException;

/**
 * Created by Ellouze Skander on 28/10/2018.
 */

@Local
public interface GoogleCalendarServiceLocal {

    public void synchronizeGoogleCalendar(String adr) throws IOException;
}

