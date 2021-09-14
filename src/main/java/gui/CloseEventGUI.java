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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Bet;
import domain.Event;
import domain.Pronostic;
import domain.Question;

public class CloseEventGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelCalendar = new JLabel();
	private final JLabel jLabelEvent = new JLabel();
	private final JLabel jLabelError = new JLabel();
	private final JLabel jLabelQuestion = new JLabel();
	private final JLabel jLabelPronos = new JLabel();

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

	private BLFacade businessLogic = LoginGUI.getBusinessLogic();

	private String[] columnNamesEvents = new String[] { "Evento#", "Evento" };
	private String[] columnNamesQueries = new String[] { "Pregunta#", "Pregunta" };
	private String[] columnNamesPronos = new String[] { "Pronostico#", "Pronostico" };

	private Event selectedEvent;
	private Question selectedQuestion;
	private Pronostic selectedPronos;

	private JButton jButtonCloseEvent = new JButton();
	private JButton jButtonClose = new JButton();///
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton jButtonClosePronostic = new JButton("Cerrar pronostico");
	private JRadioButton jRadioButtonWin = new JRadioButton("Acertado");
	private JRadioButton jRadioButtonLose = new JRadioButton("Fallado");

	// Para el listener
	@SuppressWarnings("unused")
	private boolean proResult;

	public CloseEventGUI() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.setSize(new Dimension(700, 670));
		this.setTitle("Cerrar evento");

		this.getContentPane().setLayout(null);
		this.getContentPane().add(jLabelCalendar, null);
		this.getContentPane().add(jLabelEvent, null);
		this.getContentPane().add(jLabelQuestion, null);
		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jCalendar1, null);
		this.getContentPane().add(jScrollEvents, null);
		this.getContentPane().add(jScrollQuestions, null);
		this.getContentPane().add(jScrollPronostic, null);
		this.getContentPane().add(jLabelError, null);
		this.getContentPane().add(jButtonCloseEvent, null);
		this.getContentPane().add(jLabelPronos, null);
		this.getContentPane().add(jButtonClosePronostic);
		this.getContentPane().add(jRadioButtonWin);
		this.getContentPane().add(jRadioButtonLose);

		jLabelCalendar.setText("Calendario");
		jLabelCalendar.setBounds(new Rectangle(40, 19, 225, 21));

		jLabelEvent.setText("Eventos");
		jLabelEvent.setBounds(292, 21, 259, 16);

		jLabelQuestion.setText("Preguntas");
		jLabelQuestion.setBounds(40, 226, 395, 14);

		jLabelPronos.setText("Pronosticos");
		jLabelPronos.setBounds(40, 406, 395, 14);

		jLabelError.setText("");
		jLabelError.setForeground(Color.RED);
		jLabelError.setBounds(40, 600, 395, 20);

		jButtonCloseEvent.setText("Cerrar evento");
		jButtonCloseEvent.setEnabled(false);
		jButtonCloseEvent.setBounds(468, 517, 169, 23);

		jButtonClose.setText("Cerrar");
		jButtonClose.setBounds(new Rectangle(569, 563, 77, 25));

		jScrollEvents.setBounds(new Rectangle(292, 50, 346, 150));
		jScrollEvents.setViewportView(tableEvents);

		jScrollQuestions.setBounds(new Rectangle(40, 251, 395, 129));
		jScrollQuestions.setViewportView(tableQueries);

		jScrollPronostic.setBounds(new Rectangle(40, 431, 395, 150));
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

		datesWithEventsCurrentMonth = businessLogic.getEventsMonth(jCalendar1.getDate());

		CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);

		jButtonClosePronostic.setBounds(468, 474, 170, 23);

		buttonGroup.add(jRadioButtonWin);
		buttonGroup.add(jRadioButtonLose);

		jRadioButtonWin.setBounds(468, 434, 99, 23);
		jRadioButtonLose.setBounds(569, 434, 86, 23);

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
																										// // queries
			}
		});

		/// 3
		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableQueries.getSelectedRow();
				domain.Question q = (domain.Question) tableModelQueries.getValueAt(i, 2); //
				// obtain q object
				selectedQuestion = q;
				fillPronosticTable();
			}
		});

		/// 4
		tablePronos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tablePronos.getSelectedRow();
				domain.Pronostic p = (domain.Pronostic) tableModelPronos.getValueAt(i, 2); //
				// obtain q object
				selectedPronos = p;
			}
		});

		jRadioButtonWin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean error = false;

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
				if (!error) {
					selectedPronos.setProResult(true);
					businessLogic.updatePronosResult(selectedPronos, true);
					jLabelError.setText("Pronostico #" + selectedPronos.getProId() + " ganado.");

				}
			}
		});

		jRadioButtonLose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				boolean error = false;
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
				if (!error) {
					selectedPronos.setProResult(false);
					businessLogic.updatePronosResult(selectedPronos, false);
					jLabelError.setText("Pronostico #" + selectedPronos.getProId() + " perdido.");
				}
			}
		});

		jButtonClosePronostic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean error = false;

				if (new Date().compareTo(selectedEvent.getEvDate()) <= 0) {
					jLabelError.setText("Debe escoger un evento que haya pasado");
					error = true;
				}
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
				if (!error) {
					selectedPronos.setProClosed(true);
					jLabelError.setText("Pronostico #" + selectedPronos.getProId() + " cerrado.");
					ArrayList<Pronostic> pronostics = fillPronosticTable();
					if (allPronosClosed(pronostics))
						jButtonCloseEvent.setEnabled(true);
					fillPronosticTable();

				}

			}
		});

		jButtonCloseEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean error = false;
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
				if (!error) {
					closeEvent();
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

	private ArrayList<Pronostic> fillPronosticTable() {
		ArrayList<Pronostic> pronostics = selectedQuestion.getPronostics();

		tableModelPronos.setDataVector(null, columnNamesPronos);
		tableModelPronos.setColumnCount(3); // another column added to allocate ev objects

		for (domain.Pronostic p : pronostics) {
			Vector<Object> row = new Vector<Object>();
			row.add(p.getProId());
			if (p.isProClosed()) {
				if (p.isProResult())
					row.add(p.getProDescription() + "  (Pronostico ganado)");
				else
					row.add(p.getProDescription() + "  (Pronostico perdido)");
			} else
				row.add(p.getProDescription());
			row.add(p);
			tableModelPronos.addRow(row);
		}
		tablePronos.getColumnModel().getColumn(0).setPreferredWidth(25);
		tablePronos.getColumnModel().getColumn(1).setPreferredWidth(268);
		tablePronos.getColumnModel().removeColumn(tablePronos.getColumnModel().getColumn(2)); // not visible

		return pronostics;

	}

	private void closeEvent() {
		boolean error = false;
		float proGain;
		float plusBalance;
		float newBalance;

		Vector<Question> questions = new Vector<Question>();
		// Como Question ya estaba hecho devuelve Vector en vez de ArrayList
		ArrayList<Pronostic> pronostics = new ArrayList<Pronostic>();
		ArrayList<Bet> bets = new ArrayList<Bet>();

		if (selectedEvent == null) {
			jLabelError.setText("Debe escoger un evento.");
			error = true;
		}
		if (!error) {
			businessLogic.updateEventClosed(selectedEvent, true);
			questions = selectedEvent.getEvQuestions();

			if (!questions.isEmpty())
				for (Question q : questions) { // Recorre las preguntas del evento
					pronostics = q.getPronostics();

					if (!pronostics.isEmpty()) {

						for (Pronostic p : pronostics) { // Recorre los pronosticos de la pregunta
							if (p.isProResult()) {
								proGain = p.getProGain();
								bets = p.getProBets();

								if (!bets.isEmpty())
									for (Bet b : bets) { // Recorre las apuestas del pronostico
										plusBalance = b.getBetCant() * proGain;
										newBalance = b.getBetUser().getUsBalance() + plusBalance;
										businessLogic.updateUserBalance(b.getBetUser(), newBalance);
									}

							}
						}
					} else
						jLabelError.setText("Cierre todos los pronosticos.");

				}
			jLabelError.setText("Evento cerrado!");
		}
	}

	private boolean allPronosClosed(ArrayList<Pronostic> pronostics) {
		for (Pronostic p : pronostics) {
			if (!p.isProClosed())
				return false; // Si hay alguno que no se ha cerrado -> false
		}
		return true; // Si estan todos cerrados -> true
	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
