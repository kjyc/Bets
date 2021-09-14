package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Event;
import domain.Sport;

public class CreateEventGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private BLFacade businessLogic = LoginGUI.getBusinessLogic();
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();
	private JTextField jTextEvent;
	private final JButton jButtonClose = new JButton("Cerrar");
	Date firstDay;
	private final JLabel jLabelError = new JLabel("");
	java.util.List<Sport> listSports;
	@SuppressWarnings("rawtypes")
	private DefaultComboBoxModel listaCombo = new DefaultComboBoxModel();

	@SuppressWarnings("unchecked")
	public CreateEventGUI() {
		try {
			jbInit();
			// llenar combo deportes
			listSports = LoginGUI.getBusinessLogic().getAllSports();
			for (Sport sp : listSports) {
				listaCombo.addElement(sp.getSpDescription());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 301));
		this.setTitle("Crear eventos");

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		this.getContentPane().add(jCalendar, null);

		BLFacade facade = LoginGUI.getBusinessLogic();
		datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar.getDate());
		paintDaysWithEvents(jCalendar, datesWithEventsCurrentMonth);

		JLabel jLabelEvent = new JLabel();
		jLabelEvent.setText("Nombre del evento:");
		jLabelEvent.setBounds(300, 62, 168, 14);
		getContentPane().add(jLabelEvent);

		jTextEvent = new JTextField();
		jTextEvent.setBounds(300, 87, 168, 20);
		getContentPane().add(jTextEvent);
		jTextEvent.setColumns(10);

		JButton jButtonCreateEvent = new JButton("Crear evento");
		jButtonCreateEvent.setBounds(330, 177, 113, 23);
		getContentPane().add(jButtonCreateEvent);
		jButtonClose.setBounds(493, 232, 89, 23);

		getContentPane().add(jButtonClose);
		jLabelError.setForeground(Color.RED);
		jLabelError.setBounds(40, 211, 225, 14);

		getContentPane().add(jLabelError);

		@SuppressWarnings("rawtypes")
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(300, 144, 168, 22);
		getContentPane().add(comboBox);
		comboBox.setModel(listaCombo);

		JLabel lblNewLabel = new JLabel("Tipo Deporte:");
		lblNewLabel.setBounds(300, 119, 126, 14);
		getContentPane().add(lblNewLabel);

		// Listeners

		// Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
//				this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
//					public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					System.out.println("calendarAnt: " + calendarAnt.getTime());
					System.out.println("calendarAct: " + calendarAct.getTime());
					/// DateFormat dateformat1 = DateFormat.getDateInstance(1,
					/// jCalendar.getLocale());

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct != monthAnt) {
						if (monthAct == monthAnt + 2) {
							// Si en JCalendar estÃ¡ 30 de enero y se avanza al mes siguiente,
							// devolverÃ­a 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este cÃ³digo se dejarÃ¡ como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt + 1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar.setCalendar(calendarAct);

						BLFacade facade = LoginGUI.getBusinessLogic();

						datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar.getDate());
					}

					paintDaysWithEvents(jCalendar, datesWithEventsCurrentMonth);

					firstDay = UtilDate.trim(calendarAct.getTime());

				}
			}
		});

		jButtonCreateEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String eName = jTextEvent.getText();
				Date eDate = firstDay;
				boolean error = false;

				if (eName.equals("") || eDate == null) {
					jLabelError.setText("La fecha o nombre del evento son incorrectos");
					error = true;
				}

				if ((businessLogic.findEvent(eName, eDate) != null)) {
					jLabelError.setText("Evento existente!");
					error = true;

				}

				if (!error) {
					Sport sp = businessLogic.findSport((String) listaCombo.getSelectedItem());
					Event e1 = businessLogic.createEvent(eName, eDate, sp);

					businessLogic.updateSport(sp, e1);

					if (e1 != null) {
						jLabelError.setText("Evento creado!");
						System.out.println("::: Evento agregado --> " + e1.getEvDescription() + " :::");
					} else
						jLabelError.setText("No se pudo crear el evento!");
				}

			}
		});

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		// Fin Listeners

	}

	public static void paintDaysWithEvents(JCalendar jCalendar, Vector<Date> datesWithEventsCurrentMonth) {
		// For each day with events in current month, the background color for that day
		// is changed.

		Calendar calendar = jCalendar.getCalendar();

		int month = calendar.get(Calendar.MONTH);
		int today = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;

		for (Date d : datesWithEventsCurrentMonth) {

			calendar.setTime(d);
			System.out.println(d);

			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
		}

		calendar.set(Calendar.DAY_OF_MONTH, today);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);

	}

	/**
	 * Metodo para el boton de cerrar ventana
	 * 
	 * @param e
	 */
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}