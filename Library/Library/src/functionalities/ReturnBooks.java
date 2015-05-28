package functionalities;

//import the packages for using the classes in them into the program

import items.Books;
import items.Loans;
import items.Members;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A public class
 */
public class ReturnBooks extends JInternalFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/***************************************************************************
	 *** declaration of the private variables used in the program ***
	 ***************************************************************************/

	// for creating the North Panel
	private JPanel northPanel = new JPanel();
	// for creating the label
	private JLabel title = new JLabel("BOOK INFORMATION");

	// for creating the centre Panel
	private JPanel centerPanel = new JPanel();
	// for creating an Internal Panel in the centre panel
	private JPanel informationPanel = new JPanel();
	// for creating an array of JLabel
	private JLabel[] informationLabel = new JLabel[3];
	// for creating an array of String
	private String[] informationString = { "Book ID:", "Member ID:",
			"Loan ID:" };
	// for creating an array of JTextField
	private JTextField[] informationTextField = new JTextField[3];
	// for creating an array of string to store the data
	private String[] data;
	// for creating an Internal Panel in the centre panel
	private JPanel returnButtonPanel = new JPanel();
	// for creating the button
	private JButton returnButton = new JButton("Return");

	// for creating the panel
	private JPanel southPanel = new JPanel();
	// for creating the button
	private JButton cancelButton = new JButton("Cancel");

	// for creating an object
	private Books book;
	private Members member;
	private Loans loan;

	// for checking the information from the text field
	public boolean isCorrect() {
		data = new String[3];
		for (int i = 0; i < informationLabel.length; i++) {
			if (!informationTextField[i].getText().equals("")) {
				data[i] = informationTextField[i].getText();
			} else {
				return false;
			}
		}
		return true;
	}

	// for setting the array of JTextField to null
	public void clearTextField() {
		for (int i = 0; i < informationTextField.length; i++) {
			informationTextField[i].setText(null);
		}
	}

	// constructor of returnBooks
	public ReturnBooks() {
		// for setting the title for the internal frame
		super("Return books", false, true, false, true);
		// for setting the icon
		setFrameIcon(new ImageIcon(
				ClassLoader.getSystemResource("images/Import16.gif")));
		// for getting the graphical user interface components display area
		Container cp = getContentPane();

		// for setting the layout
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		// for setting the font
		title.setFont(new Font("Tahoma", Font.BOLD, 14));
		// for adding the label
		northPanel.add(title);
		// for adding the north panel to the container
		cp.add("North", northPanel);

		// for setting the layout
		centerPanel.setLayout(new BorderLayout());
		// for setting the layout for the internal panel
		informationPanel.setLayout(new GridLayout(5, 2, 1, 1));

		/***********************************************************************
		 * for adding the strings to the labels, for setting the font * and
		 * adding these labels to the panel. * finally adding the panel to the
		 * container *
		 ***********************************************************************/
		for (int i = 0; i < informationLabel.length; i++) {
			informationPanel.add(informationLabel[i] = new JLabel(
					informationString[i]));
			informationLabel[i].setFont(new Font("Tahoma", Font.BOLD, 11));
			informationPanel.add(informationTextField[i] = new JTextField());
			informationTextField[i].setFont(new Font("Tahoma", Font.PLAIN, 11));
		}
		centerPanel.add("Center", informationPanel);
		// for setting the layout
		returnButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		// for adding the button
		returnButtonPanel.add(returnButton);
		// for setting the font to the button
		returnButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		// for adding the internal panel to the panel
		centerPanel.add("South", returnButtonPanel);
		// for setting the border
		centerPanel.setBorder(BorderFactory
				.createTitledBorder("Return a book:"));
		// for adding the centre panel to the container
		cp.add("Center", centerPanel);

		// for setting the layout
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		// for adding the button

		southPanel.add(cancelButton);
		// for setting the font to the button
		cancelButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		// for setting the border
		southPanel.setBorder(BorderFactory.createEtchedBorder());
		// for adding the south panel to the container
		cp.add("South", southPanel);

		/***********************************************************************
		 * for adding the action listener to the button,first the text will be *
		 * taken from the JTextField and make the connection for database, *
		 * after that update the table in the database with the new value *
		 ***********************************************************************/
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
					book = new Books();
					member = new Members();
					loan = new Loans();
					try {
						book.select("select book_id, quantity, available, loaned, my_books from all_books where book_id = "
								+ data[0]);
						member.select("select member_id, my_members from all_members where member_id = "
								+ data[1]);
						loan.select("select loan_id, member_id, book_id, request_date, return_date, other_details from all_loans where loan_id = "
								+ data[2]);
						if (book.getBookID() > 0 && member.getMemberID() > 0
								&& loan.getLoanID() > 0) {
							loan.execute("delete from all_loans where loan_id = "
									+ data[2]);
							JOptionPane
									.showMessageDialog(
											null,
											"The book has been successfully returned! Thank you.",
											"Success",
											JOptionPane.INFORMATION_MESSAGE);
							clearTextField();
						} else {
							JOptionPane.showMessageDialog(null,
									"Incorrect returning data.", "Warning",
									JOptionPane.WARNING_MESSAGE);
						}
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e.toString(),
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			};
			runner.start();
		} // if there is a missing data, then display Message Dialog
		if (ae.getSource() == cancelButton) {
			dispose();
		}
	}

}
