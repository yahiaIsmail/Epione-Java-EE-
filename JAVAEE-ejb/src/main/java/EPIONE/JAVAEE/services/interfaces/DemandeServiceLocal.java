package EPIONE.JAVAEE.services.interfaces;

import EPIONE.JAVAEE.entities.Demande;

import javax.ejb.Local;
import java.util.List;

@Local
public interface DemandeServiceLocal {
    int addDemande(Demande demande);
    List<Demande> getAllDemandes();
    int deleteDemande(Demande demande);
    Demande getDemande(Demande demande);
}
