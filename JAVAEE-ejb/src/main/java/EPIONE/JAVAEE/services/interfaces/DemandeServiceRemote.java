package EPIONE.JAVAEE.services.interfaces;

import EPIONE.JAVAEE.entities.Demande;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface DemandeServiceRemote {
    int addDemande(Demande demande);
    List<Demande> getAllDemandes();
    int deleteDemande(Demande demande);
    Demande getDemande(Demande demande);
}
