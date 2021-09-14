package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Bet;
import domain.Event;
import domain.Pronostic;
import domain.Question;

public class CreateBetGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelCalendar = new JLabel();
	private final JLabel jLabelQuestion = new JLabel();
	private final JLabel jLabelEvent = new JLabel();
	private final JLabel jLabelPronos = new JLabel();
	private final JLabel jLabelError = new JLabel();

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;

	private JScrollPane jScrollEvents = new JScrollPane();
	private JScrollPane jScrollQuestions = new JScrollPane();
	private JScrollPane jScrollPronostic = new JScrollPane();///

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents = new JTable();
	private JTable tableQueries = new JTable();
	private JTable tablePronos = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelPronos;

	private BLFacade facade = LoginGUI.getBusinessLogic();

	private String[] columnNamesEvents = new String[] { "Evento#", "Evento" };
	private String[] columnNamesQueries = new String[] { "Pregunta#", "Pregunta" };
	private String[] columnNamesPronos = new String[] { "Pronostico#", "Pronostico" };

	private Event selectedEvent;
	private Question selectedQuestion;
	private Pronostic selectedPronos;

	private JButton jButtonCreateBet = new JButton();
	private JButton jButtonClose = new JButton();///

	private JSpinner jSpinnerBetCant = new JSpinner();

	private final JLabel jLabelMinimumBet = new JLabel("Apuesta minima: ");
	private JLabel jLabelBetCant = new JLabel("Cantidad a apostar: ");
	private JLabel jLabelGain = new JLabel("Ganancia minima: ");
	private JLabel jLabelUsBalance = new JLabel("Saldo actual: 0.0 €");

	public CreateBetGUI() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.setSize(new Dimension(700, 589));
		this.setTitle("Crear apuestas");

		this.getContentPane().setLayout(null);
		this.getContentPane().add(jLabelCalendar, null);
		this.getContentPane().add(jLabelQuestion, null);
		this.getContentPane().add(jLabelEvent, null);
		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jCalendar1, null);
		this.getContentPane().add(jScrollEvents, null);
		this.getContentPane().add(jScrollQuestions, null);
		this.getContentPane().add(jLabelError, null);
		this.getContentPane().add(jButtonCreateBet, null);
		this.getContentPane().add(jScrollPronostic, null);
		this.getContentPane().add(jLabelPronos, null);
		this.getContentPane().add(jSpinnerBetCant);
		this.getContentPane().add(jLabelBetCant);
		this.getContentPane().add(jLabelMinimumBet);
		this.getContentPane().add(jLabelGain);
		this.getContentPane().add(jLabelUsBalance);

		jLabelCalendar.setText("Calendario");
		jLabelCalendar.setBounds(new Rectangle(40, 19, 225, 21));

		jLabelEvent.setText("Eventos");
		jLabelEvent.setBounds(295, 19, 259, 16);

		jLabelQuestion.setText("Preguntas");
		jLabelQuestion.setBounds(40, 226, 395, 14);

		jLabelPronos.setText("Pronosticos");
		jLabelPronos.setBounds(40, 374, 395, 14);

		jLabelError.setText("");
		jLabelError.setForeground(Color.RED);
		jLabelError.setBounds(40, 516, 395, 20);

		jButtonCreateBet.setText("Crear apuesta");
		jButtonCreateBet.setEnabled(true);
		jButtonCreateBet.setBounds(473, 448, 139, 23);

		jButtonClose.setText("Cerrar");
		jButtonClose.setBounds(new Rectangle(561, 498, 77, 25));

		jScrollEvents.setBounds(new Rectangle(292, 50, 346, 150));
		jScrollEvents.setViewportView(tableEvents);

		jScrollQuestions.setBounds(new Rectangle(40, 251, 395, 106));
		jScrollQuestions.setViewportView(tableQueries);

		jScrollPronostic.setBounds(new Rectangle(40, 399, 395, 106));
		jScrollPronostic.setViewportView(tablePronos);

		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);

		tableModelPronos = new DefaultTableModel(null, columnNamesPronos);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);

		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);

		tablePronos.setModel(tableModelPronos);
		tablePronos.getColumnModel().getColumn(0).setPreferredWidth(25);
		tablePronos.getColumnModel().getColumn(1).setPreferredWidth(268);

		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar1.getDate());

		CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);

		jSpinnerBetCant.setBounds(473, 403, 139, 20);

		jLabelBetCant.setBounds(473, 378, 124, 14);

		jLabelMinimumBet.setBounds(473, 318, 139, 14);

		jLabelGain.setBounds(473, 343, 165, 14);

		jLabelUsBalance.setBounds(473, 278, 139, 14);

		// Listeners

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {

				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();

					Date firstDay = UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);

					if (monthAct != monthAnt) {
						if (monthAct == monthAnt + 2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2
							// de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt + 1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = LoginGUI.getBusinessLogic();

						datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar1.getDate());
					}

					CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);

					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						BLFacade facade = LoginGUI.getBusinessLogic();

						Vector<domain.Event> events = facade.getEvents(firstDay);

						if (events.isEmpty())
							jLabelEvent.setText("Eventos");
						else
							jLabelEvent.setText("Eventos");
						for (domain.Event ev : events) {
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events " + ev);

							row.add(ev.getEvId());
							row.add(ev.getEvDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(40);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not
																												// shown
																												// in
																												// JTable
					} catch (Exception e1) {

						jLabelQuestion.setText(e1.getMessage());
					}

				}
			}
		});
		// Fin code for JCalendar

		/// 2
		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableEvents.getSelectedRow();
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(i, 2); // obtain ev object
				selectedEvent = ev;
				Vector<Question> queries = ev.getEvQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);
				tableModelQueries.setColumnCount(3); // another column added to allocate ev objects

				for (domain.Question q : queries) {
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuesId());
					row.add(q.getQuesDescription());
					row.add(q);// q object added in order to obtain it with tableModelEvents.getValueAt(i,2)
					tableModelQueries.addRow(row);
				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
				tableQueries.getColumnModel().removeColumn(tableQueries.getColumnModel().getColumn(2)); // not visible
																										// on the table
																										// queries
			}
		});

		/// 3
		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableQueries.getSelectedRow();
				// domain.Question q = (domain.Question) tableModelQueries.getValueAt(i, 2); //
				// obtain q object
				Question q = selectedEvent.getEvQuestions().get(i); // obtain q object
				selectedQuestion = q;
				ArrayList<Pronostic> pronostics = q.getPronostics();

				tableModelPronos.setDataVector(null, columnNamesPronos);

				for (domain.Pronostic p : pronostics) {
					Vector<Object> row = new Vector<Object>();

					row.add(p.getProId());
					row.add(p.getProDescription());
					tableModelPronos.addRow(row);
				}
				tablePronos.getColumnModel().getColumn(0).setPreferredWidth(25);
				tablePronos.getColumnModel().getColumn(1).setPreferredWidth(268);

				jLabelMinimumBet.setText("Apuesta minima: " + selectedQuestion.getQuesBetMinimum() + " �");
			}
		});

		/// 4
		tablePronos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tablePronos.getSelectedRow();
				Pronostic p = selectedQuestion.getPronostics().get(i); // obtain p object
				selectedPronos = p;

				jLabelGain.setText("Ganancia: " + selectedPronos.getProGain());

			}
		});

		jButtonCreateBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				boolean error = false;
				Integer betCant = 0, betMin = 0;

				if (selectedPronos == null) {
					jLabelError.setText("Debe escoger un pronostico.");
					error = true;
				}
				if (selectedQuestion == null) {
					jLabelError.setText("Debe escoger una pregunta.");
					error = true;
				}
				if (selectedEvent == null) {
					jLabelError.setText("Debe escoger un evento.");
					error = true;
				}
				if ((selectedEvent != null) && (selectedQuestion != null) && (selectedPronos != null)) {
					betCant = (Integer) jSpinnerBetCant.getValue();
					betMin = (int) selectedQuestion.getQuesBetMinimum();

					if (betCant > facade.getU().getUsBalance()) {
						jLabelError.setText("No tiene suficiente saldo");
						error = true;
					}
					if (betCant <= betMin) {
						jLabelError.setText("La apuesta debe superar el minimo");
						error = true;
					}
					if (new Date().compareTo(selectedEvent.getEvDate()) > 0) {
						jLabelError.setText("Debe escoger un evento que no haya pasado");
						error = true;
					}
				}

				if (!error) {

					jLabelError.setText("Apuesta anadida!");
					// Cambiar el saldo del user
					float newBalance = facade.getU().getUsBalance() - betCant;
					facade.getU().setUsBalance(newBalance);
					facade.updateUserBalance(facade.getU(), newBalance);
					// Mostrar nuevo saldo
					jLabelUsBalance.setText("Saldo actual: " + newBalance + " �");
					Bet b = facade.createBet(betCant, selectedPronos, facade.getU());
					System.out.println("::: Apuesta anadida --> id: " + b.getBetId() + " :::");
					facade.updateEventBets(selectedEvent); // aumenta la cantidad de apuesta en el evento +1
					facade.updateEventGambler(selectedEvent, facade.getU()); // agrega el usuario que realiza la apuesta
					facade.updateUser(facade.getU(), b); // vinculando apuesta con usuario // a lista de apostadores del
															// evento
					jSpinnerBetCant.setValue(0);
				}

			}
		});

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});

		// Fin listeners

	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
