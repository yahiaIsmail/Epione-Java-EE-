package EPIONE.JAVAEE.services.interfaces;

import EPIONE.JAVAEE.entities.Demande;

import javax.ejb.Remote;
import java.util.Collection;
import java.util.List;

@Remote
public interface DemandeServiceRemote {
    int addDemande(Demande demande);

    List<Demande> getAllDemandes();

    void deleteDemande(Demande demande);

    List<Demande> getDemande(Demande demande);

    Demande getDemandeById(int idDemande);
}
