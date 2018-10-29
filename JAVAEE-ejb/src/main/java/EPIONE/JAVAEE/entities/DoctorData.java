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

	@OneToMany(mappedBy = "doctorData")
	private List<Curriculum> curriculumList;

	@OneToMany(mappedBy = "doctor")
	private List<Motif> motifs;

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

	public List<Motif> getMotifs() {
		return motifs;
	}

	public void setMotifs(List<Motif> motifs) {
		this.motifs = motifs;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DoctorData that = (DoctorData) o;

		if (id != that.id) return false;
		if (Float.compare(that.prices, prices) != 0) return false;
		if (description != null ? !description.equals(that.description) : that.description != null) return false;
		if (languages != null ? !languages.equals(that.languages) : that.languages != null) return false;
		if (specialty != null ? !specialty.equals(that.specialty) : that.specialty != null) return false;
		if (additionalInfo != null ? !additionalInfo.equals(that.additionalInfo) : that.additionalInfo != null)
			return false;
		if (users != null ? !users.equals(that.users) : that.users != null) return false;
		if (expertise != null ? !expertise.equals(that.expertise) : that.expertise != null) return false;
		if (curriculumList != null ? !curriculumList.equals(that.curriculumList) : that.curriculumList != null)
			return false;
		if (motifs != null ? !motifs.equals(that.motifs) : that.motifs != null) return false;
		return transportsList != null ? transportsList.equals(that.transportsList) : that.transportsList == null;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (prices != +0.0f ? Float.floatToIntBits(prices) : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (languages != null ? languages.hashCode() : 0);
		result = 31 * result + (specialty != null ? specialty.hashCode() : 0);
		result = 31 * result + (additionalInfo != null ? additionalInfo.hashCode() : 0);
		result = 31 * result + (users != null ? users.hashCode() : 0);
		result = 31 * result + (expertise != null ? expertise.hashCode() : 0);
		result = 31 * result + (curriculumList != null ? curriculumList.hashCode() : 0);
		result = 31 * result + (motifs != null ? motifs.hashCode() : 0);
		result = 31 * result + (transportsList != null ? transportsList.hashCode() : 0);
		return result;
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
				", motifs=" + motifs +
				", transportsList=" + transportsList +
				'}';
	}
}
