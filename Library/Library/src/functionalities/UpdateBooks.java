package functionalities;

//import the packages for using the classes in them into the program

import items.Books;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

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
public class UpdateBooks extends JInternalFrame {

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
	private JLabel northLabel = new JLabel("BOOK INFORMATION");

	// for creating the Centre Panel
	private JPanel centerPanel = new JPanel();
	// for creating an Internal Panel in the centre panel
	private JPanel informationLabelPanel = new JPanel();

	// for creating an array of JLabel
	private JLabel[] informationLabel = new JLabel[6];
	private JLabel lblShelfNo = new JLabel(" Shelf No");
	private JTextField txtShelfNo = new JTextField();
	// for creating an array of String
	private String[] informationString = { "BookID: ", "Book title: ",
			"Publication date: ", "Quantity", "Available", "Loaned", };
	// for creating an Internal Panel in the centre panel
	private JPanel informationTextFieldPanel = new JPanel();
	// for creating an array of JTextField
	private JTextField[] informationTextField = new JTextField[6];

	// for creating an Internal Panel in the centre panel
	private JPanel insertInformationButtonPanel = new JPanel();
	// for creating a button
	private JButton insertInformationButton = new JButton(
			"Update the Information");

	// for creating South Panel
	private JPanel southPanel = new JPanel();
	// for creating a button
	private JButton OKButton = new JButton("Exit");

	// create objects from another classes for using them in the ActionListener
	private Books book;
	// for creating an array of string to store the data
	private String[] data;

	// for setting available option to true
	// private boolean available = true;

	// for checking the information from the text field
	public boolean isCorrect() {
		data = new String[6];
		for (int i = 0; i < informationLabel.length; i++) {
			if (!informationTextField[i].getText().equals("")) {
				data[i] = informationTextField[i].getText();
			} else {
				return false;
			}
		}
		return true;
	}

	// for setting the array of JTextField to empty
	public void clearTextField() {
		for (int i = 0; i < informationTextField.length; i++) {
			informationTextField[i].setText(null);
		}
		txtShelfNo.setText(null);
	}

	// constructor of addBooks
	public UpdateBooks() {
		// for setting the title for the internal frame
		super("Update a book", false, true, false, true);
		// for setting the icon
		setFrameIcon(new ImageIcon(
				ClassLoader.getSystemResource("images/Edit16.gif")));
		// for getting the graphical user interface components display area
		Container cp = getContentPane();

		// for setting the layout
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		// for setting the font for the North Panel
		northLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		// for adding the label in the North Panel
		northPanel.add(northLabel);
		// for adding the north panel to the container
		cp.add("North", northPanel);

		// for setting the layout
		centerPanel.setLayout(new BorderLayout());
		// for setting the border to the panel
		centerPanel.setBorder(BorderFactory
				.createTitledBorder("Update a new book:"));
		// for setting the layout
		informationLabelPanel.setLayout(new GridLayout(7, 1, 1, 1));
		/***********************************************************************
		 * for adding the strings to the labels, for setting the font * and
		 * adding these labels to the panel. * finally adding the panel to the
		 * container *
		 ***********************************************************************/
		for (int i = 0; i < informationLabel.length; i++) {
			informationLabelPanel.add(informationLabel[i] = new JLabel(
					informationString[i]));
			informationLabel[i].setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		centerPanel.add("West", informationLabelPanel);

		// for setting the layout
		informationTextFieldPanel.setLayout(new GridLayout(7, 1, 1, 1));
		/***********************************************************************
		 * for adding the strings to the labels, for setting the font * and
		 * adding these labels to the panel. * finally adding the panel to the
		 * container *
		 ***********************************************************************/
		for (int i = 0; i < informationTextField.length; i++) {
			informationTextFieldPanel
					.add(informationTextField[i] = new JTextField(25));
			informationTextField[i].setFont(new Font("Tahoma", Font.PLAIN, 11));
		}
		lblShelfNo.setFont(new Font("Tahoma", Font.BOLD, 11));
		// informationLabelPanel.add(lblShelfNo);
		txtShelfNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		// informationTextFieldPanel.add(txtShelfNo);
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

				if (!isCorrect()) {
					JOptionPane.showMessageDialog(null,
							"Please complete all the fields!", "Warning",
							JOptionPane.WARNING_MESSAGE);
				}
					Thread runner = new Thread() {

						public void run() {
							book = new Books();
							try {
								book.select("select book_id, quantity, available, loaned, my_books from all_books where book_id ="
										+ data[0]);
								int bookID = book.getBookID();
								if (data[0].equals(String.valueOf(bookID))) {
									String query = "update all_books set book_id = "
											+ Integer.valueOf(data[0])
											+ ",quantity = "
											+ Integer.valueOf(data[3])
											+ ",available = "
											+ Integer.valueOf(data[4])
											+ ",loaned = "
											+ Integer.valueOf(data[5])
											+ ",my_books = books(book(\'"
											+ data[1]
											+ "\',\'"
											+ data[2]
											+ "\')) where book_id ="
											+ Integer.valueOf(data[0]);
									Statement statement = MyConnection.getConnection()
											.createStatement();
									statement.executeUpdate(query);
									statement.close();
									clearTextField();
									JOptionPane.showMessageDialog(null,
											"Update complete!", "Success",
											JOptionPane.INFORMATION_MESSAGE);

								} else {
									JOptionPane.showMessageDialog(null,
											"The book doesn't exist!", "Error",
											JOptionPane.ERROR_MESSAGE);
								}
							} catch (SQLException e) {
								JOptionPane.showMessageDialog(null,
										e.toString(), "Error",
										JOptionPane.ERROR_MESSAGE);
							}

						}
					};
					runner.start();
				} 
		});
		// for adding the action listener for the button to dispose the frame
		OKButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				clearTextField();
				dispose();
			}
		});
		setResizable(true);
		setVisible(true);
		pack();
	}
}