package EPIONE.JAVAEE.services.implementation;

import EPIONE.JAVAEE.entities.*;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class MedicalPathServiceUtils {
    public List<MedicalPath> Count(EntityManager em, int id) {

        TypedQuery<MedicalPath> count = em.createQuery("SELECT m FROM MedicalPath m WHERE m.rendezVous.id = :id", MedicalPath.class);
        List<MedicalPath> list = count.setParameter("id", id).getResultList();
        return list;
    }

    public RDV getRdvById(EntityManager em, int id) {

        TypedQuery<RDV> query = em.createQuery("SELECT m FROM RDV m WHERE m.id = :id", RDV.class);

        return query.setParameter("id", id).getSingleResult();
    }

    public List<MedicalPath> getPathById(EntityManager em, int id) {

        TypedQuery<MedicalPath> query = em.createQuery("SELECT m FROM MedicalPath m WHERE m.id = :id", MedicalPath.class);
        List<MedicalPath> list = query.setParameter("id", id).getResultList();
        return list;
    }

    public List<PathDoctors> verify(EntityManager em, int iddoc, int idpath) {

        TypedQuery<PathDoctors> query = em.createQuery("SELECT p FROM PathDoctors p WHERE p.doctor.id = :iddoc and p.path.id= :idpath", PathDoctors.class);
        List<PathDoctors> list = query.setParameter("iddoc", iddoc).setParameter("idpath", idpath).getResultList();
        return list;
    }

    public int getDesiredDoctorPath(EntityManager em, int iddoc, int idpath) {

        TypedQuery<PathDoctors> query = em.createQuery("SELECT p FROM PathDoctors p WHERE p.doctor.id = :iddoc and p.path.id= :idpath", PathDoctors.class);
        PathDoctors list = query.setParameter("iddoc", iddoc).setParameter("idpath", idpath).getSingleResult();
        return list.getId();
    }

    public List<PathDoctors> getDocPathByPathId(EntityManager em, int idpath) {

        TypedQuery<PathDoctors> query = em.createQuery("SELECT p FROM PathDoctors p WHERE p.path.id= :idpath", PathDoctors.class);
        List<PathDoctors> list = query.setParameter("idpath", idpath).getResultList();

        return list;
    }

    public MedicalVisit getVisitById(EntityManager em, int id) {
        TypedQuery<MedicalVisit> query = (TypedQuery<MedicalVisit>) em.createQuery("SELECT p FROM MedicalVisit p WHERE p.pathDoctors.id= :id", MedicalVisit.class);

        return query.setParameter("id", id).getSingleResult();
    }

    public List<RDV> getRdvPatient(EntityManager em, int idpath) {

        TypedQuery<RDV> query = em.createQuery("SELECT p FROM RDV p WHERE p.users.id= :idpath", RDV.class);
        List<RDV> list = query.setParameter("idpath", idpath).getResultList();

        return list;
    }

    public List<MedicalVisit> referencedPathStatus(EntityManager em) {

           /* Query query=em.createNativeQuery("SELECT m.medicalState , p.id from medicalvisit m,medicalpath p,pathdoctors c WHERE m.pathDoctors_id=c.id and c.path_id=p.id");
                List<Object> list =query.getResultList();*/
        TypedQuery<MedicalVisit> list = em.createQuery("select m from MedicalVisit m,MedicalPath  p,PathDoctors  c where m.pathDoctors.id=c.id and  c.path.id=p.id order by m.pathDoctors.path.id asc", MedicalVisit.class);
        List<MedicalVisit> query = list.getResultList();
        return query;
    }

}
