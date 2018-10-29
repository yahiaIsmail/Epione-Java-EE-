package EPIONE.JAVAEE.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class MedicalPath implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Boolean status;
	private String justification;
	private Boolean active;
	private Date createdAt;



	@OneToOne
	private RDV rendezVous;


	@OneToMany(mappedBy = "path",cascade = CascadeType.REMOVE )
	private List<PathDoctors> doctorPath;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean isStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "MedicalPath{" +
				"id=" + id +
				", status=" + status +
				", justification='" + justification + '\'' +
				", active=" + active +

				", doctorPath=" + doctorPath +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MedicalPath path = (MedicalPath) o;
		return id == path.id &&
				status == path.status &&
				active == path.active &&
				Objects.equals(justification, path.justification) &&
				Objects.equals(rendezVous, path.rendezVous) &&
				Objects.equals(doctorPath, path.doctorPath);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, status, justification, active, rendezVous, doctorPath);
	}

	public RDV getRendezVous() {
		return rendezVous;
	}

	public void setRendezVous(RDV rendezVous) {
		this.rendezVous = rendezVous;
	}

	public List<PathDoctors> getDoctorPath() {
		return doctorPath;
	}

	public void setDoctorPath(List<PathDoctors> doctorPath) {
		this.doctorPath = doctorPath;
	}

	public Boolean getStatus() {
		return status;
	}

	public Boolean getActive() {
		return active;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}