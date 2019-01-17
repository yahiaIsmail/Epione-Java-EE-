package EPIONE.JAVAEE.services.implementation;

import EPIONE.JAVAEE.entities.*;
import EPIONE.JAVAEE.services.interfaces.RdvServiceLocal;
import EPIONE.JAVAEE.services.interfaces.RdvServiceRemote;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class RdvService implements RdvServiceLocal, RdvServiceRemote {
    @PersistenceContext(unitName = "JAVAEE-ejb")
    EntityManager em;

    @Override
    public boolean modifyRdvDate(int id, int year, int month, int day, int hour, int minutes) {
        try {
            RDV rdv = (RDV) em.createQuery("SELECT rdv FROM RDV rdv WHERE rdv.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
            Instant now = Instant.now();
            Timestamp newDateRdv = Timestamp.from(now);
            newDateRdv.setHours(hour);
            newDateRdv.setMinutes(minutes);
            newDateRdv.setMonth(month - 1);
            newDateRdv.setYear(year);
            newDateRdv.setDate(day);
            rdv.setDateRDV(newDateRdv);
            em.merge(rdv);
            return true;
        } catch (javax.persistence.NoResultException exp) {
            return false;
        }
    }

    @Override
    public int takeRvdPatient(String emailPatient, String emailDoctor, int motifId, int year, int month,
                              int day, int hour, int minutes) {
        try {
            User patient = (User) em.createQuery(
                    "SELECT u FROM User u WHERE u.email = :emailPatient")
                    .setParameter("emailPatient", emailPatient)
                    .getSingleResult();
            User doctor = (User) em.createQuery(
                    "SELECT u FROM User u WHERE u.email = :emailDoctor")
                    .setParameter("emailDoctor", emailDoctor)
                    .getSingleResult();
            Address doctorAddress = doctor.getAddress();

            Motif motif = (Motif) em.createQuery(
                    "SELECT m FROM Motif m WHERE m.id = :id")
                    .setParameter("id", motifId)
                    .getSingleResult();
            RDV rdv = new RDV();
            Instant now = Instant.now();
            Timestamp dateRdv = Timestamp.from(now);
            dateRdv.setHours(hour);
            dateRdv.setMinutes(minutes);
            dateRdv.setMonth(month - 1);
            dateRdv.setYear(year - 1900);
            dateRdv.setDate(day - 3);

            rdv.setMotif(motif);
            rdv.setDateRDV(dateRdv);
            rdv.setConfirmationDoc(false);
            rdv.setConfirmationPatient(false);
            rdv.setStatus(Status.InProgress);
            rdv.setUsers(patient);
            rdv.setDoctors(doctor);

            em.persist(rdv);
            return rdv.getId();
        } catch (javax.persistence.NoResultException exp) {
            return 0;
        }
    }

    @Override
    public boolean confirmRdvPatient(String token, int rdvId) {
        try {
            User usr = (User) em.createQuery(
                    "SELECT u FROM User u WHERE u.confirmationToken = :token")
                    .setParameter("token", token)
                    .getSingleResult();
            RDV rdv = (RDV) em.createQuery("SELECT rdv FROM RDV rdv WHERE rdv.id = :id")
                    .setParameter("id", rdvId)
                    .getSingleResult();
            rdv.setConfirmationPatient(true);
            em.merge(rdv);
            return true;
        } catch (javax.persistence.NoResultException exp) {
            return false;
        }
    }

    @Override
    public boolean confirmRdvDoctor(String token, int rdvId) {
        try {
            User usr = (User) em.createQuery(
                    "SELECT u FROM User u WHERE u.confirmationToken = :token")
                    .setParameter("token", token)
                    .getSingleResult();
            RDV rdv = (RDV) em.createQuery("SELECT rdv FROM RDV rdv WHERE rdv.id = :id")
                    .setParameter("id", rdvId)
                    .getSingleResult();
            rdv.setConfirmationDoc(true);
            em.merge(rdv);
            return true;
        } catch (javax.persistence.NoResultException exp) {
            return false;
        }
    }

    @Override
    public boolean modifyRdvMotif(int id, int motifId) {
        try {
            RDV rdv = (RDV) em.createQuery("SELECT rdv FROM RDV rdv WHERE rdv.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
            Motif newMotif = (Motif) em.createQuery("SELECT motif FROM Motif motif WHERE motif.id = :id ")
                    .setParameter("id", motifId)
                    .getSingleResult();
            rdv.setMotif(newMotif);
            em.merge(rdv);
            return true;
        } catch (javax.persistence.NoResultException exp) {
            return false;
        }
    }

    @Override
    public Map<String, User> cancelRdv(int rdvId) {
        try {
            RDV rdv = (RDV) em.createQuery("SELECT rdv FROM RDV rdv WHERE rdv.id = :id")
                    .setParameter("id", rdvId)
                    .getSingleResult();
            rdv.setStatus(Status.Canceled);
            User usr = rdv.getUsers();
            User doctor = rdv.getDoctors();
            em.merge(rdv);
            Map<String, User> map = new HashMap<>();
            map.put("patient", usr);
            map.put("doctor", doctor);
            return map;
        } catch (javax.persistence.NoResultException exp) {
            return null;
        }
    }

    @Override
    public Map<String, User> confirmRdv(int rdvId) {
        try {
            RDV rdv = (RDV) em.createQuery("SELECT rdv FROM RDV rdv WHERE rdv.id = :id")
                    .setParameter("id", rdvId)
                    .getSingleResult();
            rdv.setStatus(Status.Completed);
            User usr = rdv.getUsers();
            User doctor = rdv.getDoctors();
            em.merge(rdv);
            Map<String, User> map = new HashMap<>();
            map.put("patient", usr);
            map.put("doctor", doctor);
            return map;
        } catch (javax.persistence.NoResultException exp) {
            return null;
        }
    }

    @Override
    public User selectRdvMotif(int rdvId, int motifId) {
        try {
            RDV rdv = (RDV) em.createQuery("SELECT rdv FROM RDV rdv WHERE rdv.id = :id")
                    .setParameter("id", rdvId)
                    .getSingleResult();
            Motif motif = (Motif) em.createQuery("SELECT motif FROM Motif motif WHERE motif.id = :id")
                    .setParameter("id", motifId)
                    .getSingleResult();
            rdv.setMotif(motif);
            User doctor = rdv.getDoctors();
            em.merge(rdv);
            return doctor;
        } catch (javax.persistence.NoResultException exp) {
            return null;
        }
    }

    @Override
    public List<RDV> searchRdvByDoctor(int doctorId) {
        try {
            User doc = new User();
            doc.setId(doctorId);
            List<RDV> rdvs = em.createQuery("SELECT rdv FROM RDV rdv where rdv.doctors = :id")
                    .setParameter("id", doc)
                    .getResultList();
            return rdvs;
        } catch (javax.persistence.NoResultException exp) {
            return null;
        }
    }

    @Override
    public List<RDV> searchRdvByPatient(int patientId) {
        try {
            User pat = new User();
            pat.setId(patientId);
            List<RDV> rdvs = em.createQuery("SELECT rdv FROM RDV rdv where rdv.users = :id")
                    .setParameter("id", pat)
                    .getResultList();
            return rdvs;
        } catch (javax.persistence.NoResultException exp) {
            return null;
        }
    }

    @Override
    public List<RDV> searchRdvConfirmed() {
        try {
            List<RDV> rdvs = em.createQuery("SELECT rdv FROM RDV rdv where rdv.confirmationDoc = true AND rdv.confirmationPatient = true")
                    .getResultList();
            return rdvs;
        } catch (javax.persistence.NoResultException exp) {
            return null;
        }
    }
    @Override
    public List<RDV> getAllRdv() {
        try {
            List<RDV> rdvs = em.createQuery("SELECT rdv FROM RDV rdv ")
                    .getResultList();
            return rdvs;
        } catch (javax.persistence.NoResultException exp) {
            return null;
        }
    }

}
