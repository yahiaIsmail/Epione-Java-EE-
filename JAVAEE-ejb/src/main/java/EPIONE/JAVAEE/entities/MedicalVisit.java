package EPIONE.JAVAEE.entities;

import javax.persistence.*;
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
	@OneToOne
	private PathDoctors pathDoctors;
	


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



	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MedicalVisit that = (MedicalVisit) o;
		return id == that.id &&
				medicalState == that.medicalState &&
				rating == that.rating &&
				Objects.equals(description, that.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, description, medicalState, rating);
	}

	@Override
	public String toString() {
		return "MedicalVisit{" +
				"id=" + id +
				", description='" + description + '\'' +
				", medicalState=" + medicalState +
				", rating=" + rating +

				'}';
	}
}
