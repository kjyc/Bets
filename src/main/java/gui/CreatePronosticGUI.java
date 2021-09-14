package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Event;
import domain.Pronostic;
import domain.Question;

public class CreatePronosticGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelCalendar = new JLabel();
	private final JLabel jLabelQuestion = new JLabel();
	private final JLabel jLabelEvent = new JLabel();
	private JLabel jLabelError = new JLabel("");
	private JLabel jLabelPronostic = new JLabel();
	private JLabel jLabelGainPronostic = new JLabel();

	private JButton jButtonAddPronostic = new JButton();
	private JButton jButtonClose = new JButton();

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane jScrollEvents = new JScrollPane();
	private JScrollPane jScrollQuestions = new JScrollPane();
	private JSpinner jSpinnerGain = new JSpinner();

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents = new JTable();
	private JTable tableQueries = new JTable();

	// logica de negocio
	private BLFacade businessLogic = LoginGUI.getBusinessLogic();

	private String[] columnNamesEvents = new String[] { "Evento#", "Evento" };
	private String[] columnNamesQueries = new String[] { "Pregunta#", "Pregunta" };

	private DefaultTableModel tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

	private DefaultTableModel tableModelQueries = new DefaultTableModel(null, columnNamesQueries);

	private JTextField jTextPronostic = new JTextField();

	private Event selectedEvent;
	private Question selectedQuestion;

	public CreatePronosticGUI() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.setTitle("A�adir pronosticos");
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.getContentPane().add(jLabelCalendar, null);
		this.getContentPane().add(jLabelQuestion);
		this.getContentPane().add(jLabelEvent);
		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jCalendar1, null);
		this.getContentPane().add(jScrollEvents, null);
		this.getContentPane().add(jScrollQuestions, null);
		this.getContentPane().add(jTextPronostic);
		this.getContentPane().add(jLabelError);
		this.getContentPane().add(jSpinnerGain);
		jButtonAddPronostic.setFont(new Font("Tahoma", Font.PLAIN, 10));
		this.getContentPane().add(jButtonAddPronostic);
		this.getContentPane().add(jLabelGainPronostic);
		this.getContentPane().add(jButtonAddPronostic);
		this.getContentPane().add(jLabelPronostic);

		jLabelCalendar.setText("Calendario");
		jLabelCalendar.setBounds(new Rectangle(40, 19, 225, 21));

		jLabelEvent.setText("Eventos");
		jLabelEvent.setBounds(295, 19, 259, 16);

		jLabelQuestion.setText("Preguntas");
		jLabelQuestion.setBounds(40, 247, 412, 14);

		jButtonClose.setText("Cerrar");
		jButtonClose.setBounds(new Rectangle(559, 414, 77, 25));

		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		datesWithEventsCurrentMonth = businessLogic.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);

		jScrollEvents.setBounds(new Rectangle(292, 50, 346, 150));
		jScrollEvents.setViewportView(tableEvents);

		jScrollQuestions.setBounds(new Rectangle(40, 272, 406, 116));
		jScrollQuestions.setViewportView(tableQueries);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);

		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);

		jTextPronostic.setBounds(new Rectangle(100, 211, 429, 20));
		jTextPronostic.setBounds(466, 272, 181, 20);

		jLabelError.setForeground(Color.RED);
		jLabelError.setBounds(75, 399, 303, 20);

		jSpinnerGain.setBounds(466, 328, 61, 20);

		jButtonAddPronostic.setText("A\u00F1adir pronostico");
		jButtonAddPronostic.setEnabled(true);
		jButtonAddPronostic.setBounds(466, 365, 150, 23);

		jLabelGainPronostic.setText("Ganancia del pronostico:");
		jLabelGainPronostic.setBounds(470, 303, 181, 14);
		jButtonAddPronostic.setBounds(466, 365, 117, 23);

		jLabelPronostic.setText("Pronostico:");
		jLabelPronostic.setBounds(466, 247, 181, 14);

		/// Listeners

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
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
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
					row.add(q); // q object added in order to obtain it with tableModelQueries.getValueAt(i,2)
					tableModelQueries.addRow(row);

				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
				tableQueries.getColumnModel().removeColumn(tableQueries.getColumnModel().getColumn(2)); // not
				// shown
				// in
				// JTable

			}
		});

		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableQueries.getSelectedRow();
				domain.Question q = (domain.Question) tableModelQueries.getValueAt(i, 2); // obtain ev object
				selectedQuestion = q;
			}
		});

		jButtonAddPronostic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String p = jTextPronostic.getText();
				int ganancia = (int) jSpinnerGain.getValue();
				boolean error = false;

				if (new Date().compareTo(selectedEvent.getEvDate()) > 0) {
					jLabelError.setText("Debe escoger un evento que no haya pasado");
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
				if ((selectedEvent != null) && (selectedQuestion != null)) {
					if (p.equals("")) {
						jLabelError.setText("Ingresar datos");
						error = true;
					}
					if (ganancia <= 0) {
						jLabelError.setText("Debe ingresar un valor de ganancia valido");
						error = true;
					}
				}

				if (!error) {
					Pronostic pronostico = businessLogic.createPronostic((int) jSpinnerGain.getValue(), p,
							selectedQuestion);
					jLabelError.setText("Pronostico anadido");
					System.out.println("::: Pronostico a�adido --> " + pronostico.getProDescription() + " :::");
					businessLogic.updateQuestion(selectedQuestion, pronostico);
					jTextPronostic.setText("");
					jSpinnerGain.setValue(0);
				}

			}
		});

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});
		/// Fin listeners
	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
