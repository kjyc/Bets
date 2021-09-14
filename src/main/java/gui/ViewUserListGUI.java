package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.User;

public class ViewUserListGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultComboBoxModel<String> listModel = new DefaultComboBoxModel<String>();
	private List<User> userList;

	private BLFacade businessLogic = LoginGUI.getBusinessLogic();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewUserListGUI frame = new ViewUserListGUI();
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
	public ViewUserListGUI() {
		setTitle("Ver la lista de usuarios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel jLabelList = new JLabel("Lista de usuarios");
		jLabelList.setBounds(80, 53, 143, 14);
		contentPane.add(jLabelList);

		JComboBox<String> jComboBoxUserList = new JComboBox<String>();
		jComboBoxUserList.setBounds(80, 78, 143, 20);
		contentPane.add(jComboBoxUserList);
		jComboBoxUserList.setModel(listModel);

		JButton jButtonDelete = new JButton("Borrar usuario");
		jButtonDelete.setBounds(250, 77, 118, 23);
		contentPane.add(jButtonDelete);

		JButton jButtonRefreshList = new JButton("Refrescar lista");
		jButtonRefreshList.setBounds(250, 111, 118, 23);
		contentPane.add(jButtonRefreshList);

		JLabel jLabelError = new JLabel("");
		jLabelError.setBounds(80, 177, 288, 14);
		contentPane.add(jLabelError);

		JButton jButtonClose = new JButton("Cerrar");
		jButtonClose.setBounds(339, 231, 89, 23);
		contentPane.add(jButtonClose);

		// Listeners

		jButtonRefreshList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				listModel.removeAllElements();
				userList = businessLogic.getAllUsers();
				for (User u : userList) {
					listModel.addElement(u.getUsName());
				}
				if (userList.isEmpty()) {
					jLabelError.setText("No hay ningun usuario registrado.");
				}

			}
		});

		jButtonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedUserName = (String) listModel.getSelectedItem();
				System.out.println(selectedUserName);
				businessLogic.deleteUser(selectedUserName);
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
