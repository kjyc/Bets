package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Bet {

	@Id
	@GeneratedValue
	private Integer betId;
	private Integer betCant;
	private Pronostic betPronostic;
	private User betUser;

	public Bet(Integer betCant, Pronostic betPronos, User betUser) {
		this.betUser = betUser;
		this.betCant = betCant;
		this.betPronostic = betPronos;
	}

	public Integer getBetId() {
		return betId;
	}

	public Integer getBetCant() {
		return betCant;
	}

	public User getBetUser() {
		return betUser;
	}

	public void setBetUser(User betUser) {
		this.betUser = betUser;
	}

	public void setBetCant(Integer betCant) {
		this.betCant = betCant;
	}

	public Pronostic getBetPronostic() {
		return betPronostic;
	}

	public void setBetPronostic(Pronostic betPronostic) {
		this.betPronostic = betPronostic;
	}

}
