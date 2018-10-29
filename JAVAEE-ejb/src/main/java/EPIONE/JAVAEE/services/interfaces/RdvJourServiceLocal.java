package EPIONE.JAVAEE.services.interfaces;

import EPIONE.JAVAEE.entities.RDV;

import javax.ejb.Local;
import java.util.Collection;

/**
 * Created by Ellouze Skander on 27/10/2018.
 */

@Local
public interface RdvJourServiceLocal {
    public Collection<RDV> ProgrammeJourneJ(int docId,int year, int month, int day);
}
