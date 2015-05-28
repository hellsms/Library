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
public class BorrowBooks extends JInternalFrame {
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
	private JLabel[] informationLabel = new JLabel[5];
	// for creating an array of String
	private String[] informationString = { "Member ID: ", "Book ID: ",
			"Request date: ", "Return date: ", "Other details: " };
	// for creating an array of JTextField
	private JTextField[] informationTextField = new JTextField[5];
	// for creating an array of string to store the data
	private String[] data;

	// for creating an Internal Panel in the centre panel
	private JPanel borrowButtonPanel = new JPanel();
	// for creating the button
	private JButton borrowButton = new JButton("Borrow");

	// for creating South Panel
	private JPanel southPanel = new JPanel();
	// for creating the button
	private JButton cancelButton = new JButton("Cancel");

	// for creating an object
	private Books book;
	private Members member;
	private Loans borrow;

	// for checking the information from the text field
	public boolean isCorrect() {
		data = new String[5];
		for (int i = 0; i < informationLabel.length; i++) {
			if (!informationTextField[i].getText().equals(""))
				data[i] = informationTextField[i].getText();
			else
				return false;
		}
		return true;
	}

	// for setting the array of JTextField to null
	public void clearTextField() {
		for (int i = 0; i < informationTextField.length; i++)
			informationTextField[i].setText(null);
	}

	// constructor of borrowBooks
	public BorrowBooks() {
		// for setting the title for the internal frame
		super("Borrow Books", false, true, false, true);
		// for setting the icon
		setFrameIcon(new ImageIcon(
				ClassLoader.getSystemResource("images/Export16.gif")));
		// for getting the graphical user interface components display area
		Container cp = getContentPane();

		// for setting the layout
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		// for setting the font
		title.setFont(new Font("Tahoma", Font.BOLD, 14));
		// for adding the label to the panel
		northPanel.add(title);
		// for adding the panel to the container
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
		borrowButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		// for setting the font to the button
		borrowButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		// for adding the button to the panel
		borrowButtonPanel.add(borrowButton);
		// for adding the panel to the centre panel
		centerPanel.add("South", borrowButtonPanel);
		// for setting the border to the panel
		centerPanel.setBorder(BorderFactory
				.createTitledBorder("Borrow a book:"));
		// for adding the panel to the container
		cp.add("Center", centerPanel);

		// for adding the layout
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		// for setting the font to the button
		cancelButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		// for adding the button to the panel
		southPanel.add(cancelButton);
		// for setting the border to the panel
		southPanel.setBorder(BorderFactory.createEtchedBorder());
		// for adding the panel to the container
		cp.add("South", southPanel);

		/***********************************************************************
		 * for adding the action listener to the button,first the text will be *
		 * taken from the JTextField[] and make the connection for database, *
		 * after that update the table in the database with the new value *
		 ***********************************************************************/
		borrowButton.addActionListener(new ActionListener() {
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
						book = new Books();
						member = new Members();
						borrow = new Loans();
						try {
							member.select("select member_id, my_members from all_members where member_id = "
									+ Integer.valueOf(data[0]));
							System.out.println("before book.select");
							book.select("select book_id, quantity, available, loaned, my_books from all_books where book_id = "
									+ Integer.valueOf(data[1]));
							if (book.getBookID() <= 0
									&& member.getMemberID() <= 0) {
								JOptionPane.showMessageDialog(null,
										"Invalid book/member information!",
										"Error", JOptionPane.ERROR_MESSAGE);
								return;
							}
							System.out.println("before borrow.update");
							borrow.update("insert into all_loans(loan_id, member_id, book_id, request_date, return_date, other_details) values ("
									+ data[0]
									+ ','
									+ data[0]
									+ ','
									+ data[1]
									+ ",\'"
									+ data[2]
									+ "\',\'"
									+ data[3]
									+ "\',\'" + data[4] + "\')");
							JOptionPane
									.showMessageDialog(
											null,
											"The book has been successfully borrowed! Thank you.",
											"Success",
											JOptionPane.INFORMATION_MESSAGE);
							clearTextField();
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(null, e.toString(),
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				};
				runner.start();
			}
		});
		// for adding the action listener for the button to dispose the frame
		cancelButton.addActionListener(new ActionListener() {
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