package EPIONE.JAVAEE.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class MedicalPath implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private boolean status;
	private String justification;
	

	
	@OneToOne
	private RDV rendezVous;


	@OneToMany(mappedBy = "path")
	private List<PathDoctors> doctorPath;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}



	public RDV getRendezVous() {
		return rendezVous;
	}

	public void setRendezVous(RDV rendezVous) {
		this.rendezVous = rendezVous;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MedicalPath that = (MedicalPath) o;
		return id == that.id &&
				status == that.status &&
				Objects.equals(justification, that.justification) &&
				Objects.equals(rendezVous, that.rendezVous);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, status, justification, rendezVous);
	}

	@Override
	public String toString() {
		return "MedicalPath{" +
				"id=" + id +
				", status=" + status +
				", justification='" + justification + '\'' +

				", rendezVous=" + rendezVous +
				'}';
	}


}
