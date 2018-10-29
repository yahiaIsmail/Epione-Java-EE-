package EPIONE.JAVAEE.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class RDV implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRDV;
    private boolean confirmationDoc;
    private boolean confirmationPatient;
    private Status status;


    @JsonIgnore
    @ManyToOne
    private User users;//patient

    @JsonIgnore
    @ManyToOne
    private User doctors;

    @OneToOne
    private Motif motif;

    @OneToOne(mappedBy = "rendezVous")
    private MedicalPath medicalPath;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateRDV() {
        return dateRDV;
    }

    public void setDateRDV(Date dateRDV) {
        this.dateRDV = dateRDV;
    }

    public boolean isConfirmationDoc() {
        return confirmationDoc;
    }

    public void setConfirmationDoc(boolean confirmationDoc) {
        this.confirmationDoc = confirmationDoc;
    }

    public boolean isConfirmationPatient() {
        return confirmationPatient;
    }

    public void setConfirmationPatient(boolean confirmationPatient) {
        this.confirmationPatient = confirmationPatient;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public User getDoctors() {
        return doctors;
    }

    public void setDoctors(User doctors) {
        this.doctors = doctors;
    }

    public Motif getMotif() {
        return motif;
    }

    public void setMotif(Motif motif) {
        this.motif = motif;
    }

    public MedicalPath getMedicalPath() {
        return medicalPath;
    }

    public void setMedicalPath(MedicalPath medicalPath) {
        this.medicalPath = medicalPath;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RDV rdv = (RDV) o;
        return id == rdv.id &&
                confirmationDoc == rdv.confirmationDoc &&
                confirmationPatient == rdv.confirmationPatient &&
                Objects.equals(dateRDV, rdv.dateRDV) &&
                status == rdv.status &&
                Objects.equals(users, rdv.users) &&
                Objects.equals(doctors, rdv.doctors) &&
                Objects.equals(motif, rdv.motif) &&
                Objects.equals(medicalPath, rdv.medicalPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateRDV, confirmationDoc, confirmationPatient, status, users, doctors, motif, medicalPath);
    }

    @Override
    public String toString() {
        return "RDV{" +
                "id=" + id +
                ", dateRDV=" + dateRDV +
                ", confirmationDoc=" + confirmationDoc +
                ", confirmationPatient=" + confirmationPatient +
                ", status=" + status +
                ", users=" + users +
                ", doctors=" + doctors +
                ", motif=" + motif +
                ", medicalPath=" + medicalPath +
                '}';
    }
}
