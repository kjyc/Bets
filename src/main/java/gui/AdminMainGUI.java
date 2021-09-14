package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Event;
import java.awt.Font;

public class AdminMainGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	// private BLFacade bussinessLogic = LoginGUI.getBusinessLogic();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMainGUI frame = new AdminMainGUI();
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
	public AdminMainGUI() {
		setTitle("Menu de administradores");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 443, 382);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		///////////////// Botones////////////////////

		// inicio Registro usuarios
		JButton jButtonRegisterAdmin = new JButton("Registro de Admins");
		jButtonRegisterAdmin.setFont(new Font("Tahoma", Font.PLAIN, 10));
		jButtonRegisterAdmin.setBounds(59, 83, 124, 23);
		contentPane.add(jButtonRegisterAdmin);
		// fin Registro usuarios

		// inicio lista de usuarios
		JButton jButtonListUsers = new JButton("Lista Usuarios");
		jButtonListUsers.setBounds(59, 131, 124, 23);
		contentPane.add(jButtonListUsers);
		// fin lista usuarios

		// inicio crear pregunta
		JButton jButtonCreateQuestion = new JButton("Crear Pregunta");
		jButtonCreateQuestion.setBounds(59, 183, 124, 23);
		contentPane.add(jButtonCreateQuestion);
		// fin crear pregunta

		// inicio crear evento
		JButton jButtonCreateEvent = new JButton("Crear Evento");
		jButtonCreateEvent.setBounds(240, 83, 124, 23);
		contentPane.add(jButtonCreateEvent);
		// fin crear evento

		// inicio a�adir pronostico
		JButton jButtonCreatePronostic = new JButton("A\u00F1adir Pronostico");
		jButtonCreatePronostic.setFont(new Font("Tahoma", Font.PLAIN, 10));
		jButtonCreatePronostic.setBounds(240, 131, 124, 23);
		contentPane.add(jButtonCreatePronostic);
		// fin a�adir pronostico

		// inicio Cerrar Evento
		JButton jButtonCloseEvent = new JButton("Cerrar Evento");
		jButtonCloseEvent.setBounds(240, 183, 124, 23);
		contentPane.add(jButtonCloseEvent);
		// fin Cerrar Evento
		
		JButton jButtonClose = new JButton("Cerrar");
		jButtonClose.setBounds(332, 293, 89, 23);
		contentPane.add(jButtonClose);

		JLabel jLabelChooseAnOption = new JLabel("Escoja una opci\u00F3n:");
		jLabelChooseAnOption.setBounds(59, 58, 177, 14);
		contentPane.add(jLabelChooseAnOption);
		
		//inicio crear deporte
		JButton btnNewButton = new JButton("Crear Deporte");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateSportGUI ads = new CreateSportGUI();
				ads.setVisible(true);
			}
		});
		btnNewButton.setBounds(61, 230, 122, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Editar Evento");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UpdateEventGUI upE = new UpdateEventGUI(new Vector<Event>());
				upE.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(240, 230, 124, 23);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("Modo Administrador");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setEnabled(false);
		lblNewLabel.setBounds(59, 25, 399, 14);
		contentPane.add(lblNewLabel);
		//fin crear deporte

		// Listeners

		jButtonRegisterAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterAdminGUI ra = new RegisterAdminGUI();
				ra.setVisible(true);
			}
		});

		jButtonCreateEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateEventGUI ce = new CreateEventGUI();
				// CalendarioIndex_a_ ce = new CalendarioIndex_a_();
				ce.setVisible(true);
			}
		});

		jButtonListUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewUserListGUI vul = new ViewUserListGUI();
				vul.setVisible(true);
			}
		});

		jButtonCreatePronostic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreatePronosticGUI ap = new CreatePronosticGUI();
				ap.setVisible(true);
			}
		});

		jButtonCreateQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateQuestionGUI qu = new CreateQuestionGUI(new Vector<Event>());
				qu.setVisible(true);
			}
		});

		jButtonCloseEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CloseEventGUI ce = new CloseEventGUI();
				ce.setVisible(true);
			}
		});

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		// Fin Listeners
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
