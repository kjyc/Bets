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
public class DataAccess {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	public DataAccess(boolean initializeMode) {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccess() {
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

	////////////////////////////////////////////////////////////////////////
	///////////////////////////// Find Objects /////////////////////////////
	////////////////////////////////////////////////////////////////////////

	public User findUser(String name) {
		// open(falsue);
		User u = db.find(User.class, name);

		return u;
	}

	public Bet findBet(Integer id) {
		Bet b = db.find(Bet.class, id);

		return b;
	}

	public Pronostic findPronostic(String descrip) {
		try {
			String s = "SELECT p FROM Pronostic p WHERE p.proDescription = ?0 ";
			TypedQuery<Pronostic> query = db.createQuery(s, Pronostic.class);
			query.setParameter(0, descrip);
			return query.getResultList().get(0);

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Funcion que busca una pregunta por su questionNumber
	 * 
	 * @param n
	 * @return question
	 */
	public Question findQuestion(Question qu) {
		try {
			open(false);
			db.getTransaction().begin();
			String q = "SELECT o FROM Question o WHERE u.quesId = ?0";
			TypedQuery<Question> query = db.createQuery(q, Question.class).setParameter(0, qu.getQuesId());

			return query.getResultList().get(0);

		} catch (Exception e) {
			return null;
		}
		/*
		 * db.getTransaction().begin(); Question pregunta = db.find(Question.class,
		 * q.getQuestionNumber()); db.close(); return pregunta;
		 */
	}

	/**
	 * Funci�n que busca un evento por su descripcion y fecha
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

	public void changeEvent(Event e, String newName, Date newDate) {
		db.getTransaction().begin();

		Event event = db.find(Event.class, e.getEvId());
		event.setEvDescription(newName);
		if (newDate != null) {
			event.setEvDate(newDate);
		}
		db.persist(event);
		db.getTransaction().commit();
		System.out.println("Evento actualizado en DB: " + event.getEvDescription());
	}

	//////////////////////////// Fin Find Objects ///////////////////////////

	////////////////////////////////////////////////////////////////////////
	//////////////////////////// Create Objects ////////////////////////////
	////////////////////////////////////////////////////////////////////////

	/**
	 * Crea un nuevo evento
	 * 
	 * @param eventName descripcion
	 * @param date      fecha
	 */
	public Event createEvent(String eventName, Date eDate, Sport s) {
		if (findEvent(eventName, eDate) != null)
			return null;

		db.getTransaction().begin();
		Event e = s.setSpEvents(eventName, eDate);
		db.persist(e);
		db.getTransaction().commit();
		return e;
	}

	public void updateEventGambler(Event e, User u) {
		db.getTransaction().begin();

		Event event = db.find(Event.class, e.getEvId());
		if (event.getGamblers().isEmpty()) {
			event.addGamblers(u);
		} else {
			for (User us : event.getGamblers()) {
				if (us.getUsName().equals(u.getUsName())) {
					event.addGamblers(u);
					db.persist(event);
					System.out.println("Evento actualizado: " + u.getUsName() + " agredo a lista de gambler.");
				} else {
					System.out.println("El evento ya contiene al usuario en lista de Gamblers");
				}
			}
		}

		db.getTransaction().commit();
	}

	/**
	 * Crea una nueva apuesta
	 * 
	 * @param eventName descripcion
	 * @param date      fecha
	 */
	public Bet createBet(Integer betCant, Pronostic betPronos, User betUser) {
		db.getTransaction().begin();
		Pronostic prono = db.find(Pronostic.class, betPronos.getProId());
		Bet b = prono.setProBets(betCant, betUser);
		db.persist(prono);
		db.getTransaction().commit();
		return b;
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= " + event + " question= " + question + " betMinimum="
				+ betMinimum);

		Event ev = db.find(Event.class, event.getEvId());

		if (ev.doesEvQuestionExists(question))
			throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addEvQuestion(question, betMinimum);

		// db.persist(q);
		// db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is
		// added in questions
		// property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;

	}

	public Sport createSport(String spDescription) {
		System.out.println(">>Creando nuevo Deporte: " + spDescription);
		db.getTransaction().begin();
		Sport sp = new Sport(spDescription);

		if (findSport(spDescription) == null) {
			db.persist(sp);
			db.getTransaction().commit();
			return sp;
		} else {
			return null;
		}
	}

	public Sport findSport(String spDesciption) {
		try {
			String q = "SELECT sp FROM Sport sp WHERE sp.spDescription = '" + spDesciption + "'";
			TypedQuery<Sport> query = db.createQuery(q, Sport.class);
			return query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}

	public void updateSport(Sport sp, Event ev) {
		open(false);
		db.getTransaction().begin();
		Sport sp1 = findSport(sp.getSpDescription());
		sp1.setSpEvents(ev);
		db.persist(sp1);
		db.getTransaction().commit();
		System.out.println("Evento a�adido a deporte");
	}

	public List<Sport> allSports() {
		try {
			String q = "SELECT sp FROM Sport sp";
			TypedQuery<Sport> query = db.createQuery(q, Sport.class);
			List<Sport> list = query.getResultList();
			return list;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * Metodo que crea un usuario con los valores enviados por parametro y
	 * posteriormente agrega el usuario a la BD
	 * 
	 * @param nameUser
	 * @param pass
	 * @param isAdmin
	 */
	public void storeUser(String nameUser, String pass, boolean isAdmin) {
		open(false);
		db.getTransaction().begin();
		User u = new User(nameUser, pass, isAdmin);
		db.persist(u);
		db.getTransaction().commit();
		System.out.println("Usuario agregado a la DB");
	}

	/**
	 * Agrega un pronostico a la base de datos.
	 * 
	 * @param p
	 */
	public Pronostic storePronostic(int g, String p, Question q) {
		db.getTransaction().begin();
		Pronostic u = new Pronostic(g, p, q);
		db.persist(u);
		db.getTransaction().commit();
		System.out.println("Pronostico agregado a la DB");
		return u;
	}

	//////////////////////////// Fin Creat Objects //////////////////////////

	public void updateUserBalance(User userLogeado, float bet) {
		// open(false);
		db.getTransaction().begin();

		User u = db.find(User.class, userLogeado.getUsName());
		u.setUsBalance(bet);
		db.persist(u);
		db.getTransaction().commit();
		System.out.println("Usuario con saldo actualizado en DB: " + u.getUsBalance() + "��");
	}

	/**
	 * This method retrieves from the database the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.evDate=?1 and ev.closed = ?2",
				Event.class);
		query.setParameter(1, date);
		query.setParameter(2, false);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}

	public List<Event> searchEvents(Sport sp) {
		try {
			TypedQuery<Event> query = db.createQuery("SELECT e FROM Event e WHERE e.sport = ?1", Event.class);
			query.setParameter(1, sp);
			List<Event> list = query.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT ev.evDate FROM Event ev WHERE ev.evDate BETWEEN ?1 and ?2 and ev.closed = ?3",
				Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		query.setParameter(3, false);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}

	/**
	 * guarda todos los usuarios almacenado en la bd en una lista
	 * 
	 * @return lsita de usuarios
	 */
	public List<User> allUsers() {
		try {
			String q = "SELECT u FROM User u";
			TypedQuery<User> query = db.createQuery(q, User.class);
			List<User> list = query.getResultList();

			return list;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * guarda todos los eventos almacenados en la bd en una lista
	 * 
	 * @return lista de eventos
	 */
	public List<Event> allEvents() {
		try {
			String q = "SELECT e FROM Event e";
			TypedQuery<Event> query = db.createQuery(q, Event.class);
			List<Event> list = query.getResultList();
			return list;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public void deleteUser(String name) {
		try {
			db.getTransaction().begin();
			Query query = db.createQuery("DELETE FROM User u WHERE u.usName='" + name + "'");
			int deletedUsers = query.executeUpdate();
			System.out.println("Usuarios borrados: " + deletedUsers);
			db.getTransaction().commit();
			System.out.println("usuario borrado de la DB");
		} catch (Exception e) {

		}

	}

	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion=> event= " + event + " question= " + question);
		Event ev = db.find(Event.class, event.getEvId());
		return ev.doesEvQuestionExists(question);

	}

	public void updateUser(User us, Bet b) {
		// open(false);
		db.getTransaction().begin();

		User u = db.find(User.class, us.getUsName());
		u.addBets(b);
		db.persist(u);
		db.getTransaction().commit();
	}

	public void updatePronosResult(Pronostic p, boolean b) {
		db.getTransaction().begin();

		Pronostic pronos = db.find(Pronostic.class, p.getProId());
		pronos.setProResult(b);
		db.persist(pronos);
		db.getTransaction().commit();
	}

	public void updateEventBets(Event e) {
		// open(false);
		db.getTransaction().begin();

		Event event = db.find(Event.class, e.getEvId());
		event.setAmountBets(event.getAmountBets() + 1);
		db.persist(event);
		db.getTransaction().commit();
		System.out.println("Evento con apuestas actualizado en DB: " + event.getAmountBets());
	}

	public void updateQuestion(Question q, Pronostic pro) {
		// open(false);
		db.getTransaction().begin();

		Question qu = db.find(Question.class, q.getQuesId());
		qu.addPronostic(pro);
		// db.persist(qu);

		db.getTransaction().commit();
		System.out
				.println("Question actualizada en DB: " + pro.getProDescription() + " agregado a lista de pronosticos");
	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	public void updateEventClosed(Event e, boolean b) {
		db.getTransaction().begin();

		Event ev = db.find(Event.class, e.getEvId());
		ev.setClosed(b);
		// db.persist(qu);

		db.getTransaction().commit();
		System.out.println("Evento actualizada en DB: " + ev.getEvDescription() + " cerrado.");
	}

}