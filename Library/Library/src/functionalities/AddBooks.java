package functionalities;

import items.Books;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logics.MyConnection;

public class AddBooks extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	private JPanel northPanel = new JPanel();
	private JLabel northLabel = new JLabel("BOOK INFORMATION");

	private JPanel centerPanel = new JPanel();
	private JPanel informationLabelPanel = new JPanel();

	private JLabel[] informationLabel = new JLabel[4];
	private JLabel lblShelfNo = new JLabel(" Shelf No");
	private JTextField txtShelfNo = new JTextField();
	private String[] informationString = { "BookID: ", "Book title: ",
			"Publication date: ", "Quantity" };
	private JPanel informationTextFieldPanel = new JPanel();
	private JTextField[] informationTextField = new JTextField[4];

	private JPanel insertInformationButtonPanel = new JPanel();
	private JButton insertInformationButton = new JButton(
			"Insert the Information");

	private JPanel southPanel = new JPanel();
	private JButton OKButton = new JButton("Exit");

	private Books book;
	private String[] data;

	public boolean isCorrect() {
		data = new String[4];
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
		txtShelfNo.setText(null);
	}

	public AddBooks() {
		super("Add Books", false, true, false, true);
		setFrameIcon(new ImageIcon(
				ClassLoader.getSystemResource("images/Add16.gif")));
		Container cp = getContentPane();

		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		northLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		northPanel.add(northLabel);
		cp.add("North", northPanel);

		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBorder(BorderFactory
				.createTitledBorder("Add a new book:"));
		informationLabelPanel.setLayout(new GridLayout(5, 1, 1, 1));
		for (int i = 0; i < informationLabel.length; i++) {
			informationLabelPanel.add(informationLabel[i] = new JLabel(
					informationString[i]));
			informationLabel[i].setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		centerPanel.add("West", informationLabelPanel);

		informationTextFieldPanel.setLayout(new GridLayout(5, 1, 1, 1));
		for (int i = 0; i < informationTextField.length; i++) {
			informationTextFieldPanel
					.add(informationTextField[i] = new JTextField(25));
			informationTextField[i].setFont(new Font("Tahoma", Font.PLAIN, 11));
		}
		lblShelfNo.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtShelfNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		centerPanel.add("East", informationTextFieldPanel);

		insertInformationButtonPanel
				.setLayout(new FlowLayout(FlowLayout.RIGHT));
		insertInformationButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		insertInformationButtonPanel.add(insertInformationButton);
		centerPanel.add("South", insertInformationButtonPanel);
		cp.add("Center", centerPanel);

		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		OKButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		southPanel.add(OKButton);
		southPanel.setBorder(BorderFactory.createEtchedBorder());
		cp.add("South", southPanel);

		insertInformationButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {

				if (!isCorrect()) {
					JOptionPane.showMessageDialog(null,
							"Please complete all the fields!", "Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				Thread runner = new Thread() {

					public void run() {
						
						try {
							book = new Books();
							book.select("select book_id,quantity,available,loaned,my_books from all_books where book_id ="
									+ data[0]);
							int bookID = book.getBookID();
							if (data[0].equals(String.valueOf(bookID))) {
								JOptionPane.showMessageDialog(null,
										"The book already exists!", "Error",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
							String sql = "insert into all_books(book_id, quantity, available, loaned, my_books) values "
									+ "(?,?,?,?,books(book(?,?)))";
							PreparedStatement ps = MyConnection.getConnection()
									.prepareStatement(sql);
							ps.setInt(1, Integer.valueOf(data[0]));
							ps.setInt(2, Integer.valueOf(data[3]));
							ps.setInt(3, Integer.valueOf(data[3]));
							ps.setInt(4, 0);
							ps.setString(5, data[1]);
							ps.setString(6, data[2]);
							ps.executeUpdate();
							clearTextField();
							JOptionPane.showMessageDialog(null,
									"The book has been added! Thank you.",
									"Success", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, ex.toString(),
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				};
				runner.start();
			}
		});
		OKButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});
		setVisible(true);
		pack();
	}
}