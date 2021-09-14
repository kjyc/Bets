package domain;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author JM
 *
 */

@Entity
public class User {

	@Id
	private String usName;
	private String usPassword;
	private boolean usAdmin; // true = admin
	private float usBalance; // saldo
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private ArrayList<Bet> bets = new ArrayList<Bet>();
	
	public User(String usName, String usPassword, boolean usAdmin) {
		super();
		this.usName = usName;
		this.usPassword = usPassword;
		this.usAdmin = usAdmin;
		this.usBalance = 0;
	}

	public String getUsName() {
		return usName;
	}

	public void setUsName(String usName) {
		this.usName = usName;
	}

	public String getUsPassword() {
		return usPassword;
	}

	public void setUsPassword(String usPassword) {
		this.usPassword = usPassword;
	}

	public boolean isUsAdmin() {
		return usAdmin;
	}

	public void setUsAdmin(boolean usAdmin) {
		this.usAdmin = usAdmin;
	}

	public float getUsBalance() {
		return usBalance;
	}

	public void setUsBalance(float usBalance) {
		this.usBalance = usBalance;
	}

	public ArrayList<Bet> getBets() {
		return bets;
	}

	public void addBets(Bet bet) {
		this.bets.add(bet);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (usName != other.usName)
			return false;
		return true;
	}

}
