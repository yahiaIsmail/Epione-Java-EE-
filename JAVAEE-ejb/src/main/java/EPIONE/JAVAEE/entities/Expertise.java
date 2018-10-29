package EPIONE.JAVAEE.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Expertise implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String content;

	@JsonIgnore
	@ManyToOne
	private User doctor;

	public Expertise() {
	}

	public Expertise(String content) {
		this.content = content;
	}

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

	public User getDoctor() {
		return doctor;
	}

	public void setDoctor(User doctor) {
		this.doctor = doctor;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Expertise expertise = (Expertise) o;
		return id == expertise.id &&
				Objects.equals(content, expertise.content) &&
				Objects.equals(doctor, expertise.doctor);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, content, doctor);
	}

	@Override
	public String toString() {
		return "Expertise{" +
				"id=" + id +
				", content='" + content + '\'' +
				'}';
	}
}
