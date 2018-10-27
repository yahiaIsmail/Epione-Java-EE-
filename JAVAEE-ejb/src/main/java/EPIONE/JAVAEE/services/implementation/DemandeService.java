package EPIONE.JAVAEE.services.implementation;

import EPIONE.JAVAEE.entities.Demande;
import EPIONE.JAVAEE.services.interfaces.DemandeServiceLocal;
import EPIONE.JAVAEE.services.interfaces.DemandeServiceRemote;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class DemandeService implements DemandeServiceLocal, DemandeServiceRemote {

    @PersistenceContext(unitName = "JAVAEE-ejb")
    EntityManager em;
    @Override
    public int addDemande(Demande demande) {
            em.persist(demande);
            return demande.getId();
    }

    @Override
    public List<Demande> getAllDemandes() {
        List<Demande> d= new ArrayList<>();
        List<Demande> d1= new ArrayList<>();
        return  em.createQuery("select d from Demande d",Demande.class).getResultList();

    }



    @Override
    public int deleteDemande(Demande demande) {

            em.remove(demande);
            return 1;

    }

    @Override
    public Demande getDemande(Demande demande) {
     return em.find(Demande.class,demande);
    }
}
