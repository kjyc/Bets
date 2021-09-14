package gui;

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

public class RegisterAdminGUI extends JFrame {

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
					RegisterAdminGUI frame = new RegisterAdminGUI();
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
	public RegisterAdminGUI() {
		setTitle("Registro de administradores");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel jLabelTitle = new JLabel("Registro de administradores");
		jLabelTitle.setBounds(129, 35, 140, 14);
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

		JButton jButtonRegister = new JButton("Registrar el admin");
		jButtonRegister.setBounds(129, 193, 156, 23);
		contentPane.add(jButtonRegister);

		JLabel jLabelError = new JLabel("");
		jLabelError.setBounds(95, 168, 223, 14);
		contentPane.add(jLabelError);

		JButton jButtonClose = new JButton("Cerrar");
		jButtonClose.setBounds(322, 227, 89, 23);
		contentPane.add(jButtonClose);

		jPassword = new JPasswordField();
		jPassword.setBounds(199, 126, 119, 20);
		contentPane.add(jPassword);

		// Listeners

		jButtonRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = jTextName.getText();
				String pass = new String(jPassword.getPassword());
				boolean isAd = true;
				boolean error = false;

				if (name.equals("") || pass.equals("")) {
					jLabelError.setText("Inserte un usuario y contraseña");
					error = true;
				}
				if (businessLogic.findUser(name) != null) {
					jLabelError.setText("Admin existente!");
					error = true;
				}
				if (!error) {
					businessLogic.storeUser(name, pass, isAd);
					jLabelError.setText("Admin creado!");
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
