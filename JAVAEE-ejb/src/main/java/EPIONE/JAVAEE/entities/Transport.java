package EPIONE.JAVAEE.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String meansOfTransport;

    @ManyToOne
    private DoctorData doctorData;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeansOfTransport() {
        return meansOfTransport;
    }

    public void setMeansOfTransport(String meansOfTransport) {
        this.meansOfTransport = meansOfTransport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transport transport = (Transport) o;
        return id == transport.id &&
                Objects.equals(meansOfTransport, transport.meansOfTransport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, meansOfTransport);
    }

    @Override
    public String toString() {
        return "Transport{" +
                "id=" + id +
                ", meansOfTransport='" + meansOfTransport + '\'' +
                '}';
    }
}
