package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
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

public class UpdateEventGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	private JLabel jLabelListOfEvents = new JLabel();
	private JLabel jLabelQuery = new JLabel();
	private JLabel jLabelMinBet = new JLabel();
	private JLabel jLabelEventDate = new JLabel();

	private JTextField nuevoNombreEvent = new JTextField();
	private JTextField day = new JTextField();
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton saveButton = new JButton();
	private JButton jButtonClose = new JButton();
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();
	private final JLabel lblNewLabel = new JLabel(); // $NON-NLS-1$ //$NON-NLS-2$
	private JTextField month;
	private JTextField year;

	public UpdateEventGUI(Vector<domain.Event> v) {
		try {
			jbInit(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(Vector<domain.Event> v) throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle("Modificar Evento");

		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setBounds(new Rectangle(275, 108, 250, 20));
		jLabelListOfEvents.setBounds(new Rectangle(275, 77, 277, 20));
		jLabelQuery.setText("Modificar Nombre:");
		jLabelQuery.setBounds(new Rectangle(25, 211, 148, 20));
		nuevoNombreEvent.setBounds(new Rectangle(183, 211, 346, 20));
		jLabelMinBet.setText("Fecha (d\u00EDa/mes/ano):");
		jLabelMinBet.setBounds(new Rectangle(25, 243, 147, 20));
		day.setBounds(new Rectangle(183, 244, 60, 20));

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));
		saveButton.setText("Guardar Cambios");

		saveButton.setBounds(new Rectangle(100, 275, 130, 30));
		saveButton.setEnabled(false);

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e);
			}
		});
		jButtonClose.setText("Salir");
		jButtonClose.setBounds(new Rectangle(275, 275, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		jLabelMsg.setBounds(new Rectangle(275, 139, 305, 20));
		jLabelMsg.setForeground(Color.red);
		// jLabelMsg.setSize(new Dimension(305, 20));

		jLabelError.setBounds(new Rectangle(275, 181, 305, 20));
		jLabelError.setForeground(Color.red);

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(saveButton, null);
		this.getContentPane().add(nuevoNombreEvent, null);
		this.getContentPane().add(jLabelQuery, null);
		this.getContentPane().add(day, null);

		this.getContentPane().add(jLabelMinBet, null);
		this.getContentPane().add(jLabelListOfEvents, null);
		this.getContentPane().add(jComboBoxEvents, null);

		this.getContentPane().add(jCalendar, null);

		BLFacade facade = LoginGUI.getBusinessLogic();
		datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar.getDate());
		paintDaysWithEvents(jCalendar, datesWithEventsCurrentMonth);
		jLabelEventDate.setText("Calendario");

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);
		lblNewLabel.setText("Modificaci\u00F3n de Eventos");
		lblNewLabel.setBounds(201, 21, 277, 14);

		getContentPane().add(lblNewLabel);

		month = new JTextField();
		month.setBounds(new Rectangle(183, 244, 60, 20));
		month.setBounds(253, 244, 60, 20);
		getContentPane().add(month);

		year = new JTextField();
		year.setBounds(new Rectangle(183, 244, 60, 20));
		year.setBounds(323, 244, 60, 20);
		getContentPane().add(year);

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
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());

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

					// Date firstDay = UtilDate.trim(new
					// Date(jCalendar.getCalendar().getTime().getTime()));
					Date firstDay = UtilDate.trim(calendarAct.getTime());

					try {
						BLFacade facade = LoginGUI.getBusinessLogic();

						Vector<domain.Event> events = facade.getEvents(firstDay);

						if (events.isEmpty())
							jLabelListOfEvents.setText(("NoEvents") + ": " + dateformat1.format(calendarAct.getTime()));
						else
							jLabelListOfEvents.setText("Events" + ": " + dateformat1.format(calendarAct.getTime()));
						jComboBoxEvents.removeAllItems();
						System.out.println("Events " + events);

						for (domain.Event ev : events)
							modelEvents.addElement(ev);
						jComboBoxEvents.repaint();

						if (events.size() == 0)
							saveButton.setEnabled(false);
						else
							saveButton.setEnabled(true);

					} catch (Exception e1) {

						jLabelError.setText(e1.getMessage());
					}

				}
			}
		});
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

	private void jButtonCreate_actionPerformed(ActionEvent e) {
		domain.Event event = ((domain.Event) jComboBoxEvents.getSelectedItem());
		try {
			jLabelError.setText("");
			jLabelMsg.setText("");

			// Displays an exception if the query field is empty
			String nuevoNombre = nuevoNombreEvent.getText();

			if (nuevoNombre.length() > 0) {
				// It could be to trigger an exception if the introduced string is not a number
				int dia = Integer.parseInt(day.getText());
				int mes = Integer.parseInt(month.getText());
				int ano = Integer.parseInt(year.getText());

				if (dia <= 0 || mes <= 0 || ano <= 0) {
					jLabelError.setText("Ingresar un valor de fecha valido");
				} else {
					// Obtain the business logic from a StartWindow class (local or remote)
					BLFacade facade = LoginGUI.getBusinessLogic();
					// configuration.UtilDate.newDate(ano, mes, dia);
					facade.changeEvent(event, nuevoNombre, configuration.UtilDate.newDate(ano, mes, dia));
					jLabelMsg.setText("Evento modificado satisfactoriamente");
				}

			} else
				jLabelError.setText("Ingresar un nombre valido");
		} catch (NumberFormatException e1) {
			jLabelError.setText("Ingresar un valor de fecha valido");
		}
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}