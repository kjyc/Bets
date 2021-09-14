package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

public class ChangeBalanceGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private BLFacade businessLogic = LoginGUI.getBusinessLogic();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangeBalanceGUI frame = new ChangeBalanceGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChangeBalanceGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel jLabelUserTitle = new JLabel("Nombre de usuario: " + businessLogic.getU().getUsName());
		jLabelUserTitle.setBounds(89, 32, 117, 14);
		contentPane.add(jLabelUserTitle);

		JLabel jLabelBalanceTitleOld = new JLabel("Saldo antiguo:");
		jLabelBalanceTitleOld.setBounds(89, 69, 93, 14);
		contentPane.add(jLabelBalanceTitleOld);

		JLabel jLabelBalance = new JLabel(businessLogic.getU().getUsBalance() + " \u20AC");
		jLabelBalance.setBounds(217, 69, 104, 14);
		contentPane.add(jLabelBalance);

		JLabel JLabelName = new JLabel("");
		JLabelName.setBounds(216, 32, 105, 14);
		contentPane.add(JLabelName);

		JLabel jLabelBalanceTitleNew = new JLabel("Saldo nuevo:");
		jLabelBalanceTitleNew.setBounds(89, 136, 93, 14);
		contentPane.add(jLabelBalanceTitleNew);

		JButton btnNewButton = new JButton("Agregar saldo");
		btnNewButton.setBounds(142, 203, 139, 23);
		contentPane.add(btnNewButton);

		JLabel jLabelError = new JLabel("");
		jLabelError.setForeground(Color.RED);
		jLabelError.setBounds(104, 178, 218, 14);
		contentPane.add(jLabelError);

		JSpinner jSpinnerBalance = new JSpinner();
		jSpinnerBalance.setBounds(217, 133, 54, 20);
		contentPane.add(jSpinnerBalance);

		JButton jButtonClose = new JButton("Cerrar");
		jButtonClose.setBounds(323, 216, 89, 23);
		contentPane.add(jButtonClose);

		// Listeners

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int intt = (int) jSpinnerBalance.getValue();

				if (intt < 0 || intt == 0) {
					jLabelError.setText("Inserte un saldo mayor a 0.");
				} else {
					float actualBalance = businessLogic.getU().getUsBalance();
					float newBalance = intt + actualBalance;
					businessLogic.updateUserBalance(businessLogic.getU(), newBalance);
					jLabelBalance.setText(businessLogic.getU().getUsBalance() + " \u20AC");
					jLabelError.setText("Saldo agredado correctamente");
				}
			}
		});

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		// Fin listeners
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
