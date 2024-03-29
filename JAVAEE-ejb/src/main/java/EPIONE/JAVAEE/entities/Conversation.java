package EPIONE.JAVAEE.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Conversation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @ManyToMany(mappedBy = "conversations", fetch = FetchType.EAGER)
    private Set<User> users;

    @OneToMany(mappedBy = "conversation", fetch = FetchType.EAGER)
    private List<SentMessage> sentMessages;

    @OneToMany(mappedBy = "conversation")
    private List<RecievedMessage> recievedMessages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public List<SentMessage> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<SentMessage> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public List<RecievedMessage> getRecievedMessages() {
        return recievedMessages;
    }

    public void setRecievedMessages(List<RecievedMessage> recievedMessages) {
        this.recievedMessages = recievedMessages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversation that = (Conversation) o;
        return id == that.id &&
                Objects.equals(dateCreated, that.dateCreated) &&
                Objects.equals(sentMessages, that.sentMessages) &&
                Objects.equals(recievedMessages, that.recievedMessages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateCreated, sentMessages, recievedMessages);
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", users=" + users +
                ", sentMessages=" + sentMessages +
                ", recievedMessages=" + recievedMessages +
                '}';
    }
}
