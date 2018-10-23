package EPIONE.JAVAEE.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class RDV implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Date dateRDV;
	private boolean confirmationDoc;
	private boolean confirmationPatient;
	private Status status;
	

	@ManyToOne
<<<<<<< HEAD
	private User users;//patient

	@ManyToOne
	private User doctors;

	@OneToOne
	private Motif motif;
=======
	private User user;
>>>>>>> 2008d356333070c6cdd9d73d715f12228cac414d
	
	@OneToOne(mappedBy="rendezVous")
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
				Objects.equals(medicalPath, rdv.medicalPath);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, dateRDV, confirmationDoc, confirmationPatient, status, medicalPath);
	}

	@Override
	public String toString() {
		return "RDV{" +
				"id=" + id +
				", dateRDV=" + dateRDV +
				", confirmationDoc=" + confirmationDoc +
				", confirmationPatient=" + confirmationPatient +
				", status=" + status +
				", medicalPath=" + medicalPath +
				'}';
	}
}
