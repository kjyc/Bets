package domain;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
public class Sport {
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer spId;
	private String spDescription;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private ArrayList<Event> spEvents = new ArrayList<Event>();

	public Sport(String description) {
		this.spDescription = description;
	}

	public Integer getSpId() {
		return spId;
	}

	public String getSpDescription() {
		return spDescription;
	}

	public ArrayList<Event> getSpEvents() {
		return spEvents;
	}

	public void setSpId(Integer spId) {
		this.spId = spId;
	}

	public void setSpDescription(String spDescription) {
		this.spDescription = spDescription;
	}

	public void setSpEvents(Event ev) {
		this.spEvents.add(ev);
	}

	public Event setSpEvents(String evDesc, Date evDate) {
		Event e = new Event(evDesc, evDate, this);
		spEvents.add(e);
		return e;
	}

}
