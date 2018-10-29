package EPIONE.JAVAEE.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class PathDoctors implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
    private int ordre;
    @ManyToOne
    private MedicalPath path;
    @OneToOne
    private User doctor;
    @OneToOne(cascade = CascadeType.REMOVE)
    private MedicalVisit medicalVisit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    public MedicalPath getPath() {
        return path;
    }

    public void setPath(MedicalPath path) {
        this.path = path;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public MedicalVisit getMedicalVisit() {
        return medicalVisit;
    }

    public void setMedicalVisit(MedicalVisit medicalVisit) {
        this.medicalVisit = medicalVisit;
    }

    @Override
    public String toString() {
        return "PathDoctors{" +
                "id=" + id +
                ", ordre=" + ordre +
                ", path=" + path +
                ", doctor=" + doctor +
                ", medicalVisit=" + medicalVisit +
                '}';
    }
}
