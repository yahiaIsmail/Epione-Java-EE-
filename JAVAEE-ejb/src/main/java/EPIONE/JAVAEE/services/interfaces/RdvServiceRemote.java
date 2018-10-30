package EPIONE.JAVAEE.services.interfaces;

import javax.ejb.Remote;

@Remote
public interface RdvServiceRemote {
    boolean modifyRdvDate(int id, int year, int month, int day, int hour, int minutes);

    int takeRvdPatient(String emailPatient, String emailDoctor, int motif, int year, int month, int day, int hour, int minutes);


    boolean confirmRdvPatient(String token);

    boolean confirmRdvDoctor(String token);
}
