package EPIONE.JAVAEE.services.implementation;

import EPIONE.JAVAEE.entities.MedicalPath;
import EPIONE.JAVAEE.entities.RDV;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class MedicalPathServiceUtils {
   public boolean Count(EntityManager em, int id ) {

        TypedQuery<Boolean> count = em.createQuery("SELECT m FROM MedicalPath m WHERE m.rendezVous.id = :id",Boolean.class);

        return count.setParameter("id",id).getSingleResult();
    }
    public RDV getRdvById(EntityManager em, int id ) {

        TypedQuery<RDV> query = em.createQuery("SELECT m FROM RDV m WHERE m.id = :id",RDV.class);

        return query.setParameter("id",id).getSingleResult();
    }
}
