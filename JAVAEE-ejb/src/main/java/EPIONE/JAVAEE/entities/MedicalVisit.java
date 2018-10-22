package EPIONE.JAVAEE.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class MedicalVisit implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String description;
	private boolean medicalState;
	private int rating;
	
	@ManyToOne
	private MedicalPath medicalPath;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isMedicalState() {
		return medicalState;
	}

	public void setMedicalState(boolean medicalState) {
		this.medicalState = medicalState;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
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
		MedicalVisit that = (MedicalVisit) o;
		return id == that.id &&
				medicalState == that.medicalState &&
				rating == that.rating &&
				Objects.equals(description, that.description) &&
				Objects.equals(medicalPath, that.medicalPath);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, description, medicalState, rating, medicalPath);
	}

	@Override
	public String toString() {
		return "MedicalVisit{" +
				"id=" + id +
				", description='" + description + '\'' +
				", medicalState=" + medicalState +
				", rating=" + rating +
				", medicalPath=" + medicalPath +
				'}';
	}
}
