package EPIONE.JAVAEE.services.interfaces;

import EPIONE.JAVAEE.entities.User;

import javax.ejb.Local;
import java.util.Map;

@Local
public interface RdvServiceLocal {
    boolean modifyRdvDate(int id, int year, int month, int day, int hour, int minutes);

    int takeRvdPatient(String emailPatient, String emailDoctor, int motif, int year, int month, int day, int hour, int minutes);

    boolean confirmRdvPatient(String token, int rdvId);

    boolean confirmRdvDoctor(String token, int rdvId);

    boolean modifyRdvMotif(int id, int motifId);

    Map<String, User> cancelRdv(int rdvId);

}
