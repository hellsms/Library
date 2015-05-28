package functionalities;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logics.MyConnection;

public class LoadCsv extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel northPanel = new JPanel();
	private JLabel title = new JLabel("LOANS INFORMATION");

	private JPanel centerPanel = new JPanel();
	private JPanel informationPanel = new JPanel();
	private JLabel[] informationLabel = new JLabel[2];
	private String[] informationString = { "Folder Path:", "File name:" };
	private JTextField[] informationTextField = new JTextField[2];
	private String[] data;
	private JPanel returnButtonPanel = new JPanel();
	private JButton returnButton = new JButton("Import");

	private JPanel southPanel = new JPanel();
	private JButton cancelButton = new JButton("Cancel");

	/*private Books book;
	private Members member;
	private Loans loan;*/

	public boolean isCorrect() {
		data = new String[2];
		for (int i = 0; i < informationLabel.length; i++) {
			if (!informationTextField[i].getText().equals("")) {
				data[i] = informationTextField[i].getText();
			} else {
				return false;
			}
		}
		return true;
	}

	public void clearTextField() {
		for (int i = 0; i < informationTextField.length; i++) {
			informationTextField[i].setText(null);
		}
	}

	public LoadCsv() {
		super("Import a csv", false, true, false, true);
		setFrameIcon(new ImageIcon(
				ClassLoader.getSystemResource("images/csv.jpg")));
		Container cp = getContentPane();

		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		title.setFont(new Font("Tahoma", Font.BOLD, 14));
		northPanel.add(title);
		cp.add("North", northPanel);

		centerPanel.setLayout(new BorderLayout());
		informationPanel.setLayout(new GridLayout(4, 2, 1, 1));

		for (int i = 0; i < informationLabel.length; i++) {
			informationPanel.add(informationLabel[i] = new JLabel(
					informationString[i]));
			informationLabel[i].setFont(new Font("Tahoma", Font.BOLD, 11));
			informationPanel.add(informationTextField[i] = new JTextField());
			informationTextField[i].setFont(new Font("Tahoma", Font.PLAIN, 11));
		}
		centerPanel.add("Center", informationPanel);
		returnButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		returnButtonPanel.add(returnButton);
		returnButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		centerPanel.add("South", returnButtonPanel);
		centerPanel.setBorder(BorderFactory
				.createTitledBorder("Provide paths:"));
		cp.add("Center", centerPanel);

		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		southPanel.add(cancelButton);
		cancelButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		southPanel.setBorder(BorderFactory.createEtchedBorder());
		cp.add("South", southPanel);

		returnButton.addActionListener(this);
		cancelButton.addActionListener(this);
		setResizable(true);
		setVisible(true);
		pack();
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == returnButton) {
			if (!isCorrect()) {
				JOptionPane.showMessageDialog(null,
						"Please complete all the fields!", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			Thread runner = new Thread() {
				public void run() {
				
					try {
						String loadCall = "{call library_management.read_loan_csv(?,?)}";
						CallableStatement call = MyConnection.getConnection().prepareCall(loadCall);
						call.setString(1, data[0]);
						call.setString(2, data[1]);
						call.execute();
						call.close();
						clearTextField();
						JOptionPane.showMessageDialog(null,
								"The file has been loaded!", "Success",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null,
								e.toString(), "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
					
			};
			runner.start();
		} 
		if (ae.getSource() == cancelButton) {
			dispose();
		}
	}

}
