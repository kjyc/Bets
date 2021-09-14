package businessLogic;

//hola
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Bet;
import domain.Event;
import domain.Pronostic;
import domain.Question;
import domain.Sport;
import domain.User;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {
	DataAccess dbManager;
	User u;

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			dbManager = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
			dbManager.close();
		}

	}

	public User getU() {
		dbManager.open(false);
		User user = dbManager.findUser(u.getUsName());
		dbManager.close();
		setU(user);
		return user;
	}

	public void setU(User u) {
		this.u = u;
	}

	public BLFacadeImplementation(DataAccess da) {

		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			da.open(true);
			da.initializeDB();
			da.close();

		}
		dbManager = da;
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished        if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	@WebMethod
	public Question createQuestion(Event event, String question, float betMinimum)
			throws EventFinished, QuestionAlreadyExist {

		// The minimum bed must be greater than 0
		dbManager.open(false);
		Question qry = null;

		if (new Date().compareTo(event.getEvDate()) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));

		qry = dbManager.createQuestion(event, question, betMinimum);

		dbManager.close();

		return qry;
	};

	public Bet createBet(Integer betCant, Pronostic betPronos, User betUser) {
		dbManager.open(false);
		Bet b = dbManager.createBet(betCant, betPronos, betUser);
		dbManager.close();
		return b;
	}

	/**
	 * This method invokes the data access to retrieve the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public Vector<Event> getEvents(Date date) {
		dbManager.open(false);
		Vector<Event> events = dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

	@WebMethod
	public List<Event> allEvents() {
		dbManager.open(false);
		List<Event> allEvents = dbManager.allEvents();
		dbManager.close();
		return allEvents;
	}

	public List<Event> searchEvents(Sport sp) {
		dbManager.open(false);
		List<Event> list = dbManager.searchEvents(sp);
		dbManager.close();
		return list;
	}

	/**
	 * This method invokes the data access to retrieve the dates a month for which
	 * there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@WebMethod
	public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date> dates = dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}

	public void close() {
		DataAccess dB4oManager = new DataAccess(false);

		dB4oManager.close();

	}

	public void updateEventClosed(Event e, boolean b) {
		dbManager.open(false);
		dbManager.updateEventClosed(e, b);
		dbManager.close();
	}

	/**
	 * This method invokes the data access to initialize the database with some
	 * events and questions. It is invoked only when the option "initialize" is
	 * declared in the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD() {
		dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}

	public User findUser(String name) {
		dbManager.open(false);
		User u = dbManager.findUser(name);
		dbManager.close();
		return u;
	}

	public Bet findBet(Integer id) {
		dbManager.open(false);
		Bet b = dbManager.findBet(id);
		dbManager.close();
		return b;
	}

	public Pronostic findPronostic(String s) {
		dbManager.open(false);
		Pronostic p = dbManager.findPronostic(s);
		dbManager.close();
		return p;
	}

	public void changeEvent(Event e, String newName, Date newDate) {
		dbManager.open(false);
		dbManager.changeEvent(e, newName, newDate);
		dbManager.close();
	}

	public boolean login(String name, String pass) {
		dbManager.open(false);
		User u = dbManager.findUser(name);
		dbManager.close();

		if (u != null) {
			if (u.getUsPassword().equals(pass)) {
				return true;
			}
		}
		return false;
	}

	public void storeUser(String nameUser, String pass, boolean isAdmin) {
		dbManager.storeUser(nameUser, pass, isAdmin);
	}

	public Question findQuestion(Question n) {
		return dbManager.findQuestion(n);
	}

	public Pronostic createPronostic(int g, String name, Question q) {
		dbManager.open(false);
		Pronostic p = dbManager.storePronostic(g, name, q);
		dbManager.close();
		return p;
	}

	public void updateUserBalance(User usuarioLogeado, float bet) {
		dbManager.open(false);
		dbManager.updateUserBalance(usuarioLogeado, bet);
		dbManager.close();
	}

	public List<User> getAllUsers() {
		dbManager.open(false);
		List<User> u = dbManager.allUsers();
		dbManager.close();
		return u;
	}

	public void deleteUser(String name) {
		dbManager.open(false);
		dbManager.deleteUser(name);
		dbManager.close();
	}

	public Event findEvent(String eventName, Date eDate) {
		return dbManager.findEvent(eventName, eDate);
	}

	public Event createEvent(String eName, Date eDate, Sport s) {
		dbManager.open(false);
		Event e = null;
		// No se pueden crear eventos ni para hoy ni para dias pasados
		if (new Date().compareTo(eDate) > 0) {
			return e;
		}
		e = dbManager.createEvent(eName, eDate, s);
		dbManager.close();
		return e;
	}

	public Sport createSport(String spDescription) {
		dbManager.open(false);
		Sport sp = dbManager.createSport(spDescription);
		dbManager.close();
		return sp;
	}

	public Sport findSport(String description) {
		dbManager.open(false);
		Sport sp = dbManager.findSport(description);
		dbManager.close();
		return sp;
	}

	public List<Sport> getAllSports() {
		dbManager.open(false);
		List<Sport> sp = dbManager.allSports();
		dbManager.close();
		return sp;
	}

	public void updateSport(Sport sp, Event ev) {
		dbManager.open(false);
		dbManager.updateSport(sp, ev);
		dbManager.close();
	}

	public void updateQuestion(Question q, Pronostic pro) {
		dbManager.open(false);
		dbManager.updateQuestion(q, pro);
		dbManager.close();
	}

	public void updateEventGambler(Event e, User u) {
		dbManager.open(false);
		dbManager.updateEventGambler(e, u);
		dbManager.close();
	}

	public void updateEventBets(Event e) {
		dbManager.open(false);
		dbManager.updateEventBets(e);
		dbManager.close();
	}

	public void updateUser(User us, Bet b) {
		dbManager.open(false);
		dbManager.updateUser(us, b);
		dbManager.close();
	}

	public void updatePronosResult(Pronostic p, boolean b) {
		dbManager.open(false);
		dbManager.updatePronosResult(p, b);
		dbManager.close();
	}

}
