package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Event implements Serializable {

	private static final long serialVersionUID = 1L;
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id
	@GeneratedValue
	private Integer evId;
	private String evDescription;
	private Date evDate;
	private boolean closed;

	private Sport sport;
	private int amountBets = 0;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Vector<Question> evQuestions = new Vector<Question>();
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private ArrayList<User> gamblers = new ArrayList<User>();

	public Vector<Question> getEvQuestions() {
		return evQuestions;
	}

	public void setEvQuestions(Vector<Question> questions) {
		this.evQuestions = questions;
	}

	public Event(String description, Date eventDate, Sport s) {
		this.evDescription = description;
		this.evDate = eventDate;
		this.sport = s;
	}

	public Integer getEvId() {
		return evId;
	}

	public void setEvId(Integer eventNumber) {
		this.evId = eventNumber;
	}

	public String getEvDescription() {
		return evDescription;
	}

	public void setEvDescription(String description) {
		this.evDescription = description;
	}

	public Date getEvDate() {
		return evDate;
	}

	public void setEvDate(Date eventDate) {
		this.evDate = eventDate;
	}

	public Sport getSport() {
		return sport;
	}

	public void setSport(Sport sport) {
		this.sport = sport;
	}

	public int getAmountBets() {
		return amountBets;
	}

	public void setAmountBets(int amountBets) {
		this.amountBets = amountBets;
	}

	public ArrayList<User> getGamblers() {
		return gamblers;
	}

	public void addGamblers(User u) {
		this.gamblers.add(u);
	}

	public String toString() {
		return evId + ";" + evDescription;
	}

	/**
	 * This method creates a bet with a question, minimum bet ammount and percentual
	 * profit
	 * 
	 * @param question   to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */
	public Question addEvQuestion(String question, float betMinimum) {
		Question q = new Question(question, betMinimum, this);
		evQuestions.add(q);
		return q;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	/**
	 * This method checks if the question already exists for that event
	 * 
	 * @param question that needs to be checked if there exists
	 * @return true if the question exists and false in other case
	 */
	public boolean doesEvQuestionExists(String question) {
		for (Question q : this.getEvQuestions()) {
			if (q.getQuesDescription().compareTo(question) == 0)
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + evId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (evId != other.evId)
			return false;
		return true;
	}

}
