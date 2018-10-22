package EPIONE.JAVAEE.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Address implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private float longit;
	private float attitude;
	private String street;
	private String city;
	private String town;
	private int postalCode;
	private String country;

	@OneToOne(mappedBy = "address")
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getLongit() {
		return longit;
	}

	public void setLongit(float longit) {
		this.longit = longit;
	}

	public float getAttitude() {
		return attitude;
	}

	public void setAttitude(float attitude) {
		this.attitude = attitude;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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
				Float.compare(address.longit, longit) == 0 &&
				Float.compare(address.attitude, attitude) == 0 &&
				postalCode == address.postalCode &&
				Objects.equals(street, address.street) &&
				Objects.equals(city, address.city) &&
				Objects.equals(town, address.town) &&
				Objects.equals(country, address.country) &&
				Objects.equals(user, address.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, longit, attitude, street, city, town, postalCode, country, user);
	}

	@Override
	public String toString() {
		return "Address{" +
				"id=" + id +
				", longit=" + longit +
				", attitude=" + attitude +
				", street='" + street + '\'' +
				", city='" + city + '\'' +
				", town='" + town + '\'' +
				", postalCode=" + postalCode +
				", country='" + country + '\'' +
				", user=" + user +
				'}';
	}
}
