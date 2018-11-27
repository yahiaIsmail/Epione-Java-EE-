package EPIONE.JAVAEE.services.implementation;

import EPIONE.JAVAEE.entities.*;
import EPIONE.JAVAEE.services.interfaces.MedicalPathServiceLocal;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.json.JsonObject;
import javax.naming.ldap.Rdn;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.Null;
import java.util.*;
import java.util.stream.Collectors;

import static EPIONE.JAVAEE.services.implementation.UserService.userConnected;

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
    public String addDoctorsToPath(int idPath, int idDoctor, PathDoctors pathDoctors, String description) {
        MedicalPath path = em.find(MedicalPath.class, idPath);
        User doctor = em.find(User.class, idDoctor);

        //test on connected user role
        if (doctor.getRole().equals(Roles.Doctor)) {

            if (utils.verify(em, idDoctor, idPath).isEmpty() && path.isActive()) {

                pathDoctors.setDoctor(doctor);
                pathDoctors.setPath(path);
                em.persist(pathDoctors);
                //  doctor.setPathDoctors(pathDoctors);
                MedicalVisit medicalVisit = new MedicalVisit();
                medicalVisit.setDescription(description);
                medicalVisit.setPathDoctors(pathDoctors);
                medicalVisit.setCreatedAt(new Date());
                medicalVisit.setRating(0);
                medicalVisit.setMedicalState(false);
                em.persist(medicalVisit);
                pathDoctors.setMedicalVisit(medicalVisit);

                int i = pathDoctors.getOrdre();
                for (PathDoctors d : utils.getDocPathByPathId(em, idPath)) {
                    if (!d.equals(pathDoctors)) {
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

        } else
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

        PathDoctors pathDoc = em.find(PathDoctors.class, utils.getDesiredDoctorPath(em, pathId, doctorId));
        if (!pathDoc.equals(null)) {
            int i = pathDoc.getOrdre();

            em.remove(pathDoc);
            for (PathDoctors d : utils.getDocPathByPathId(em, pathId)) {

                if (d.getOrdre() > 0 && d.getOrdre() == i + 1) {
                    //decrement ordre of steps of patient
                    d.setOrdre(d.getOrdre() - 1);

                } else break;

            }

            return "ok";
        } else {
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
            if (path.getRendezVous() != null)
                pathFind.setRendezVous(path.getRendezVous());
            if (path.getJustification() != null)
                pathFind.setJustification(path.getJustification());
            if (path.isStatus() != null)
                pathFind.setStatus(path.isStatus());
            if (path.isActive() != null)
                pathFind.setActive(path.isActive());

        }
    }

    /********************************update MedicalVisitStatus******************************/
    @Override
    public void updateMedicalVisitStatus(int pathDoc, MedicalVisit medicalVisit) {
        System.out.println(utils.getVisitById(em, pathDoc));
        MedicalVisit medi = utils.getVisitById(em, pathDoc);

        if (!medi.equals(null)) {
            if (medicalVisit.getDescription() != null)
                medi.setDescription(medicalVisit.getDescription());
            if (medicalVisit.getMedicalState() != null)
                medi.setMedicalState(medicalVisit.getMedicalState());
            if (medicalVisit.getRating() != 0)
                medi.setRating(medicalVisit.getRating());
            if (medicalVisit.getPathDoctors() != null)
                medi.setPathDoctors(medicalVisit.getPathDoctors());

        }

    }

    /**********************************update Path (active/status)**********************/
    public void agentDisablePath(int id) {
        MedicalPath pathFind = em.find(MedicalPath.class, id);
        pathFind.setActive(false);
        pathFind.setStatus(true);
        pathFind.getRendezVous().setStatus(Status.Completed);
        em.flush();
    }

    /**************************** Scan agent with cron "Automated tasks" ***************************/

    /**
     *
     * @Schedule(second = "*10", minute = "*", hour = "*", persistent = false)
     */

    public void atSchedule() throws InterruptedException {

        // System.out.println("DeclarativeScheduler:: In atSchedule()");
        // System.out.println(utils.referencedPathStatus(em));
        List<MedicalVisit> list = new ArrayList<>();
        List<Integer> paths = new ArrayList<>();
        list = utils.referencedPathStatus(em);
        int idpath;
        //System.out.println(list);
        for (int i = 0; i < list.size(); i++) {
            int cnt = 0, j;
            idpath = list.get(i).getPathDoctors().getPath().getId();
            // System.out.println("***************************INIT");
            // System.out.println(list.get(i));
            for (j = i; j < list.size(); j++) {
                if (list.get(j).getPathDoctors().getPath().getId() == idpath) {
                    //System.out.println(list.get(j));

                    if (list.get(j).getMedicalState() == true) {
                        cnt++;


                    }
                } else break;

            }

            if (cnt == j - i) {
                paths.add(idpath);
                //System.out.println("***************************wa\n");
                //System.out.println(list.get(i));

            }
            i = j - 1;


            //System.out.println(d.getPathDoctors().getPath().getId());

        }
        //System.out.println(paths);
        paths.forEach(e -> {
            agentDisablePath(e);
        });


    }


    /***************************get all rdv for patient ejb ********************************************/
    @Override
    public List<RDV> getAllRDVPatient(int idPatient) {
        //System.out.println(em.find(RDV.class,idPatient));
        List<RDV> list = utils.getRdvPatient(em, idPatient);

        return list;
    }

    /************************************* get path doc by id**************************************
     *
     */
    @Override
    public List<PathDoctors> getDoctorsInPathById(int pathid) {
        List<PathDoctors> list = utils.getPathDocByIdPath(em, pathid);
        return list;
    }

    /********************************************************** get all paths for patient**********************/
    @Override
    public List<MedicalPath> allPathsForConnectedPatient(int idpatient) {
        User user = em.find(User.class, idpatient);
        if (user != null) {

            List<MedicalPath> list = utils.getAllPathsForPatient(em, idpatient);
            return list;


        } else return null;
    }

    /****************************************** multiple search criteria ***************************************/
    @Override
    public List<MedicalPath> searchPathByOption(int idPatient, MedicalPath medicalPath) {
        List<MedicalPath> list = utils.searchList(em, medicalPath, idPatient);
        return list;
    }

    /************************************* get doctor's visits *************************************************/
    @Override
    public List<MedicalVisit> getDoctorAllvisits(int idDoctor) {
        User user = em.find(User.class, idDoctor);
        if (user != null && user.getRole().equals(Roles.Doctor)) {
            List<MedicalVisit> m = utils.doctorVisits(em, idDoctor);
            return m;
        }
        return null;
    }

    /*************************** Doctor update Medical Path *********************************/
    @Override
    public void updateDoctorInPath(String justification, int idpath, PathDoctors pathDoctors) {

        if (userConnected.getRole().equals(Roles.Doctor)) {
            List<Integer> pathids = utils.listpathsIdforDoctor(em, userConnected.getId());
            for (Integer i : pathids) {
                if (i == idpath) {

                    if (pathDoctors.getDoctor().getId() != 0) {
                        PathDoctors ptdoc = em.find(PathDoctors.class, pathDoctors.getId());
                        ptdoc.setDoctor(em.find(User.class, pathDoctors.getDoctor().getId()));
                        if (!pathDoctors.getMedicalVisit().getDescription().equals(null))
                            ptdoc.getMedicalVisit().setDescription(pathDoctors.getMedicalVisit().getDescription());
                        if (pathDoctors.getOrdre() != 0) {
                            ptdoc.setOrdre(pathDoctors.getOrdre());
                        }
                        if (justification != null) {
                            MedicalPath pathfinder = em.find(MedicalPath.class, i);
                            pathfinder.setJustification(justification);
                            em.flush();
                        }
                        em.flush();
                    }


                }
            }
        }

    }

    /**************************************** get all paths for doctor**************************/
    @Override
    public List<PathDoctors> getAllPathForDoctor() {
        if (userConnected.getRole().equals(Roles.Doctor)) {
            List<PathDoctors> list = utils.listpathsforDoctor(em, userConnected.getId());
            System.out.println(list);
            return list;
        } else return null;

    }
    /**************************************** nearby Doctors on adding doctors for patient path *-************************/
    public double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
    @Override
    public List<User> nearbyDoctors(double el1,double el2,int idPatient) {
        System.out.println(em.find(User.class,idPatient));
        double x=distance(43.2724008,73.2724008,5.39050539999994,7.39050539999994,el1,el2);
        User li=em.find(User.class,idPatient);
        System.out.println(x/1000);
        List<User> lis=new ArrayList<>();
        lis.add(li);
        return lis;
    }

    @Override
    public List<MedicalPath> pathsforOneDoctor(int id) {
        TypedQuery<MedicalPath> list= em.createQuery("select p from MedicalPath p " +
                "where p.rendezVous.doctors.id=:id   ",MedicalPath.class);


        List<MedicalPath> query;
        query=list.setParameter("id",id).getResultList();
        for (MedicalPath p : query ) {
            List<PathDoctors> personsWithoutDuplicates = p.getDoctorPath().stream()
                    .distinct()
                    .collect(Collectors.toList());
            p.setDoctorPath(null);
            p.setDoctorPath(personsWithoutDuplicates);
        }
        return query;

    }
    /************************* list rdv for doc ******************/
    @Override
    public List<RDV> getDocRDVS(int id) {
        TypedQuery<RDV> list= em.createQuery("select p from RDV p WHERE NOT EXISTS (SELECT m.rendezVous.id FROM MedicalPath m)and p.doctors.id=:id and p.status=1  ",RDV.class);

        return list.setParameter("id",id).getResultList();
    }
}
