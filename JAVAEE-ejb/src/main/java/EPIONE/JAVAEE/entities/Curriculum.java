package EPIONE.JAVAEE.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class Curriculum implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String description;
	private Date diplomaDate;



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

	public Date getDiplomaDate() {
		return diplomaDate;
	}

	public void setDiplomaDate(Date diplomaDate) {
		this.diplomaDate = diplomaDate;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Curriculum that = (Curriculum) o;
		return id == that.id &&
				Objects.equals(description, that.description) &&
				Objects.equals(diplomaDate, that.diplomaDate) ;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, description, diplomaDate);
	}

	@Override
	public String toString() {
		return "Curriculum{" +
				"id=" + id +
				", description='" + description + '\'' +
				", diplomaDate=" + diplomaDate +
				'}';
	}
}
