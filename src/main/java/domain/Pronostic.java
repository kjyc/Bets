package domain;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author JM
 *
 */

@Entity
public class Pronostic {
	@Id
	@GeneratedValue
	private int proId;
	private String proDescription;
	private float proGain;
	private Question proQuestion;
	private boolean proResult;
	private boolean proClosed;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ArrayList<Bet> proBets;

	public Pronostic(int g, String proDescription, Question proQuestion) {
		super();
		this.proGain = g;
		this.proDescription = proDescription;
		this.proQuestion = proQuestion;
		this.proClosed = false;
		this.proResult = false;
		this.proBets = new ArrayList<Bet>();
		// if (Math.random() < 0.5)
		// this.proResult = true;
	}

	public void setProBets(Bet bet) {
		this.proBets.add(bet);
	}

	public Bet setProBets(int betCant, User betU) {
		Bet b = new Bet(betCant, this, betU);
		this.proBets.add(b);
		return b;
	}

	public int getProId() {
		return proId;
	}

	public String getProDescription() {
		return proDescription;
	}

	public void setProDescription(String proDescription) {
		this.proDescription = proDescription;
	}

	public Question getProQuestion() {
		return proQuestion;
	}

	public void setProQuestion(Question proQuestion) {
		this.proQuestion = proQuestion;
	}

	public float getProGain() {
		return proGain;
	}

	public void setProGain(float proGain) {
		this.proGain = proGain;
	}

	public boolean isProResult() {
		return proResult;
	}

	public void setProResult(boolean proResult) {
		this.proResult = proResult;
	}

	public ArrayList<Bet> getProBets() {
		return proBets;
	}

	public void addProBet(Bet bet) {
		this.proBets.add(bet);
	}

	public boolean isProClosed() {
		return proClosed;
	}

	public void setProClosed(boolean proClosed) {
		this.proClosed = proClosed;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pronostic other = (Pronostic) obj;
		if (proId != other.proId)
			return false;
		return true;
	}

}
