package EPIONE.JAVAEE.services;

import EPIONE.JAVAEE.entities.MedicalPath;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
@Stateless
public class MedicalPathService implements MedicalPathServiceInterface {
    @PersistenceContext
    private EntityManager em;


    @Override
    public int createMedicalPath(MedicalPath mediPath) {
        em.persist(mediPath);
        return mediPath.getId();
    }
}
