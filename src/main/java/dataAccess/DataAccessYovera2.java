package dataAccess;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Bet;
import domain.Event;
import domain.Pronostic;
import domain.Question;
import domain.Sport;
import domain.User;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccessYovera2 {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	public DataAccessYovera2(boolean initializeMode) {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccessYovera2() {
		this(false);
	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();
		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}

			Sport sp1 = new Sport("Futbol");

			Event ev1 = sp1.setSpEvents("Atlético-Athletic", UtilDate.newDate(year, month - 2, 17));
			Event ev2 = sp1.setSpEvents("Eibar-Barcelona", UtilDate.newDate(year, month, 17));
			Event ev3 = sp1.setSpEvents("Getafe-Celta", UtilDate.newDate(year, month, 17));
			Event ev4 = sp1.setSpEvents("Alavés-Deportivo", UtilDate.newDate(year, month, 17));
			Event ev5 = sp1.setSpEvents("Español-Villareal", UtilDate.newDate(year, month, 17));
			Event ev6 = sp1.setSpEvents("Las Palmas-Sevilla", UtilDate.newDate(year, month, 17));
			Event ev7 = sp1.setSpEvents("Malaga-Valencia", UtilDate.newDate(year, month, 17));
			Event ev8 = sp1.setSpEvents("Girona-Leganés", UtilDate.newDate(year, month, 17));
			Event ev9 = sp1.setSpEvents("Real Sociedad-Levante", UtilDate.newDate(year, month, 17));
			Event ev10 = sp1.setSpEvents("Betis-Real Madrid", UtilDate.newDate(year, month, 17));

			Event ev11 = sp1.setSpEvents("Atletico-Athletic", UtilDate.newDate(year, month, 1));
			Event ev12 = sp1.setSpEvents("Eibar-Barcelona", UtilDate.newDate(year, month, 1));
			Event ev13 = sp1.setSpEvents("Getafe-Celta", UtilDate.newDate(year, month, 1));
			Event ev14 = sp1.setSpEvents("Alavés-Deportivo", UtilDate.newDate(year, month, 1));
			Event ev15 = sp1.setSpEvents("Español-Villareal", UtilDate.newDate(year, month, 1));
			Event ev16 = sp1.setSpEvents("Las Palmas-Sevilla", UtilDate.newDate(year, month, 1));

			Event ev17 = sp1.setSpEvents("Málaga-Valencia", UtilDate.newDate(year, month + 1, 28));
			Event ev18 = sp1.setSpEvents("Girona-Leganés", UtilDate.newDate(year, month + 1, 28));
			Event ev19 = sp1.setSpEvents("Real Sociedad-Levante", UtilDate.newDate(year, month + 1, 28));
			Event ev20 = sp1.setSpEvents("Betis-Real Madrid", UtilDate.newDate(year, month + 1, 28));

			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;

			q1 = ev1.addEvQuestion("¿Quién ganará el partido?", 1);
			q2 = ev1.addEvQuestion("¿Quién meterá el primer gol?", 2);
			q3 = ev11.addEvQuestion("¿Quién ganará el partido?", 1);
			q4 = ev11.addEvQuestion("¿Cuántos goles se marcarán?", 2);
			q5 = ev17.addEvQuestion("¿Quién ganará el partido?", 1);
			q6 = ev17.addEvQuestion("¿Habrá goles en la primera parte?", 2);

			Pronostic p1 = q1.addQuesPronostic(4, "Ganara el Athletic.");
			Pronostic p2 = q1.addQuesPronostic(3, "Ganara el Atlético.");

			db.persist(p1);
			db.persist(p2);

			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6);

			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);

			// agregando usuarios
			User a1 = new User("admin", "admin", true);
			User u2 = new User("user", "user", false);
			u2.setUsBalance(20);

			db.persist(sp1);

			db.persist(a1);
			db.persist(u2);

			db.getTransaction().commit();

			System.out.println("Db initialized");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open(boolean initializeMode) {

		System.out.println("Opening DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		String fileName = c.getDbFilename();
		if (initializeMode) {
			fileName = fileName + ";drop";
			System.out.println("Deleting the DataBase");
		}

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

	/**
	 * Funcion que busca un evento por su descripcion y fecha
	 * 
	 * @param eventName descripcion
	 * @param eDate     fecha
	 * @return evento
	 */
	public Event findEvent(String eventName, Date eDate) {
		try {
			String q = "SELECT e FROM Event e WHERE e.evDescription = '" + eventName + "' AND e.evDate = ?0";
			TypedQuery<Event> query = db.createQuery(q, Event.class).setParameter(0, eDate);

			return query.getResultList().get(0);

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Agrega un usuario a un evento
	 * 
	 * @param e evento
	 * @param u usuario
	 */

	public void updateEventGambler(Event e, User u) {
		if (e == null || u == null)
			throw new NullPointerException("Event or User is null.");

		// Event event = db.find(Event.class, e.getEvId());
		Event event = findEvent(e.getEvDescription(), e.getEvDate());

		if (event == null)
			throw new NullPointerException("Event not in DB.");

		User user = db.find(User.class, u.getUsName());

		if (user == null)
			throw new NullPointerException("User not in DB.");

		db.getTransaction().begin();

		if (event.getGamblers().isEmpty()) {
			event.addGamblers(user);
			db.persist(event);
			System.out.println("Evento actualizado: " + user.getUsName() + " agredo a la lista.");
		} else {
			boolean contains = false;
			for (User us : event.getGamblers()) {
				if (user.getUsName().equals(us.getUsName())) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				event.addGamblers(user);
				db.persist(event);
				System.out.println("Evento actualizado: " + user.getUsName() + " agredo a la lista.");
			} else {
				System.out.println("El evento contiene al usuario: " + user.getUsName());
			}
		}

		db.getTransaction().commit();
	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

}