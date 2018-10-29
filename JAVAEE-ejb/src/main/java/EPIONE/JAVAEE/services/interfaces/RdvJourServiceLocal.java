package EPIONE.JAVAEE.services.interfaces;

import EPIONE.JAVAEE.entities.RDV;

import javax.ejb.Local;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

/**
 * Created by Ellouze Skander on 27/10/2018.
 */

@Local
public interface RdvJourServiceLocal {
    public Collection<RDV> ProgrammeJourneJ(int docId,int year, int month, int day);
    public void generateExcel(int docId);
}
