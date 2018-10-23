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
	private List<Expertise> expertise;
	
	@OneToMany(mappedBy = "doctor")
	private List<Motif> motifs;

	@OneToMany(mappedBy = "doctorData")
	private List<Curriculum> curriculumList;

	@OneToMany(mappedBy = "doctorData")
	private List<Transport> transportsList;

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

	public List<Expertise> getExpertise() {
		return expertise;
	}

	public void setExpertise(List<Expertise> expertise) {
		this.expertise = expertise;
	}

	public List<Curriculum> getCurriculumList() {
		return curriculumList;
	}

	public void setCurriculumList(List<Curriculum> curriculumList) {
		this.curriculumList = curriculumList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DoctorData that = (DoctorData) o;
		return id == that.id &&
				Float.compare(that.prices, prices) == 0 &&
				Objects.equals(description, that.description) &&
				Objects.equals(languages, that.languages) &&
				Objects.equals(specialty, that.specialty) &&
				Objects.equals(additionalInfo, that.additionalInfo) &&
				Objects.equals(users, that.users) &&
				Objects.equals(expertise, that.expertise) &&
				Objects.equals(curriculumList, that.curriculumList);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, prices, description, languages, specialty, additionalInfo, users, expertise, curriculumList);
	}

	@Override
	public String toString() {
		return "DoctorData{" +
				"id=" + id +
				", prices=" + prices +
				", description='" + description + '\'' +
				", languages='" + languages + '\'' +
				", specialty='" + specialty + '\'' +
				", additionalInfo='" + additionalInfo + '\'' +
				", users=" + users +
				", expertise=" + expertise +
				", curriculumList=" + curriculumList +
				'}';
	}
}
