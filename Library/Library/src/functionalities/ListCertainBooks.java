package functionalities;

import items.Books;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Timestamp;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import logics.MyConnection;
import oracle.jdbc.OracleTypes;

public class ListCertainBooks extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	private JPanel northPanel = new JPanel();
	private JLabel northLabel = new JLabel("BOOK INFORMATION");

	private JPanel centerPanel = new JPanel();
	private JPanel informationLabelPanel = new JPanel();

	private JLabel[] informationLabel = new JLabel[2];
	private JLabel lblShelfNo = new JLabel(" Shelf No");
	private JTextField txtShelfNo = new JTextField();
	private String[] informationString = { "First row:", "Last row:" };
	private JPanel informationTextFieldPanel = new JPanel();
	private JTextField[] informationTextField = new JTextField[2];

	private JPanel insertInformationButtonPanel = new JPanel();
	private JButton insertInformationButton = new JButton(
			"Insert the Information");

	private JPanel southPanel = new JPanel();
	private JButton OKButton = new JButton("Exit");

	private JTable table;
	private Object rowData[][];
	private String[] columnNames;
	private JScrollPane scrollPane;
	private JInternalFrame internalFrame;

	private Books book;
	private String[] data;

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
		txtShelfNo.setText(null);
	}

	public void afterInsert() {
		table = new JTable(rowData, columnNames);
		table.setLocation(10,10);
		table.setSize(500,500);
		table.setName("Books:");
		table.setVisible(true);
		scrollPane = new JScrollPane(table);
		scrollPane.setVisible(true);
		BorderLayout layout = new BorderLayout();
		internalFrame = new JInternalFrame();
		internalFrame.setLayout(layout);
		internalFrame.setBorder(getBorder());
		internalFrame.setSize(new Dimension(250, 250));
		internalFrame.setMaximizable(true);
		internalFrame.setResizable(true);
		internalFrame.setIconifiable(true);
		internalFrame.setClosable(true);
		internalFrame.add(scrollPane, BorderLayout.CENTER);
		internalFrame.setVisible(true);
		JDesktopPane desktop = this.getDesktopPane();
		desktop.add(internalFrame);
	}

	public ListCertainBooks() {
		super("List Certain Books", false, true, false, true);
		setFrameIcon(new ImageIcon(
				ClassLoader.getSystemResource("images/List16.gif")));
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		Container cp = getContentPane();

		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		northLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		northPanel.add(northLabel);
		cp.add("North", northPanel);

		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBorder(BorderFactory
				.createTitledBorder("List certain books:"));
		informationLabelPanel.setLayout(new GridLayout(3, 1, 1, 1));
		for (int i = 0; i < informationLabel.length; i++) {
			informationLabelPanel.add(informationLabel[i] = new JLabel(
					informationString[i]));
			informationLabel[i].setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		centerPanel.add("West", informationLabelPanel);

		informationTextFieldPanel.setLayout(new GridLayout(3, 1, 1, 1));
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
							String loadCall = "{call library_management.any_rows_proc(?,?,?)}";
							CallableStatement call = MyConnection
									.getConnection().prepareCall(loadCall);
							call.setString(1, data[0]);
							call.setString(2, data[1]);
							call.registerOutParameter(3, OracleTypes.CURSOR);
							call.execute();
							ResultSet resultSet = (ResultSet) call.getObject(3);
							ResultSetMetaData resultSetMetaData = resultSet
									.getMetaData();
							int columnNo = resultSetMetaData.getColumnCount();
							columnNames = new String[columnNo+1];
							columnNames[0] = "Row number";
							columnNames[1] = "Book ID";
							columnNames[2] = "Quantity";
							columnNames[3] = "Available";
							columnNames[4] = "Loaned";
							columnNames[5] = "Title";
							columnNames[6] = "Publication date";
							int rowNo = Integer.valueOf(data[1])
									- Integer.valueOf(data[0]) + 1;
							System.out.println(rowNo);
							rowData = new Object[rowNo][columnNo+1];
							int counter = 0;
							int row = Integer.valueOf(data[0]);
							while (resultSet.next()) {
								book.setBookID(resultSet.getInt(1));
								book.setNumberOfBooks(resultSet.getInt(3));
								book.setNumberOfAvailableBooks(resultSet
										.getInt(4));
								book.setNumberOfBorrowedBooks(resultSet
										.getInt(5));
								book.setBooksArray(resultSet.getArray(6));
								Object[] myBooks = (Object[]) book
										.getBooksArray().getArray();
								for (int i = 0; i < myBooks.length; i++) {
									Struct mybook = (Struct) myBooks[i];
									Object[] attributes = mybook
											.getAttributes();
									String title = (String) attributes[0];
									Timestamp publicationDate = (Timestamp) attributes[1];
									book.setTitle(title);
									book.setPublicationDate(publicationDate);
								}
								rowData[counter][0] = row;
								rowData[counter][1] = book.getBookID();
								rowData[counter][2] = book.getNumberOfBooks();
								rowData[counter][3] = book
										.getNumberOfAvailableBooks();
								rowData[counter][4] = book
										.getNumberOfBorrowedBooks();
								rowData[counter][5] = book.getTitle();
								rowData[counter][6] = book.getPublicationDate();
								counter++;
								row++;
							}
							resultSet.close();
							call.close();
							clearTextField();
							afterInsert();
							JOptionPane.showMessageDialog(null,
									"Rows are available!", "Success",
									JOptionPane.INFORMATION_MESSAGE);
						} catch (SQLException ex) {
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