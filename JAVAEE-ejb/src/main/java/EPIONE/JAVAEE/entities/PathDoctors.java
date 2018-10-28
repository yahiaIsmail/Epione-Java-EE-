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
    @OneToOne
    private MedicalVisit medicalVisit;

}
