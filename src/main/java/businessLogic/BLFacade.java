package businessLogic;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import domain.Bet;
import domain.Event;
import domain.Pronostic;
import domain.Question;
import domain.Sport;
import domain.User;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade {

	public User getU();

	public void setU(User u);

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
	Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;

	/**
	 * This method retrieves the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public Vector<Event> getEvents(Date date);

	@WebMethod
	public List<Event> searchEvents(Sport sp);

	@WebMethod
	public List<Event> allEvents();

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@WebMethod
	public Vector<Date> getEventsMonth(Date date);

	/**
	 * This method calls the data access to initialize the database with some events
	 * and questions. It is invoked only when the option "initialize" is declared in
	 * the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD();

	@WebMethod
	public User findUser(String name);

	@WebMethod
	public Bet findBet(Integer id);

	@WebMethod
	public Pronostic findPronostic(String s);

	@WebMethod
	public boolean login(String login, String pass);

	@WebMethod
	public void storeUser(String nameUser, String pass, boolean isAdmin);

	@WebMethod
	public Question findQuestion(Question n);

	@WebMethod
	public Pronostic createPronostic(int g, String name, Question q);

	@WebMethod
	public void updateUserBalance(User usuarioLogeado, float bet);

	@WebMethod
	public List<User> getAllUsers();

	@WebMethod
	public void deleteUser(String name);

	@WebMethod
	public Event findEvent(String eventName, Date eDate);

	@WebMethod
	public Event createEvent(String eName, Date eDate, Sport s);

	@WebMethod
	public Bet createBet(Integer betCant, Pronostic betPronos, User betUser);

	@WebMethod
	public Sport createSport(String text);

	@WebMethod
	public List<Sport> getAllSports();

	@WebMethod
	public Sport findSport(String description);

	@WebMethod
	public void updateSport(Sport sp, Event ev);

	@WebMethod
	public void updateEventBets(Event e);

	@WebMethod
	public void changeEvent(Event e, String newName, Date newDate);

	@WebMethod
	public void updateEventGambler(Event e, User u);

	@WebMethod
	public void updateUser(User us, Bet b);

	@WebMethod
	public void updateQuestion(Question q, Pronostic pro);

	@WebMethod
	public void updateEventClosed(Event e, boolean b);

	@WebMethod
	public void updatePronosResult(Pronostic p, boolean b);

}
