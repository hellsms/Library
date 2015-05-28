package functionalities;

import javax.swing.*;
import javax.swing.table.TableColumn;

import ui.ResultSetTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.SQLException;

public class ListAuthors extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	//for creating the North Panel
	private JPanel northPanel = new JPanel();
	//for creating the centre Panel
	private JPanel centerPanel = new JPanel();
	//for creating the label
	private JLabel northLabel = new JLabel("THE LIST FOR THE AUTHORS");
	//for creating the button
	private JButton printButton;
	//for creating the table
	private JTable table;
	//for creating the TableColumn
	private TableColumn column = null;
	//for creating the JScrollPane
	private JScrollPane scrollPane;

	//for creating an object for the ResultSetTableModel class
	private ResultSetTableModel tableModel;

	/***************************************************************************
	 * for setting the required information for the ResultSetTableModel class. *
	 ***************************************************************************/
	private static final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";
	private static final String DATABASE_URL = "jdbc:oracle:thin:system/system@localhost:1521/xe";
	private static final String DEFAULT_QUERY = "select * from authors";

	//constructor of listBooks
	public ListAuthors() {
		//for setting the title for the internal frame
		super("Authors", false, true, false, true);
		//for setting the icon
		setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/List16.gif")));

		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		//for getting the graphical user interface components display area
		Container cp = getContentPane();

		//for passing the required information to the ResultSetTableModel object
		try {
			tableModel = new ResultSetTableModel(JDBC_DRIVER, DATABASE_URL, DEFAULT_QUERY);
			//for setting the Query
			try {
				tableModel.setQuery(DEFAULT_QUERY);
			}
			catch (SQLException sqlException) {
			}
		}
		catch (ClassNotFoundException classNotFound) {
			System.out.println(classNotFound.toString());
		}
		catch (SQLException sqlException) {
			System.out.println(sqlException.toString());
		}
		//for setting the table with the information
		table = new JTable(tableModel);
		//for setting the size for the table
		table.setPreferredScrollableViewportSize(new Dimension(200, 200));
		//for setting the font
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		//for setting the split pane to the table
		scrollPane = new JScrollPane(table);

		//for setting the size for the table columns
		for (int i = 0; i < 3; i++) {
			column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(20);
		}
		//for setting the font to the label
		northLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		//for setting the layout to the panel
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		//for adding the label to the panel
		northPanel.add(northLabel);
		//for adding the panel to the container
		cp.add("North", northPanel);

		//for setting the layout to the panel
		centerPanel.setLayout(new BorderLayout());
		//for creating an image for the button
		ImageIcon printIcon = new ImageIcon(ClassLoader.getSystemResource("images/Print16.gif"));
		//for adding the button to the panel
		printButton = new JButton("print the authors", printIcon);
		//for setting the tip text
		printButton.setToolTipText("Print");
		//for setting the font to the button
		printButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		//for adding the button to the panel
		//centerPanel.add(printButton, BorderLayout.NORTH);
		//for adding the scroll pane to the panel
		centerPanel.add(scrollPane, BorderLayout.CENTER);
		//for setting the border to the panel
		centerPanel.setBorder(BorderFactory.createTitledBorder("Authors:"));
		//for adding the panel to the container
		cp.add("Center", centerPanel);

		//for adding the actionListener to the button
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Thread runner = new Thread() {
					public void run() {
						try {
							PrinterJob prnJob = PrinterJob.getPrinterJob();
							prnJob.setPrintable(new PrintingBooks(DEFAULT_QUERY));
							if (!prnJob.printDialog())
								return;
							setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
							prnJob.print();
							setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						}
						catch (PrinterException ex) {
							System.out.println("Printing error: " + ex.toString());
						}
					}
				};
				runner.start();
			}
		});
		//for setting the visible to true
		setVisible(true);
		//to show the frame
		pack();
	}
}