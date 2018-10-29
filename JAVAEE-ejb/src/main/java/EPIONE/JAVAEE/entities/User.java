package EPIONE.JAVAEE.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Transactional
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
    private String state;


    public User() {
    }

    public User(String firstName) {
        this.firstName = firstName;
    }

    public User(String firstName, String lastName, String speciality, String urlPhoto) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.speciality = speciality;

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


    @OneToMany(mappedBy = "doctor",fetch = FetchType.EAGER)
    private Set<Expertise> expertiseList ;


    @OneToMany(mappedBy = "doctor",fetch = FetchType.EAGER)
    private Set<Transport> transportList;

    @ManyToMany
    private List<Conversation> conversations;

    @OneToMany(mappedBy = "user")
    private List<MessageDoctor> messageDoctors;

    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<RDV> rendezVous;


    @OneToMany(mappedBy = "doctors",fetch= FetchType.EAGER)
    private Set<RDV> rendezVousDoctors;
    @OneToOne
    private PathDoctors pathDoctors;


    @XmlAttribute(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement(name = "firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @XmlElement(name = "lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @XmlElement(name = "speciality")
    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @XmlElement(name = "tariff")
    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    @XmlElement(name = "paimentMethode")
    public String getPaimentMethode() {
        return paimentMethode;
    }

    @XmlElement(name = "language")
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @XmlElement(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPaimentMethode(String paimentMethode) {
        this.paimentMethode = paimentMethode;
    }

    public Set<Expertise> getExpertiseList() {
        return expertiseList;
    }

    public void setExpertiseList(Set<Expertise> expertiseList) {
        this.expertiseList = expertiseList;
    }

    public void setTransportList(Set<Transport> transportList) {
        this.transportList = transportList;
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

    @XmlElement(name = "urlPhoto")
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

    @XmlElement(name = "email")
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

    @XmlElement(name = "address")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    public Set<Transport> getTransportList() {
        return transportList;
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
                Objects.equals(conversations, user.conversations) &&
                Objects.equals(messageDoctors, user.messageDoctors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, password, birthday, UrlPhoto, enabled, lastLogin, confirmation, confirmationToken, role, phoneNumber, email, username, address, conversations, messageDoctors);
    }

    public Set<RDV> getRendezVous() {
        return rendezVous;
    }

    public void setRendezVous(Set<RDV> rendezVous) {
        this.rendezVous = rendezVous;
    }

    public Set<RDV> getRendezVousDoctors() {
        return rendezVousDoctors;
    }

    public void setRendezVousDoctors(Set<RDV> rendezVousDoctors) {
        this.rendezVousDoctors = rendezVousDoctors;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", UrlPhoto='" + UrlPhoto + '\'' +
                ", tariff='" + tariff + '\'' +
                ", paimentMethode='" + paimentMethode + '\'' +
                ", language='" + language + '\'' +
                ", speciality='" + speciality + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                ", enabled=" + enabled +
                ", lastLogin=" + lastLogin +
                ", confirmation='" + confirmation + '\'' +
                ", confirmationToken='" + confirmationToken + '\'' +
                ", role=" + role +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", state='" + state + '\'' +
                ", address=" + address +
                ", expertiseList=" + expertiseList +
                ", transportList=" + transportList +
                ", conversations=" + conversations +
                ", messageDoctors=" + messageDoctors +
                ", rendezVous=" + rendezVous +
                ", rendezVousDoctors=" + rendezVousDoctors +
                ", pathDoctors=" + pathDoctors +
                '}';
    }
}
