package gui;

import java.awt.Font;
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
import domain.Pronostic;

public class ViewProfileGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	private DefaultComboBoxModel listaCombo = new DefaultComboBoxModel();
	@SuppressWarnings("rawtypes")
	private DefaultComboBoxModel listaList = new DefaultComboBoxModel();
	private List<Bet> listBets;
	private ArrayList<Bet> listApuestas;
	private BLFacade businessLogic = LoginGUI.getBusinessLogic();

	@SuppressWarnings("unchecked")
	public ViewProfileGUI() {
		try {
			init();
			listBets = businessLogic.getU().getBets();
			for (Bet b : listBets) {
				listaCombo.addElement(b.getBetPronostic().getProDescription());
			}
			listApuestas = LoginGUI.getBusinessLogic().getU().getBets();
			for (Bet bet : listApuestas) {
				listaList.addElement(bet.getBetPronostic().getProQuestion().getQuesEvent().getEvDescription());
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
		setBounds(100, 100, 560, 449);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setTitle("Ver perfil");

		JLabel lblNewLabel = new JLabel("Perfil de usuario");
		lblNewLabel.setEnabled(false);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(37, 11, 501, 14);
		contentPane.add(lblNewLabel);

		@SuppressWarnings("rawtypes")
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(207, 50, 172, 22);
		contentPane.add(comboBox);
		comboBox.setModel(listaCombo);

		JLabel lblNewLabel_1 = new JLabel("Seleccione una apuesta:");
		lblNewLabel_1.setBounds(40, 54, 172, 14);
		contentPane.add(lblNewLabel_1);

		JLabel jLabelEvent = new JLabel("Evento:");
		jLabelEvent.setBounds(174, 109, 332, 14);
		contentPane.add(jLabelEvent);

		JLabel totalBets = new JLabel(" ");
		totalBets.setBounds(323, 109, 48, 14);
		contentPane.add(totalBets);

		JLabel jLabelQuestion = new JLabel("Pregunta:");
		jLabelQuestion.setBounds(174, 134, 332, 14);
		contentPane.add(jLabelQuestion);

		JLabel totalUsers = new JLabel(" ");
		totalUsers.setBounds(323, 134, 48, 14);
		contentPane.add(totalUsers);

		JList<Object> list = new JList<Object>();
		list.setBounds(40, 108, 112, 124);
		contentPane.add(list);

		list.setModel(listaList);

		JLabel betMax = new JLabel(" ");
		betMax.setBounds(323, 159, 48, 14);
		contentPane.add(betMax);

		JLabel betMin = new JLabel(" ");
		betMin.setBounds(323, 184, 48, 14);
		contentPane.add(betMin);
		/*
		 * listSports = LoginGUI.getBusinessLogic().getAllSports(); for (Sport sp :
		 * listSports) { listaCombo.addElement(sp.getSpDescription()); }
		 */

		JButton btnNewButton = new JButton("Mostrar");
		btnNewButton.setBounds(417, 50, 89, 23);
		contentPane.add(btnNewButton);

		JButton jButtonClose = new JButton("Cerrar");
		jButtonClose.setBounds(417, 363, 89, 23);
		contentPane.add(jButtonClose);

		JLabel jLabelPronos = new JLabel("Pronostico:");
		jLabelPronos.setBounds(174, 159, 332, 14);
		contentPane.add(jLabelPronos);

		JLabel jLabelAmountBets = new JLabel("Cantidad total apostada:");
		jLabelAmountBets.setBounds(174, 218, 332, 14);
		contentPane.add(jLabelAmountBets);

		JLabel jLabelCantBets = new JLabel("Cantidad total de apuestas:");
		jLabelCantBets.setBounds(174, 243, 332, 14);
		contentPane.add(jLabelCantBets);

		JLabel jLabelBalance = new JLabel("Saldo actual:");
		jLabelBalance.setBounds(174, 281, 332, 14);
		contentPane.add(jLabelBalance);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Pronostic p = businessLogic.findPronostic((String) listaCombo.getSelectedItem());
				jLabelEvent.setText("Evento:          " + p.getProQuestion().getQuesEvent().getEvDescription());
				jLabelQuestion.setText("Pregunta:          " + p.getProQuestion().getQuesDescription());

				List<Bet> bets = businessLogic.getU().getBets();
				float cantBet = 0;
				for (Bet b : bets) {
					cantBet = cantBet + b.getBetCant();
				}
				jLabelPronos.setText("Pronostico:          " + p.getProDescription());
				jLabelAmountBets.setText("Cantidad total apostada:         " + cantBet + "�");
				jLabelCantBets.setText("Cantidad total de apuestas:          " + businessLogic.getU().getBets().size());
				jLabelBalance.setText("Saldo actual:          " + businessLogic.getU().getUsBalance() + "�");

			}
		});

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