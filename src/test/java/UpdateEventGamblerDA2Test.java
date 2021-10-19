import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import configuration.ConfigXML;
import dataAccess.DataAccessYovera2;
import domain.Event;
import domain.User;
import utility.TestUtilityDataAccess;

class UpdateEventGamblerDA2Test {

	static DataAccessYovera2 sut = new DataAccessYovera2(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));
	static TestUtilityDataAccess testDA = new TestUtilityDataAccess();

	private Event ev;
	private User us1;
	private User us2;

	@Test
	@DisplayName("Test 1: El evento es nulo.")
	void test1() {
		testDA.open();
		ev = null;
		us1 = testDA.addUser("Juan", "password", false);
		testDA.close();

		testDA.open();
		assertThrows(NullPointerException.class, () -> sut.updateEventGambler(ev, us1));
		testDA.close();

		testDA.open();
		System.out.println("Removed user " + testDA.removeUser(us1));
		testDA.close();
	}

	@Test
	@DisplayName("Test 2: El usuario es nulo.")
	void test2() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			testDA.open();
			ev = testDA.addEvent(1, "Event description", oneDate);
			us1 = null;
			testDA.close();

			testDA.open();
			assertThrows(NullPointerException.class, () -> sut.updateEventGambler(ev, us1));
			testDA.close();
		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		} finally {
			testDA.open();
			System.out.println("Removed event " + testDA.removeEvent(ev));
			testDA.close();
		}
	}

	@Test
	@DisplayName("Test 3: El evento no esta en la BD.")
	void test3() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			testDA.open();
			ev = new Event(1, "Event description", oneDate);
			us1 = testDA.addUser("Juan", "password", false);
			testDA.close();

			testDA.open();
			assertThrows(NullPointerException.class, () -> sut.updateEventGambler(ev, us1));
			testDA.close();

		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		} finally {
			testDA.open();
			System.out.println("Removed user " + testDA.removeUser(us1));
			testDA.close();
		}
	}

	@Test
	@DisplayName("Test 4: El usuario no esta en la BD.")
	void test4() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			testDA.open();
			ev = testDA.addEvent(1, "Event description", oneDate);
			us1 = new User("Juan", "password", false);
			testDA.close();

			testDA.open();
			assertThrows(NullPointerException.class, () -> sut.updateEventGambler(ev, us1));
			testDA.close();

		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		} finally {
			testDA.open();
			System.out.println("Removed event " + testDA.removeEvent(ev));
			testDA.close();
		}
	}

	@Test
	@DisplayName("Test 5: Evento y usuario estan en la BD.")
	void test5() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			testDA.open();
			ev = testDA.addEvent(1, "Event description", oneDate);
			us1 = testDA.addUser("Juan", "password", false);
			testDA.close();

			testDA.open();
			sut.updateEventGambler(ev, us1);
			assertTrue(testDA.getEvent(ev.getEvId()).getGamblers().contains(testDA.getUser(us1.getUsName())));
			testDA.close();

		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		} finally {
			testDA.open();
			System.out.println("Removed event " + testDA.removeEvent(ev));
			testDA.close();
		}
	}

	/*@Test
	@DisplayName("Test 6: Hay usuarios en la lista.")
	void test6() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			testDA.open();
			ev = testDA.addEvent(1, "Event description", oneDate);
			us1 = testDA.addUser("Juan", "password", false);
			System.out.println("User added to event " + testDA.addUserToEvent(ev.getEvId(), us1.getUsName()));
			us2 = testDA.addUser("Pablo", "contrasena", false);
			ev = testDA.getEvent(ev.getEvId());
			testDA.close();

			testDA.open();
			sut.updateEventGambler(ev, us2);
			assertEquals("Pablo", testDA.getEvent(ev.getEvId()).getGamblers().get(1).getUsName());
			testDA.close();

		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		} finally {
			testDA.open();
			System.out.println("Removed event " + testDA.removeEvent(ev));
			testDA.close();
		}
	}*/

	/*@Test
	@DisplayName("Test 7: El usuario ya existe en la lista.")
	void test7() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			testDA.open();
			ev = testDA.addEvent(1, "Event description", oneDate);
			us1 = testDA.addUser("Juan", "password", false);
			System.out.println("User added to event " + testDA.addUserToEvent(ev.getEvId(), us1.getUsName()));
			ev = testDA.getEvent(ev.getEvId());
			testDA.close();

			testDA.open();
			sut.updateEventGambler(ev, us1);
			// assertTrue(testDA.getEvent(ev.getEvId()).getGamblers().contains(testDA.getUser(us1.getUsName())));
			assertTrue(testDA.isUserInEvent(ev.getEvId(), us1.getUsName()));
			testDA.close();

		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		} finally {
			testDA.open();
			System.out.println("Removed event " + testDA.removeEvent(ev));
			testDA.close();
		}
	}*/
}
