package EPIONE.JAVAEE.services.interfaces;

import EPIONE.JAVAEE.entities.Demande;

import javax.ejb.Local;
import java.util.Collection;
import java.util.List;

@Local
public interface DemandeServiceLocal {
    int addDemande(Demande demande);
    List<Demande> getAllDemandes();
    int deleteDemande(Demande demande);
    Collection<Demande> getDemande(Demande demande);
}
