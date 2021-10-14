import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Event;
import domain.User;
import utility.TestUtilityFacadeImplementation;

class UpdateEventGamblerBLTest {

	private DataAccess da = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));

	private BLFacadeImplementation sut = new BLFacadeImplementation(da);
	private TestUtilityFacadeImplementation testBL = new TestUtilityFacadeImplementation();

	private Event ev;
	private User us1;
	private User us2;

	@Test
	@DisplayName("Test 1: El evento es nulo.")
	void test1() {
		ev = null;
		us1 = testBL.addUser("Juan", "password", false);
		assertThrows(NullPointerException.class, () -> sut.updateEventGambler(ev, us1));
		System.out.println("Removed user " + testBL.removeUser(us1));
	}

	@Test
	@DisplayName("Test 2: El usuario es nulo.")
	void test2() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			ev = testBL.addEvent(1, "Event description", oneDate);
			us1 = null;
			assertThrows(NullPointerException.class, () -> sut.updateEventGambler(ev, us1));
		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		} finally {
			System.out.println("Removed event " + testBL.removeEvent(ev));
		}
	}

	@Test
	@DisplayName("Test 3: El evento no esta en la BD.")
	void test3() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			ev = new Event(1, "Event description", oneDate);
			us1 = testBL.addUser("Juan", "password", false);
			assertThrows(NullPointerException.class, () -> sut.updateEventGambler(ev, us1));
		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		} finally {
			System.out.println("Removed user " + testBL.removeUser(us1));
		}
	}

	@Test
	@DisplayName("Test 4: El usuario no esta en la BD.")
	void test4() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			ev = testBL.addEvent(1, "Event description", oneDate);
			us1 = new User("Juan", "password", false);
			assertThrows(NullPointerException.class, () -> sut.updateEventGambler(ev, us1));
		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		} finally {
			System.out.println("Removed event " + testBL.removeEvent(ev));
		}
	}

	@Test
	@DisplayName("Test 5: Evento y usuario estan en la BD.")
	void test5() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			ev = testBL.addEvent(1, "Event description", oneDate);
			us1 = testBL.addUser("Juan", "password", false);
			sut.updateEventGambler(ev, us1);
			assertEquals("Juan", testBL.getEvent(ev.getEvId()).getGamblers().get(0).getUsName());
		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		} finally {
			System.out.println("Removed event " + testBL.removeEvent(ev));
		}
	}

	@Test
	@DisplayName("Test 6: Hay usuarios en la lista.")
	void test6() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			ev = testBL.addEvent(1, "Event description", oneDate);
			us1 = testBL.addUser("Juan", "password", false);
			System.out.println("User added to event " + testBL.addUserToEvent(ev.getEvId(), us1.getUsName()));
			us2 = testBL.addUser("Pablo", "contrasena", false);
			ev = testBL.getEvent(ev.getEvId());
			sut.updateEventGambler(ev, us2);
			assertEquals("Pablo", testBL.getEvent(ev.getEvId()).getGamblers().get(1).getUsName());
		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		} finally {
			System.out.println("Removed event " + testBL.removeEvent(ev));
		}
	}

	@Test
	@DisplayName("Test 7: El usuario ya existe en la lista.")
	void test7() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			ev = testBL.addEvent(1, "Event description", oneDate);
			us1 = testBL.addUser("Juan", "password", false);
			System.out.println("User added to event " + testBL.addUserToEvent(ev.getEvId(), us1.getUsName()));
			ev = testBL.getEvent(ev.getEvId());
			sut.updateEventGambler(ev, us1);
			assertTrue(testBL.isUserInEvent(ev.getEvId(), us1.getUsName()));
		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		} finally {
			System.out.println("Removed event " + testBL.removeEvent(ev));
		}
	}

}
