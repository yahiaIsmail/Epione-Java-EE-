package EPIONE.JAVAEE.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class DoctorData implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private float prices;
	private String description;
	private String languages;
	private String specialty;
	private String additionalInfo;

	@OneToMany(mappedBy = "doctorData")
	private List<User> users;



	@OneToMany(mappedBy = "doctorData")
	private List<Curriculum> curriculumList;



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getPrices() {
		return prices;
	}

	public void setPrices(float prices) {
		this.prices = prices;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLanguages() {
		return languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}


	public List<Curriculum> getCurriculumList() {
		return curriculumList;
	}

	public void setCurriculumList(List<Curriculum> curriculumList) {
		this.curriculumList = curriculumList;
	}


}
