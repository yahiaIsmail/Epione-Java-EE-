package EPIONE.JAVAEE.services.implementation;

import EPIONE.JAVAEE.entities.*;
import EPIONE.JAVAEE.services.interfaces.MedicalPathServiceLocal;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class MedicalPathService implements MedicalPathServiceLocal {

    MedicalPathServiceUtils utils = new MedicalPathServiceUtils();

    @PersistenceContext
    EntityManager em;

    @Override
    public String test() {
        return "medicalPath is working now !";
    }

    /************************Create Path For Patient EJB******************************************************/
    @Override
    public int createPathForPatient(int rdvId, MedicalPath path) throws NullPointerException {


        RDV rdv = utils.getRdvById(em, rdvId);
        List<MedicalPath> count = utils.Count(em, rdvId);
        // System.out.println(count+"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        //test on connected user role
        //test on medical path count for the patient
        //test on the status of the RDV
        if (rdv.getStatus() == Status.InProgress && count.isEmpty()) {

            //MedicalPath path = new MedicalPath();
            path.setRendezVous(rdv);

            em.persist(path);

            return path.getId();

        } else {
            System.out.println("RDV is either completed or Cancelled / pathed");
            return -1;

        }


    }
    /************************ End Create Path For Patient EJB******************************************************/
/**
 *
 */

    /************************Add Doctor To path EJB******************************************************/
    @Override
    public String addDoctorsToPath(int idPath, int idDoctor, PathDoctors pathDoctors) {
        MedicalPath path = em.find(MedicalPath.class, idPath);
        User doctor = em.find(User.class, idDoctor);
        boolean exists;
        //test on connected user role
        if (doctor.getRole().equals(Roles.Doctor))
        {
        if (utils.verify(em, idDoctor, idPath).isEmpty() && path.isActive() ) {

            pathDoctors.setDoctor(doctor);
            pathDoctors.setPath(path);
            em.persist(pathDoctors);
            return "Added Doctor to path Successfully !";
        } else {
            return "already added";
        }

        }
        else
            return "Patient";


    }
    /************************END  Add Doctor To path EJB******************************************************/
/**
 *
 */
    /****Removing Doctor for patient path EJB****/
    @Override
    public void removeDoctorFromPath(int pathId, int doctorId) {
       /* User doc=em.find(User.class,doctorId);
        MedicalPath pathPatient=em.find(MedicalPath.class,pathId);
        for (MedicalPath path : doc.getPaths())
        {
            if(path.getId()==pathId) {
                path.getUsers().remove(doc);
               // em.remove(doc);
            }
        }
*/
    }


    /****END Removing Doctor for patient path EJB****/
    /**
     *
     */


    /***********getPathById***********************/
    @Override
    public MedicalPath getPathById(int pathId) {

        // List<MedicalPath> list =utils.getPathById(em,pathId);
        MedicalPath path = em.find(MedicalPath.class, pathId);

        return path;
    }

    /**************************update path ******************************/
    @Override
    public void updatePathComponent(int id, MedicalPath path) {
        MedicalPath pathFind = getPathById(id);
        if (!pathFind.equals(null)) {
            if(path.getRendezVous()!=null)
            pathFind.setRendezVous(path.getRendezVous());
            if (path.getJustification()!=null)
            pathFind.setJustification(path.getJustification());
            if (path.isStatus()!=null)
            pathFind.setStatus(path.isStatus());
            if(path.isActive()!=null)
            pathFind.setActive(path.isActive());

        }
    }
}
