package EPIONE.JAVAEE.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class MessageDoctor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    private String object;

    @ManyToOne
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
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
        MessageDoctor that = (MessageDoctor) o;
        return id == that.id &&
                Objects.equals(content, that.content) &&
                Objects.equals(object, that.object) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, object, user);
    }

    @Override
    public String toString() {
        return "MessageDoctor{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", object='" + object + '\'' +
                ", user=" + user +
                '}';
    }
}
