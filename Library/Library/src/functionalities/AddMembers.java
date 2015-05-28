package functionalities;

//import the packages for using the classes in them into the program

import items.Members;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
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

/**
 * A public class
 */
public class AddMembers extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/***************************************************************************
	 *** declaration of the private variables used in the program ***
	 ***************************************************************************/

	// for creating the North Panel
	private JPanel northPanel = new JPanel();
	// for creating the North Label
	private JLabel northLabel = new JLabel("MEMBER INFORMATION");

	// for creating the Centre Panel
	private JPanel centerPanel = new JPanel();
	// for creating an Internal Panel in the centre panel
	private JPanel informationLabelPanel = new JPanel();
	// for creating an array of JLabel
	private JLabel[] informationLabel = new JLabel[7];
	// for creating an array of String
	private String[] informaionString = { " Member ID: ", " Address: ",
			"First name: ", "Last name: ", "Phone number: ", "Email address: ",
			"Other details: " };
	// for creating an Internal Panel in the centre panel
	private JPanel informationTextFieldPanel = new JPanel();
	// for creating an array of JTextField
	private JTextField[] informationTextField = new JTextField[7];
	// for creating an array of JPasswordField

	// for creating an Internal Panel in the centre panel
	private JPanel insertInformationButtonPanel = new JPanel();
	// for creating a button
	private JButton insertInformationButton = new JButton(
			"Insert the Information");

	// for creating the South Panel
	private JPanel southPanel = new JPanel();
	// for creating a button
	private JButton OKButton = new JButton("Exit");

	// create objects from another classes for using them in the ActionListener
	private Members member;
	// for creating an array of string to store the data
	private String[] data;

	// for checking the password
	// public boolean isPasswordCorrect() {
	// if (informationPasswordField[0].getText().equals(
	// informationPasswordField[1].getText()))
	// data[1] = informationPasswordField[1].getText();
	// else if (!informationPasswordField[0].getText().equals(
	// informationPasswordField[1].getText()))
	// return false;
	//
	// return true;
	// }

	// for checking the information from the text field
	public boolean isCorrect() {
		data = new String[7];
		for (int i = 0; i < informationLabel.length; i++) {
			if (!informationTextField[i].getText().equals("")) {
				data[i] = informationTextField[i].getText();
			} else {
				return false;
			}
		}
		return true;
	}

	// for setting the array of JTextField & JPasswordField to null
	public void clearTextField() {
		for (int i = 0; i < informationLabel.length; i++) {
			informationTextField[i].setText(null);
		}
	}

	// constructor of addMembers
	public AddMembers() {
		// for setting the title for the internal frame
		super("Add Members", false, true, false, true);
		// for setting the icon
		setFrameIcon(new ImageIcon(
				ClassLoader.getSystemResource("images/Add16.gif")));
		// for getting the graphical user interface components display area
		Container cp = getContentPane();

		// for setting the layout
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		// for setting the font
		northLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		// for adding the label to the panel
		northPanel.add(northLabel);
		// for adding the panel to the container
		cp.add("North", northPanel);

		// for setting the layout
		centerPanel.setLayout(new BorderLayout());
		// for setting the border to the panel
		centerPanel.setBorder(BorderFactory
				.createTitledBorder("Add a new member:"));
		// for setting the layout
		informationLabelPanel.setLayout(new GridLayout(7, 1, 1, 1));
		// for setting the layout
		informationTextFieldPanel.setLayout(new GridLayout(7, 1, 1, 1));
		/***********************************************************************
		 * for adding the strings to the labels, for setting the font * and
		 * adding these labels to the panel. * finally adding the panel to the
		 * container *
		 ***********************************************************************/
		for (int i = 0; i < informationLabel.length; i++) {
			informationLabelPanel.add(informationLabel[i] = new JLabel(
					informaionString[i]));
			informationLabel[i].setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		// for adding the panel to the centerPanel
		centerPanel.add("West", informationLabelPanel);

		/***********************************************************************
		 * for adding the JTextField and JPasswordField to the panel and *
		 * setting the font to the JTextField and JPasswordField. Finally *
		 * adding the panel to the centerPanel *
		 ***********************************************************************/
		for (int i = 0; i < informationLabel.length; i++) {
			informationTextFieldPanel
					.add(informationTextField[i] = new JTextField(25));
			informationTextField[i].setFont(new Font("Tahoma", Font.PLAIN, 11));
		}
		centerPanel.add("East", informationTextFieldPanel);

		/***********************************************************************
		 * for setting the layout for the panel,setting the font for the button*
		 * and adding the button to the panel. * finally adding the panel to the
		 * container *
		 ***********************************************************************/
		insertInformationButtonPanel
				.setLayout(new FlowLayout(FlowLayout.RIGHT));
		insertInformationButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		insertInformationButtonPanel.add(insertInformationButton);
		centerPanel.add("South", insertInformationButtonPanel);
		cp.add("Center", centerPanel);

		/***********************************************************************
		 * for setting the layout for the panel,setting the font for the button*
		 * adding the button to the panel & setting the border. * finally adding
		 * the panel to the container *
		 ***********************************************************************/
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		OKButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		southPanel.add(OKButton);
		southPanel.setBorder(BorderFactory.createEtchedBorder());
		cp.add("South", southPanel);

		/***********************************************************************
		 * for adding the action listener to the button,first the text will be *
		 * taken from the JTextField[] and make the connection for database, *
		 * after that update the table in the database with the new value *
		 ***********************************************************************/
		insertInformationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// for checking if there is a missing information
				if (!isCorrect()) {
					JOptionPane.showMessageDialog(null,
							"Please complete all the fields!", "Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				Thread runner = new Thread() {
					public void run() {
						member = new Members();
						try {
							member.select("select member_id, my_members from all_members where member_id = "
									+ data[0]);
							int ID = member.getMemberID();
							if (Integer.valueOf(data[0]) == ID) {
								JOptionPane.showMessageDialog(null,
										"This member already exists!", "Error",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
							try {
								String sql = "insert into all_members (member_id, my_members) values "
										+ " (?,members(member(?,?,?,?,?,?)))";
								PreparedStatement ps = MyConnection
										.getConnection().prepareStatement(sql);
								ps.setInt(1, Integer.valueOf(data[0]));
								ps.setString(2, data[1]);
								ps.setString(3, data[2]);
								ps.setString(4, data[3]);
								ps.setString(5, data[4]);
								ps.setString(6, data[5]);
								ps.setString(7, data[6]);
								ps.executeUpdate();
								ps.close();
								clearTextField();
								JOptionPane
										.showMessageDialog(
												null,
												"The member has been successfully added! Thank you.",
												"Success",
												JOptionPane.INFORMATION_MESSAGE);
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null,
										e.toString(), "Error",
										JOptionPane.ERROR_MESSAGE);
							}
						}

						catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, e1.toString(),
									"Error", JOptionPane.ERROR_MESSAGE);
						}

					}
				};
				runner.start();
			}
			// if there is a missing data, then display Message Dialog
		});
		// for adding the action listener for the button to dispose the frame
		OKButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});
		// for setting the visible to true
		setVisible(true);
		// show the internal frame
		pack();
	}
}