package EPIONE.JAVAEE.services.implementation;

import EPIONE.JAVAEE.entities.*;
import EPIONE.JAVAEE.services.interfaces.MedicalPathServiceLocal;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
            path.setCreatedAt(new Date());
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
    public String addDoctorsToPath(int idPath, int idDoctor, PathDoctors pathDoctors,String description) {
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
            MedicalVisit medicalVisit=new MedicalVisit();
            medicalVisit.setDescription(description);
            medicalVisit.setPathDoctors(pathDoctors);
            medicalVisit.setCreatedAt(new Date());
            medicalVisit.setRating(0);
            medicalVisit.setMedicalState(false);
            em.persist(medicalVisit);
            pathDoctors.setMedicalVisit(medicalVisit);

                    int i=pathDoctors.getOrdre();
                for (PathDoctors d : utils.getDocPathByPathId(em,idPath))
                {
                    if(!d.equals(pathDoctors)) {
                        if (d.getOrdre() >= 0 && d.getOrdre() == i) {
                            //decrement ordre of steps of patient
                            d.setOrdre(d.getOrdre() + 1);
                        } else break;
                    }
                }
            em.flush();
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
    public String removeDoctorFromPath(int pathId, int doctorId) {

       // MedicalPath path=utils.getPathById(pathId);
       PathDoctors pathDoc=em.find(PathDoctors.class,utils.getDesiredDoctorPath(em,pathId,doctorId));
       if(!pathDoc.equals(null))
       {        int i=pathDoc.getOrdre();

                em.remove(pathDoc);
           for(PathDoctors d : utils.getDocPathByPathId(em,pathId) ) {

               if (d.getOrdre() > 0 && d.getOrdre()==i+1) {
                   //decrement ordre of steps of patient
                   d.setOrdre(d.getOrdre() - 1);

               } else break;

           }

           return "ok";
       }
       else
       {
        return "no";
       }

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
    /********************************update MedicalVisitStatus******************************/
    @Override
    public void updateMedicalVisitStatus(int pathDoc, MedicalVisit medicalVisit) {
        System.out.println(utils.getVisitById(em,pathDoc));
        MedicalVisit medi= utils.getVisitById(em,pathDoc);
        if (!medi.equals(null)) {
           if(medicalVisit.getDescription()!=null)
               medi.setDescription(medicalVisit.getDescription());
            if (medicalVisit.getMedicalState()!=null)
                medi.setMedicalState(medicalVisit.getMedicalState());
            if(medicalVisit.getRating()!=0)
                medi.setRating(medicalVisit.getRating());
            if (medicalVisit.getPathDoctors()!=null)
                medi.setPathDoctors(medicalVisit.getPathDoctors());

        }

    }
    @Schedule(second = "*/5", minute = "*", hour = "*", persistent = false)

    public void atSchedule() throws InterruptedException {

        System.out.println("DeclarativeScheduler:: In atSchedule()");
    }

}
