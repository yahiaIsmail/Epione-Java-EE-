package EPIONE.JAVAEE.services.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


import EPIONE.JAVAEE.entities.Motif;
import EPIONE.JAVAEE.entities.User;
import EPIONE.JAVAEE.services.interfaces.MotifServiceLocal;


@Stateless
public class MotifService implements MotifServiceLocal {


    @PersistenceContext
    EntityManager em;

    @Override
    public int ajouterMotif(Motif motif) {
        em.persist(motif);
        return 1;
    }

    @Override
    public void affecterMotifDoctor(int motifId, int docId) {
        Motif m = em.find(Motif.class, motifId);
        User d = em.find(User.class, docId);
        List<Motif> lstM = new ArrayList<Motif>();
        lstM.add(m);
        m.setDoctor(d);

    }

    @Override
    public int modifierMotif(int motifId, String desc) {
        Motif motif = em.find(Motif.class, motifId);
        motif.setDescription(desc);
        return motif.getId();
    }

    @Override
    public int supprimerMotif(int motifId) {
        Motif motif = em.find(Motif.class, motifId);
        em.remove(motif);
        return 0;
    }


    @Override
    public List<Motif> listerMotifByDoc(User doc) {
        TypedQuery<Motif> query = em.createQuery("select m from Motif m where m.doctor.id=:id", Motif.class);
        query.setParameter("id", doc.getId());
        List<Motif> lst = new ArrayList<Motif>();
        lst = query.getResultList();

        return lst;
    }

}
