package EPIONE.JAVAEE.services.interfaces;

import EPIONE.JAVAEE.entities.Demande;

import javax.ejb.Local;
import java.util.Collection;
import java.util.List;

@Local
public interface DemandeServiceLocal {
    int addDemande(Demande demande);

    List<Demande> getAllDemandes();

    void deleteDemande(Demande demande);

    List<Demande> getDemande(Demande demande);

    Demande getDemandeById(int idDemande);

}
