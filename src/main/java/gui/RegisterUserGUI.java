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

public class RegisterUserGUI extends JFrame {

	private static final long serialVersionUID = 1L; // HPM
	private JPanel contentPane;
	private JTextField jTextName;
	private JPasswordField jPassword;

	private BLFacade businessLogic = LoginGUI.getBusinessLogic();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterUserGUI frame = new RegisterUserGUI();
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
	public RegisterUserGUI() {
		setTitle("Registro de usuarios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel jLabelTitle = new JLabel("\u00A1Registrate gratis!");
		jLabelTitle.setBounds(162, 35, 93, 14);
		contentPane.add(jLabelTitle);

		jTextName = new JTextField();
		jTextName.setBounds(199, 84, 119, 20);
		contentPane.add(jTextName);
		jTextName.setColumns(10);

		JLabel jLabelName = new JLabel("Nombre:");
		jLabelName.setBounds(95, 87, 46, 14);
		contentPane.add(jLabelName);

		JLabel jLabelPassword = new JLabel("Contrase\u00F1a:");
		jLabelPassword.setBounds(95, 129, 72, 14);
		contentPane.add(jLabelPassword);

		JButton jButtonRegister = new JButton("Registrarme");
		jButtonRegister.setBounds(162, 193, 107, 23);
		contentPane.add(jButtonRegister);

		JLabel jLabelError = new JLabel("");
		jLabelError.setForeground(Color.RED);
		jLabelError.setBounds(162, 168, 107, 14);
		contentPane.add(jLabelError);

		JButton jButtonClose = new JButton("Cerrar");
		jButtonClose.setBounds(313, 212, 89, 23);
		contentPane.add(jButtonClose);

		jPassword = new JPasswordField();
		jPassword.setBounds(199, 126, 119, 20);
		contentPane.add(jPassword);

		// Listeners

		jButtonRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = jTextName.getText();
				String pass = new String(jPassword.getPassword());
				boolean isAd = false;

				if (businessLogic.findUser(name) != null) {
					jLabelError.setText("Usuario existente!");
				} else {
					businessLogic.storeUser(name, pass, isAd);
					jLabelError.setText("Usuario creado!");
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
