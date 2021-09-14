package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import businessLogic.BLFacade;
import java.awt.Font;

public class UserMainGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private BLFacade bussinessLogic = LoginGUI.getBusinessLogic();
	
	public BLFacade getBussinessLogic() {
		return bussinessLogic;
	}

	public void setBussinessLogic(BLFacade bussinessLogic) {
		this.bussinessLogic = bussinessLogic;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserMainGUI frame = new UserMainGUI();
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
	public UserMainGUI() {
		setTitle("Menu de usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// inicio ver preguntas
		JButton jButtonViewQuestions = new JButton("Ver preguntas");
		jButtonViewQuestions.setBounds(57, 96, 126, 23);
		contentPane.add(jButtonViewQuestions);
		// fin ver preguntas

		// inicio aportar
		JButton jButtonBet = new JButton("Apostar");
		jButtonBet.setBounds(239, 96, 126, 23);
		contentPane.add(jButtonBet);
		// fin aportar

		// inicio add saldo
		JButton jButtonBalance = new JButton("Agregar saldo");
		jButtonBalance.setBounds(239, 130, 126, 23);
		contentPane.add(jButtonBalance);
		// fin add saldo

		JLabel jLabelTitle = new JLabel("Escoja una opci\u00F3n:");
		jLabelTitle.setBounds(57, 71, 371, 14);
		contentPane.add(jLabelTitle);

		JButton jButtonClose = new JButton("Cerrar");
		jButtonClose.setBounds(339, 231, 89, 23);
		contentPane.add(jButtonClose);

		JButton btnNewButton = new JButton("Ver Estadisticas");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StatGUI stat = new StatGUI();
				stat.setVisible(true);
			}
		});
		btnNewButton.setBounds(57, 130, 126, 23);
		contentPane.add(btnNewButton);

		JButton jButtonViewProfile = new JButton("Ver perfil");

		jButtonViewProfile.setBounds(57, 164, 126, 23);
		contentPane.add(jButtonViewProfile);
		
		JLabel lblNewLabel = new JLabel("Modo Usuario");
		lblNewLabel.setEnabled(false);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(57, 24, 371, 14);
		contentPane.add(lblNewLabel);
		
		JLabel welcomeLabel = new JLabel("");
		welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		welcomeLabel.setBounds(67, 46, 361, 14);
		contentPane.add(welcomeLabel);
		welcomeLabel.setText("Bienvenido "+ bussinessLogic.getU().getUsName());
		// Listeners

		jButtonViewProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewProfileGUI vp = new ViewProfileGUI();
				vp.setVisible(true);
			}
		});

		jButtonViewQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FindQuestionsGUI fQu = new FindQuestionsGUI();
				fQu.setVisible(true);
			}
		});

		//Hacer la GUI de crear apuestas
		jButtonBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateBetGUI cb = new CreateBetGUI();
				cb.setVisible(true);
			}
		});

		jButtonBalance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChangeBalanceGUI cb = new ChangeBalanceGUI();
				cb.setVisible(true);
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
