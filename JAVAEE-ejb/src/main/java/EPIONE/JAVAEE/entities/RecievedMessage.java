package EPIONE.JAVAEE.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class RecievedMessage implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Date dateCreated;
	private String content;
	
	@ManyToOne
	private Conversation conversation;


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
		RecievedMessage that = (RecievedMessage) o;
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
		return "RecievedMessage{" +
				"id=" + id +
				", dateCreated=" + dateCreated +
				", content='" + content + '\'' +
				", conversation=" + conversation +
				'}';
	}
}
