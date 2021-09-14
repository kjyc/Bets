package domain;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Question implements Serializable {

	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer quesId;
	private String quesDescription;
	private float quesBetMinimum;
	@XmlIDREF
	private Event quesEvent;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private ArrayList<Pronostic> quesPronostics = new ArrayList<Pronostic>();
	// boolean para saber si tiene una pronostico
	private boolean containPronostic;

	public Question() {
		super();
	}

	public Question(Integer queryNumber, String query, float betMinimum, Event event) {
		super();
		this.quesId = queryNumber;
		this.quesDescription = query;
		this.quesBetMinimum = betMinimum;
		this.quesEvent = event;
		this.containPronostic = false;
	}

	public Question(String query, float betMinimum, Event event) {
		super();
		this.quesDescription = query;
		this.quesBetMinimum = betMinimum;
		this.containPronostic = false;
		this.quesEvent = event;
	}

	public ArrayList<Pronostic> getPronostics() {
		return quesPronostics;
	}

	public void addPronostic(Pronostic p) {
		if (!containPronostic)
			containPronostic = true;
		quesPronostics.add(p);
	}

	public Pronostic addQuesPronostic(int proGain, String proDescription) {
		Pronostic p = new Pronostic(proGain, proDescription, this);
		quesPronostics.add(p);
		return p;
	}

	public boolean isContainPronostic() {
		return containPronostic;
	}

	public void setContainPronostic(boolean containPronostic) {
		this.containPronostic = containPronostic;
	}

	/**
	 * Get the number of the question
	 * 
	 * @return the question number
	 */
	public Integer getQuesId() {
		return quesId;
	}

	/**
	 * Set the bet number to a question
	 * 
	 * @param questionNumber to be setted
	 */
	public void setQuesId(Integer questionNumber) {
		this.quesId = questionNumber;
	}

	/**
	 * Get the question description of the bet
	 * 
	 * @return the bet question
	 */

	public String getQuesDescription() {
		return quesDescription;
	}

	/**
	 * Set the question description of the bet
	 * 
	 * @param question to be setted
	 */
	public void setQuesDescription(String question) {
		this.quesDescription = question;
	}

	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @return the minimum bet ammount
	 */

	public float getQuesBetMinimum() {
		return quesBetMinimum;
	}

	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @param betMinimum minimum bet ammount to be setted
	 */

	public void setQuesBetMinimum(float betMinimum) {
		this.quesBetMinimum = betMinimum;
	}


	/**
	 * Get the event associated to the bet
	 * 
	 * @return the associated event
	 */
	public Event getQuesEvent() {
		return quesEvent;
	}

	/**
	 * Set the event associated to the bet
	 * 
	 * @param event to associate to the bet
	 */
	public void setQuesEvent(Event event) {
		this.quesEvent = event;
	}

	public String toString() {
		return quesId + ";" + quesDescription + ";" + Float.toString(quesBetMinimum);
	}

}