package EPIONE.JAVAEE.services.interfaces;

import javax.ejb.Local;

@Local
public interface RdvServiceLocal {
    boolean modifyRdvDate(int id, int year, int month, int day, int hour, int minutes);

    int takeRvdPatient(String emailPatient, String emailDoctor, int motif, int year, int month, int day, int hour, int minutes);

    boolean confirmRdvPatient(String token);

    boolean confirmRdvDoctor(String token);
}
