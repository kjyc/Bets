package utility;

import java.util.Date;
import java.util.Vector;

import configuration.ConfigXML;
import domain.Event;
import domain.User;

/**
 * Utilities to access Data Base
 * 
 * @author IS2
 *
 */
public class TestUtilityFacadeImplementation {
	private TestUtilityDataAccess dbManagerTest;

	public TestUtilityFacadeImplementation() {
		System.out.println("Creating TestFacadeImplementation instance");
		ConfigXML.getInstance();
		dbManagerTest = new TestUtilityDataAccess();
		dbManagerTest.close();
	}

	public boolean removeEvent(Event ev) {
		dbManagerTest.open();
		boolean b = dbManagerTest.removeEvent(ev);
		dbManagerTest.close();
		return b;

	}

	public Event addEventWithQuestion(String desc, Date d, String question, float qty) {
		dbManagerTest.open();
		Event o = dbManagerTest.addEventWithQuestion(desc, d, question, qty);
		dbManagerTest.close();
		return o;

	}

	public Vector<Event> getEvents(Date date) {
		dbManagerTest.open();
		Vector<Event> events = dbManagerTest.getEvents(date);
		dbManagerTest.close();
		return events;
	}

	/* My methods */

	public User addUser(String name, String password, boolean isAdmin) {
		dbManagerTest.open();
		User u = dbManagerTest.addUser(name, password, isAdmin);
		dbManagerTest.close();
		return u;
	}
	
	public User getUser(String name) {
		dbManagerTest.open();
		User u = dbManagerTest.getUser(name);
		dbManagerTest.close();
		return u;
	}
	
	public boolean removeUser(User u) {
		dbManagerTest.open();
		boolean b = dbManagerTest.removeUser(u);
		dbManagerTest.close();
		return b;

	}
	
	public Event addEvent(Integer id, String eventDesc, Date date) {
		dbManagerTest.open();
		Event e = dbManagerTest.addEvent(id, eventDesc, date);
		dbManagerTest.close();
		return e;
	}
	
	public Event getEvent(Integer id) {
		dbManagerTest.open();
		Event e = dbManagerTest.getEvent(id);
		dbManagerTest.close();
		return e;
	}
	
	public boolean addUserToEvent(Integer eventId, String userName) {
		dbManagerTest.open();
		boolean b = dbManagerTest.addUserToEvent(eventId, userName);
		dbManagerTest.close();
		return b;
	}
	
	public boolean isUserInEvent(Integer eventId, String userName) {
		dbManagerTest.open();
		boolean b = dbManagerTest.isUserInEvent(eventId, userName);
		dbManagerTest.close();
		return b;
	}

}
