package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.User;

public class LoginGUI extends JFrame {

	private static LoginGUI frame = new LoginGUI();

	private static final long serialVersionUID = 1L; // HPM
	private JPanel contentPane;
	private JTextField jTextName;
	private JPasswordField jPassword;
	private static BLFacade businessLogic;

	public static BLFacade getBusinessLogic() {
		return businessLogic;
	}

	public void setBusinessLogic(BLFacade bl) {
		businessLogic = bl;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
	public LoginGUI() {
		setTitle("Inicio de sesi\u00F3n");

		// Componentes

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton jButtonLogin = new JButton("Iniciar sesi\u00F3n");
		jButtonLogin.setBounds(149, 166, 127, 23);
		contentPane.add(jButtonLogin);

		JLabel jLabelName = new JLabel("Nombre:");
		jLabelName.setBounds(93, 62, 75, 14);
		contentPane.add(jLabelName);

		JLabel jLabelPassword = new JLabel("Contrase\u00F1a:");
		jLabelPassword.setBounds(93, 104, 75, 14);
		contentPane.add(jLabelPassword);

		jTextName = new JTextField();
		jTextName.setBounds(190, 59, 148, 20);
		contentPane.add(jTextName);
		jTextName.setColumns(10);

		JButton jButtonResgister = new JButton("Registrarme");
		jButtonResgister.setBounds(93, 200, 112, 23);
		contentPane.add(jButtonResgister);

		// inicio ver preguntar
		JButton jButtonViewQuestions = new JButton("Ver preguntas");
		jButtonViewQuestions.setBounds(217, 200, 121, 23);
		contentPane.add(jButtonViewQuestions);
		// fin ver preguntas

		JLabel jLabelError = new JLabel("");
		jLabelError.setForeground(Color.RED);
		jLabelError.setBounds(138, 141, 148, 14);
		contentPane.add(jLabelError);

		JLabel jLabelTitle = new JLabel("Apuestas Deportivas");
		jLabelTitle.setBounds(165, 22, 121, 14);
		contentPane.add(jLabelTitle);

		jPassword = new JPasswordField();
		jPassword.setBounds(190, 101, 148, 20);
		contentPane.add(jPassword);

		// Fin componentes

		// Listeners

		jButtonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String userName = jTextName.getText();

				// String userName = "user";
				String userPass = new String(jPassword.getPassword());

				boolean b = businessLogic.login(userName, userPass);
				// Devuelve true si existe el usuario
				if (b) {
					jLabelError.setText("OK");
					User u = businessLogic.findUser(userName);
					businessLogic.setU(u);
					if (u.isUsAdmin()) {
						AdminMainGUI a = new AdminMainGUI();
						frame.setVisible(false);
						// a.setBussinessLogic(getBusinessLogic());
						a.setVisible(true);
					} else {
						UserMainGUI w = new UserMainGUI();
						frame.setVisible(false);
						w.setBussinessLogic(getBusinessLogic());
						w.setVisible(true);
					}
				} else {
					jLabelError.setText("Error al inciar sesion");
				}
			}

		});

		jButtonResgister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterUserGUI ru = new RegisterUserGUI();
				ru.setVisible(true);
			}
		});

		jButtonViewQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FindQuestionsGUI fq = new FindQuestionsGUI();
				fq.setVisible(true);
			}
		});

		// Fin listeners

	}
}
