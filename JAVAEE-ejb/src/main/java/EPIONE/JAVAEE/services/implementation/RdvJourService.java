package EPIONE.JAVAEE.services.implementation;

import EPIONE.JAVAEE.entities.RDV;
import EPIONE.JAVAEE.services.interfaces.RdvJourServiceLocal;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collection;



/**
 * Created by Ellouze Skander on 27/10/2018.
 */
@Stateless
public class RdvJourService implements RdvJourServiceLocal {

    @PersistenceContext
    EntityManager em;


    @Override
    public Collection<RDV> ProgrammeJourneJ(int docId,int year,int month,int day) {
        Query query = em.createQuery("SELECT r FROM RDV r WHERE r.doctors.id =:d and YEAR(r.dateRDV)=:year and MONTH(r.dateRDV)=:month and DAY(r.dateRDV)=:day");
        query.setParameter("day", day);
        query.setParameter("month", month);
        query.setParameter("year", year);
        query.setParameter("d",docId);
        Collection<RDV> lst = (Collection<RDV>) query.getResultList();
        if (lst.size()==0)
            return null;
        else
            return lst;
    }


    @Override
    public void generateExcel(int docId)  {

    }
}
