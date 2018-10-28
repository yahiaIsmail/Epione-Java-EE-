package EPIONE.JAVAEE.services.implementation;

import EPIONE.JAVAEE.entities.MedicalPath;
import EPIONE.JAVAEE.entities.RDV;
import EPIONE.JAVAEE.entities.Status;
import EPIONE.JAVAEE.entities.User;
import EPIONE.JAVAEE.services.interfaces.MedicalPathServiceLocal;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class MedicalPathService implements MedicalPathServiceLocal {


    @PersistenceContext
    EntityManager em;

    @Override
    public String test() {
        return "medicalPath is working now !";
    }
    /************************Create Path For Patient EJB******************************************************/
    @Override
    public int createPathForPatient(int rdvId, MedicalPath path) throws NullPointerException {

        MedicalPathServiceUtils utils = new MedicalPathServiceUtils();
        RDV rdv=utils.getRdvById(em,rdvId);
       // boolean count = utils.Count(em, rdvId);
       // System.out.println(count+"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        //test on connected user role
        //test on medical path count for the patient
        //test on the status of the RDV
       if (rdv.getStatus() == Status.InProgress) {

            //MedicalPath path = new MedicalPath();
            path.setRendezVous(rdv);

            em.persist(path);
            System.out.println("created sucessfully");
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
    public String addDoctorsToPath(int idPath, int idDoctor) {
        MedicalPath path = em.find(MedicalPath.class, idPath);
        User doctor = em.find(User.class, idDoctor);
        //test on connected user role
        List<User> doctors = new ArrayList<>();
        List<MedicalPath> paths = new ArrayList<>();
        if(doctors.isEmpty() && paths.isEmpty() )
        {

          /*  doctors.add(doctor);

            paths.add(path);
            path.setUsers(doctors);
            doctor.setPaths(paths);*/

        }
        else
        {

            doctors.add(doctor);
            paths.add(path);
            /*path.setUsers(doctors);
            doctor.setPaths(paths);*/
        }
        return "Added Doctor to path Successfully !";
    }
    /************************END  Add Doctor To path EJB******************************************************/
/**
 *
 */
    /****Removing Doctor for patient path EJB****/
    @Override
    public void removeDoctorFromPath(int pathId,int doctorId) {
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
}
