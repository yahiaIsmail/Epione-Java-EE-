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
import java.util.Collection;
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
    public void deleteDemande(Demande demande) {
        System.out.println(demande);
        //em.remove(em.contains(entity) ? entity : em.merge(entity));
            em.remove(em.contains(demande) ? demande : em.merge(demande));

    }

    @Override
    public List<Demande> getDemande(Demande demande) {
//     return (Collection<Demande>) em.createNamedQuery("getDemande")
//             .setParameter("firstName",demande.getFirstName()).setParameter("lastName",demande.getLastName())
//                .getResultList();
        return  (List<Demande>)em.createQuery("select d from Demande d where d.email=:email",Demande.class)
                .setParameter("email",demande.getEmail())
                .getResultList();

    }

    @Override
    public Demande getDemandeById(int idDemande) {
        return em.find(Demande.class,idDemande);
    }
}
