package utility;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import domain.Event;
import domain.User;

public class TestUtilityDataAccess {
	protected EntityManager db;
	protected EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	public TestUtilityDataAccess() {
		System.out.println("Creating TestDataAccess instance");

		open();
	}

	public void open() {

		System.out.println("Opening TestDataAccess instance ");

		String fileName = c.getDbFilename();

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}

	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	public boolean removeEvent(Event ev) {
		System.out.println(">> DataAccessTest: removeEvent");
		Event e = db.find(Event.class, ev.getEvId());
		if (e != null) {
			db.getTransaction().begin();
			db.remove(e);
			db.getTransaction().commit();
			return true;
		} else
			return false;
	}

	public Event addEvent(Integer id, String desc, Date date) {
		System.out.println(">> DataAccessTest: addEvent");
		Event ev = null;
		try {
			db.getTransaction().begin();
			ev = new Event(id, desc, date);
			db.persist(ev);
			db.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ev;
	}

	public Event addEventWithQuestion(String desc, Date d, String question, float qty) {
		System.out.println(">> DataAccessTest: addEvent");
		Event ev = null;
		db.getTransaction().begin();
		try {
			ev = new Event(desc, d);
			ev.addEvQuestion(question, qty);
			db.persist(ev);
			db.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ev;
	}

	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.evDate=?1", Event.class);
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}

	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion=> event= " + event + " question= " + question);
		Event ev = db.find(Event.class, event.getEvId());
		return ev.doesEvQuestionExists(question);

	}

	/* My methods */

	public User addUser(String name, String pass, boolean isAdmin) {
		System.out.println(">> DataAccessTest: addUser");

		User us = new User(name, pass, isAdmin);

		db.getTransaction().begin();
		db.persist(us);
		db.getTransaction().commit();

		return us;
	}

	public boolean removeUser(User u) {
		System.out.println(">> DataAccessTest: removeUser");

		User us = db.find(User.class, u.getUsName());

		if (us != null) {
			db.getTransaction().begin();
			db.remove(us);
			db.getTransaction().commit();
			return true;
		} else
			return false;
	}

	public Event getEvent(Integer id) {
		System.out.println(">> DataAccessTest: getEvent");
		Event e = db.find(Event.class, id);
		return e;
	}

	public User getUser(String name) {
		System.out.println(">> DataAccessTest: getUser");
		User u = db.find(User.class, name);
		return u;
	}
	
	public boolean addUserToEvent(Integer eventId, String userName) {
		System.out.println(">> DataAccessTest: addUserToEvent");
		Event e = db.find(Event.class, eventId);
		User u = db.find(User.class, userName);
		if(u == null) {
			return false;
		}
		if(e != null) {
			db.getTransaction().begin();
			e.addGamblers(u);
			db.persist(e);
			db.getTransaction().commit();
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isUserInEvent(Integer eventId, String userName) {
		boolean b = false;
		Event e = db.find(Event.class, eventId);
		User u = db.find(User.class, userName);
		if(e == null || u == null) {
			b = false;
		} else if(e.getGamblers().contains(u)) {
			b = true;
		}
		return b;
	}
}
