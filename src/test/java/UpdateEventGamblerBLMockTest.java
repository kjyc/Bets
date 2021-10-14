import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Event;
import domain.User;

class UpdateEventGamblerBLMockTest {

	DataAccess dataAccess = Mockito.mock(DataAccess.class);
	Event mockedEvent = Mockito.mock(Event.class);
	User mockedUser = Mockito.mock(User.class);

	BLFacade sut = new BLFacadeImplementation(dataAccess);

	private Event event;
	private User user1;

	public void initializeVaribles() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate;
			oneDate = sdf.parse("05/10/2022");
			event = new Event(1, "Event description", oneDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		user1 = new User("Juan", "password", false);
	}

	@Test
	@DisplayName("Test 1: El evento es nulo.")
	void test1() {
		initializeVaribles();
		event = null;

		Mockito.doThrow(NullPointerException.class).when(dataAccess).updateEventGambler(Mockito.any(Event.class),
				Mockito.any(User.class));

		assertThrows(NullPointerException.class, () -> sut.updateEventGambler(event, user1));
	}

	@Test
	@DisplayName("Test 2: El usuario es nulo.")
	void test2() {
		initializeVaribles();
		user1 = null;

		Mockito.doThrow(NullPointerException.class).when(dataAccess).updateEventGambler(Mockito.any(Event.class),
				Mockito.any(User.class));

		assertThrows(NullPointerException.class, () -> sut.updateEventGambler(event, user1));
	}

	@Test
	@DisplayName("Test 3: El evento no esta en la BD.")
	void test3() {
		initializeVaribles();

		Mockito.doThrow(NullPointerException.class).when(dataAccess).updateEventGambler(Mockito.any(Event.class),
				Mockito.any(User.class));

		assertThrows(NullPointerException.class, () -> sut.updateEventGambler(event, user1));
	}

	@Test
	@DisplayName("Test 4: El usuario no esta en la BD.")
	void test4() {
		initializeVaribles();

		Mockito.doThrow(NullPointerException.class).when(dataAccess).updateEventGambler(Mockito.any(Event.class),
				Mockito.any(User.class));

		assertThrows(NullPointerException.class, () -> sut.updateEventGambler(event, user1));
	}

	@Test
	@DisplayName("Test 5: Evento y usuario estan en la BD.")
	void test5() {
		initializeVaribles();

		Mockito.doNothing().when(dataAccess).updateEventGambler(Mockito.any(Event.class), Mockito.any(User.class));

		sut.updateEventGambler(event, user1);

		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);

		Mockito.verify(dataAccess, Mockito.times(1)).updateEventGambler(eventCaptor.capture(), userCaptor.capture());

		assertEquals(event, eventCaptor.getValue());
		assertEquals(user1, userCaptor.getValue());
	}

}
