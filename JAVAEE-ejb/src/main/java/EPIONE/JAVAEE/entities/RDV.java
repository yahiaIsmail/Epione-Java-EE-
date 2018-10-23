package EPIONE.JAVAEE.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
	private User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
				Objects.equals(user, rdv.user) &&
				Objects.equals(medicalPath, rdv.medicalPath);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, dateRDV, confirmationDoc, confirmationPatient, status, user, medicalPath);
	}

	@Override
	public String toString() {
		return "RDV{" +
				"id=" + id +
				", dateRDV=" + dateRDV +
				", confirmationDoc=" + confirmationDoc +
				", confirmationPatient=" + confirmationPatient +
				", status=" + status +
				", user=" + user +
				", medicalPath=" + medicalPath +
				'}';
	}
}
