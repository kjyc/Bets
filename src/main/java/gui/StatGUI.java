package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Bet;
import domain.Event;
import domain.Sport;
import domain.User;

public class StatGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	private DefaultComboBoxModel listaCombo = new DefaultComboBoxModel();
	@SuppressWarnings("rawtypes")
	private DefaultComboBoxModel listaList = new DefaultComboBoxModel();
	private List<Sport> listSports;
	private ArrayList<Bet> listApuestas;
	private BLFacade businessLogic = LoginGUI.getBusinessLogic();

	@SuppressWarnings("unchecked")
	public StatGUI() {
		try {
			init();
			listSports = LoginGUI.getBusinessLogic().getAllSports();
			for (Sport sp : listSports) {
				listaCombo.addElement(sp.getSpDescription());
			}
			listApuestas = LoginGUI.getBusinessLogic().getU().getBets();
			for (Bet bet : listApuestas) {
				listaList.addElement(bet.getBetPronostic().getProDescription());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("unchecked")
	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setTitle("Ver estadisticas");

		JLabel lblNewLabel = new JLabel("Estadisticas de apuestas");
		lblNewLabel.setBounds(165, 11, 162, 14);
		contentPane.add(lblNewLabel);

		@SuppressWarnings("rawtypes")
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(174, 50, 135, 22);
		contentPane.add(comboBox);
		comboBox.setModel(listaCombo);

		JLabel lblNewLabel_1 = new JLabel("Seleccione Deporte");
		lblNewLabel_1.setBounds(40, 54, 112, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Apuestas totales:");
		lblNewLabel_2.setBounds(174, 109, 112, 14);
		contentPane.add(lblNewLabel_2);

		JLabel totalBets = new JLabel(" ");
		totalBets.setBounds(323, 109, 48, 14);
		contentPane.add(totalBets);

		JLabel lblNewLabel_3 = new JLabel("Total de Usuarios:");
		lblNewLabel_3.setBounds(174, 134, 126, 14);
		contentPane.add(lblNewLabel_3);

		JLabel totalUsers = new JLabel(" ");
		totalUsers.setBounds(323, 134, 48, 14);
		contentPane.add(totalUsers);

		JList<Object> jlist = new JList<Object>();
		jlist.setBounds(40, 108, 112, 124);
		contentPane.add(jlist);

		JLabel betMax = new JLabel(" ");
		betMax.setBounds(323, 159, 48, 14);
		contentPane.add(betMax);

		JLabel betMin = new JLabel(" ");
		betMin.setBounds(323, 184, 48, 14);
		contentPane.add(betMin);

		jlist.setModel(listaList);

		JButton btnNewButton = new JButton("Mostrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				totalBets.setText("");
				totalUsers.setText("");
				betMax.setText("€");
				betMin.setText("€");
				Sport sp = businessLogic.findSport((String) listaCombo.getSelectedItem());

				List<Event> listEvents = businessLogic.searchEvents(sp);
				ArrayList<User> listUsers;
				ArrayList<Bet> listBets;

				int amountBets = 0;
				int amountUsers = 0;
				int maxBet = 0;
				int minBet = Integer.MAX_VALUE;
				for (Event e : listEvents) {
					amountBets = amountBets + e.getAmountBets();
					if (e.getGamblers() == null) {
						amountUsers = amountUsers + 0;
					} else {
						amountUsers = amountUsers + e.getGamblers().size();
					}
					listUsers = e.getGamblers();

					if (listUsers != null) {
						for (User us : listUsers) {
							listBets = us.getBets();
							for (Bet b : listBets) {
								if (maxBet < b.getBetCant()) {
									maxBet = b.getBetCant();
								}
								if (minBet > b.getBetCant()) {
									minBet = b.getBetCant();
								}
							}
						}
					}
				}

				totalBets.setText(amountBets + "");
				totalUsers.setText(amountUsers + "");
				betMax.setText(maxBet + "€");
				if (minBet == Integer.MAX_VALUE) {
					betMin.setText(0 + "€");
				} else {
					betMin.setText(minBet + "€");
				}
			}
		});

		btnNewButton.setBounds(334, 50, 89, 23);
		contentPane.add(btnNewButton);

		JButton jButtonClose = new JButton("Cerrar");
		jButtonClose.setBounds(334, 231, 89, 23);
		contentPane.add(jButtonClose);

		JLabel lblNewLabel_4 = new JLabel("Apuesta Max:");
		lblNewLabel_4.setBounds(174, 159, 98, 14);
		contentPane.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Apuesta Min:");
		lblNewLabel_5.setBounds(174, 184, 98, 14);
		contentPane.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("Se ha apostado por:");
		lblNewLabel_6.setBounds(40, 90, 118, 14);
		contentPane.add(lblNewLabel_6);

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});
	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}