package EPIONE.JAVAEE.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class PathDoctors implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int ordre;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private MedicalPath path;
    @OneToOne
    private User doctor;

    @OneToOne(mappedBy = "pathDoctors" ,fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
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


                ", medicalVisit=" + medicalVisit +
                '}';
    }
}
