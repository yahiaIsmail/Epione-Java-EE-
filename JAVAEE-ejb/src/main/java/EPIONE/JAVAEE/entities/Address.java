package EPIONE.JAVAEE.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Address implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String fullAddress;
	private String longit;
	private String latitude;


	@OneToOne(mappedBy = "address", cascade =CascadeType.PERSIST)
	private User user;

	public Address() {
	}

	public Address(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public Address(String fullAddress, String latitude, String longit) {
		this.fullAddress = fullAddress;
		this.latitude = latitude;
		this.longit = longit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public String getLongit() {
		return longit;
	}

	public void setLongit(String longit) {
		this.longit = longit;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Address address = (Address) o;
		return id == address.id &&
				Objects.equals(fullAddress, address.fullAddress) &&
				Objects.equals(longit, address.longit) &&
				Objects.equals(latitude, address.latitude) &&
				Objects.equals(user, address.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, fullAddress, longit, latitude, user);
	}

	@Override
	public String toString() {
		return "Address{" +
				"id=" + id +
				", fullAddress='" + fullAddress + '\'' +
				", longit='" + longit + '\'' +
				", latitude='" + latitude + '\'' +
				", user=" + user +
				'}';
	}
}
