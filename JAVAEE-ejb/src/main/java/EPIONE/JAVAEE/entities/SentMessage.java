package EPIONE.JAVAEE.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class SentMessage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    private String content;

    @ManyToOne
    private Conversation conversation;

    @ManyToOne
    private User sender;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SentMessage that = (SentMessage) o;
        return id == that.id &&
                Objects.equals(dateCreated, that.dateCreated) &&
                Objects.equals(content, that.content) &&
                Objects.equals(conversation, that.conversation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateCreated, content, conversation);
    }

    @Override
    public String toString() {
        return "SentMessage{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", content='" + content + '\'' +
                ", conversation=" + conversation +
                '}';
    }
}
