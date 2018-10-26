package EPIONE.JAVAEE.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String lastName;
    private String UrlPhoto;
    private String tariff;
    private String paimentMethode;
    private String language;
    private String speciality;
    private String password;
	private Date birthday;
	private boolean enabled;
	private Timestamp lastLogin;
	private String confirmation;
	private String confirmationToken;
	private Roles role;
	private int phoneNumber;
	private String email;
	private String username;



	public User() {
	}

	public User(String firstName){
		this.firstName=firstName;
	}
	public User(String firstName, String lastName,String speciality,  String urlPhoto) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.speciality=speciality;
		UrlPhoto = urlPhoto;

	}

    public User(String firstName, String lastName, String speciality, Address address, String urlPhoto) {
        this.firstName = firstName;
        this.lastName = lastName;
        UrlPhoto = urlPhoto;
        this.speciality = speciality;
        this.address = address;
    }

    @OneToOne
	private Address address;

	@ManyToOne
	private DoctorData doctorData;

	@ManyToMany
	private List<Conversation> conversations;

	@OneToMany(mappedBy = "user")
	private List<MessageDoctor> messageDoctors;

	@OneToMany(mappedBy = "users")
	private List<RDV> rendezVous;

	@OneToMany(mappedBy = "doctors")
	private List<RDV> rendezVousDoctors;

	@ManyToMany
	private List<MedicalPath> paths;


	@XmlAttribute(name="id",required=true)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlElement(name="firstName")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@XmlElement(name="lastName")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@XmlElement(name="speciality")
	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

    @XmlElement(name="tariff")
    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }
    @XmlElement(name="paimentMethode")
    public String getPaimentMethode() {
        return paimentMethode;
    }
    @XmlElement(name="language")
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }



    public void setPaimentMethode(String paimentMethode) {
        this.paimentMethode = paimentMethode;
    }



	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

    @XmlElement(name="urlPhoto")
	public String getUrlPhoto() {
		return UrlPhoto;
	}

	public void setUrlPhoto(String urlPhoto) {
		UrlPhoto = urlPhoto;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}

	public String getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    @XmlElement(name="address")
    public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public DoctorData getDoctorData() {
		return doctorData;
	}

	public void setDoctorData(DoctorData doctorData) {
		this.doctorData = doctorData;
	}

	public List<Conversation> getConversations() {
		return conversations;
	}

	public void setConversations(List<Conversation> conversations) {
		this.conversations = conversations;
	}

	public List<MessageDoctor> getMessageDoctors() {
		return messageDoctors;
	}

	public void setMessageDoctors(List<MessageDoctor> messageDoctors) {
		this.messageDoctors = messageDoctors;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return id == user.id &&
				enabled == user.enabled &&
				phoneNumber == user.phoneNumber &&
				Objects.equals(firstName, user.firstName) &&
				Objects.equals(lastName, user.lastName) &&
				Objects.equals(password, user.password) &&
				Objects.equals(birthday, user.birthday) &&
				Objects.equals(UrlPhoto, user.UrlPhoto) &&
				Objects.equals(lastLogin, user.lastLogin) &&
				Objects.equals(confirmation, user.confirmation) &&
				Objects.equals(confirmationToken, user.confirmationToken) &&
				role == user.role &&
				Objects.equals(email, user.email) &&
				Objects.equals(username, user.username) &&
				Objects.equals(address, user.address) &&
				Objects.equals(doctorData, user.doctorData) &&
				Objects.equals(conversations, user.conversations) &&
				Objects.equals(messageDoctors, user.messageDoctors) ;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName, password, birthday, UrlPhoto, enabled, lastLogin, confirmation, confirmationToken, role, phoneNumber, email, username, address, doctorData, conversations, messageDoctors);
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", password='" + password + '\'' +
				", birthday=" + birthday +
				", UrlPhoto='" + UrlPhoto + '\'' +
				", enabled=" + enabled +
				", lastLogin=" + lastLogin +
				", confirmation='" + confirmation + '\'' +
				", confirmationToken='" + confirmationToken + '\'' +
				", role=" + role +
				", phoneNumber=" + phoneNumber +
				", email='" + email + '\'' +
				", username='" + username + '\'' +
				", address=" + address +
				", doctorData=" + doctorData +
				", conversations=" + conversations +
				", messageDoctors=" + messageDoctors +
				'}';
	}
}
