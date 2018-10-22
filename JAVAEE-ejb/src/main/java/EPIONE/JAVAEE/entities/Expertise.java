package EPIONE.JAVAEE.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Expertise implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String content;

	@ManyToOne
	private DoctorData doctorData;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public DoctorData getDoctorData() {
		return doctorData;
	}

	public void setDoctorData(DoctorData doctorData) {
		this.doctorData = doctorData;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Expertise expertise = (Expertise) o;
		return id == expertise.id &&
				Objects.equals(content, expertise.content) &&
				Objects.equals(doctorData, expertise.doctorData);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, content, doctorData);
	}

	@Override
	public String toString() {
		return "Expertise{" +
				"id=" + id +
				", content='" + content + '\'' +
				", doctorData=" + doctorData +
				'}';
	}
}
