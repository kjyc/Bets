package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Sport;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateSportGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField sportName;
	private BLFacade businessLogic = LoginGUI.getBusinessLogic();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateSportGUI frame = new CreateSportGUI();
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
	public CreateSportGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Agregar Deporte");
		lblNewLabel.setBounds(146, 29, 132, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre Deporte: ");
		lblNewLabel_1.setBounds(35, 88, 106, 14);
		contentPane.add(lblNewLabel_1);
		
		sportName = new JTextField();
		sportName.setBounds(152, 85, 146, 20);
		contentPane.add(sportName);
		sportName.setColumns(10);
		
		JLabel msgError = new JLabel(" ");
		msgError.setBounds(93, 121, 224, 14);
		contentPane.add(msgError);
		
		//inicio agregar deporte
		JButton addButton = new JButton("Agregar");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Sport sp = businessLogic.createSport(sportName.getText());
				
				if (sportName.getText().length() < 1) {
					msgError.setText("Debe indicar un nombre de Deporte");
				}else {
					if (sp != null) {
						msgError.setText("Deporte Creado!");
						sportName.setText("");
					}else {
						msgError.setText("Error: Deporte Existente");
					}
				}
			}
		});
		addButton.setBounds(137, 146, 89, 23);
		contentPane.add(addButton);
		//fin agregar deporte
		
		JButton jButtonClose = new JButton("Cerrar");
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		jButtonClose.setBounds(339, 231, 89, 23);
		contentPane.add(jButtonClose);
		

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
