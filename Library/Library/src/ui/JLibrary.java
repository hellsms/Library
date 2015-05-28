package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import logics.MyConnection;
import functionalities.AddBooks;
import functionalities.AddMembers;
import functionalities.BorrowBooks;
import functionalities.ListAuthors;
import functionalities.ListBooks;
import functionalities.ListCategories;
import functionalities.ListCertainBooks;
import functionalities.ListMembers;
import functionalities.ListViewRequested;
import functionalities.ListViewReturned;
import functionalities.LoadCsv;
import functionalities.ReturnBooks;
import functionalities.SearchBooksAndMembers;
import functionalities.UpdateBooks;

public class JLibrary extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel searchPanel = new JPanel();
	private JToolBar searchToolBar = new JToolBar();
	private JLabel searchLabel = new JLabel("Book title: ");
	private JTextField searchTextField = new JTextField(15);
	private JButton goButton = new JButton("Go");

	private JDesktopPane desktop = new JDesktopPane();
	private Menubar menu;
	private Toolbar toolbar;
	private StatusBar statusbar = new StatusBar();
	private ListBooks listBooks;
	private AddBooks addBooks;
	private UpdateBooks updateBooks;
	private BorrowBooks borrowBooks;
	private ReturnBooks returnBooks;
	private ListViewRequested listRequested;
	private ListViewReturned listReturned;
	private AddMembers addMembers;
	private ListMembers listMembers;
	private SearchBooksAndMembers search;
	private LoadCsv loadCsv;
	private ListCertainBooks listCertain;
	private ListCategories listCategories;
	private ListAuthors listAuthors;

	public JLibrary() {
		super("Library Manager");
		setResizable(true);
		setSize(new Dimension(500, 500));
		setMinimumSize(new Dimension(500, 500));
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image image = kit.getImage(ClassLoader
				.getSystemResource("images/lib.png"));
		setIconImage(image);

		menu = new Menubar();
		toolbar = new Toolbar();

		setJMenuBar(menu);
		menu.exit.addActionListener(this);
		menu.addBook.addActionListener(this);
		menu.updateBook.addActionListener(this);
		menu.listBook.addActionListener(this);
		menu.addMember.addActionListener(this);
		menu.listMember.addActionListener(this);
		menu.searchBooksAndMembers.addActionListener(this);
		menu.borrowBook.addActionListener(this);
		menu.returnBook.addActionListener(this);
		menu.listRequested.addActionListener(this);
		menu.listReturned.addActionListener(this);
		menu.loadCsv.addActionListener(this);
		menu.listCertain.addActionListener(this);
		menu.authors.addActionListener(this);
		menu.categories.addActionListener(this);
		
		Container cp = getContentPane();
		desktop.setBackground(new Color(240, 248 ,255));
		cp.add("Center", desktop);
		searchLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		searchTextField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		goButton.setFont(new Font("Tahoma", Font.BOLD, 9));
		searchToolBar.add(searchLabel);
		searchToolBar.add(searchTextField);
		searchToolBar.add(goButton);
		goButton.addActionListener(this);
		searchPanel.setLayout(new BorderLayout());
		searchPanel.add("Center", toolbar);
		// searchPanel.add("South", searchToolBar);
		cp.add("North", searchPanel);
		cp.add("South", statusbar);

		for (int i = 0; i < toolbar.imageName24.length; i++) {
			toolbar.button[i].addActionListener(this);
		}

		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == menu.addBook
				|| ae.getSource() == toolbar.button[0]) {
			Thread runner = new Thread() {

				public void run() {
					addBooks = new AddBooks();
					desktop.add(addBooks);
					try {
						addBooks.setSelected(true);
					} catch (java.beans.PropertyVetoException e) {
					}
				}
			};
			runner.start();
		}
		if (ae.getSource() == menu.updateBook) {
			Thread runner = new Thread() {

				public void run() {
					updateBooks = new UpdateBooks();
					desktop.add(updateBooks);
					try {
						updateBooks.setSelected(true);
					} catch (java.beans.PropertyVetoException e) {
					}
				}
			};
			runner.start();
		}
		if (ae.getSource() == menu.listBook
				|| ae.getSource() == toolbar.button[1]) {
			Thread runner = new Thread() {

				public void run() {
					listBooks = new ListBooks();
					desktop.add(listBooks);
					try {
						listBooks.setSelected(true);
					} catch (java.beans.PropertyVetoException e) {
					}
				}
			};
			runner.start();
		}
		if (ae.getSource() == menu.addMember
				|| ae.getSource() == toolbar.button[2]) {
			Thread runner = new Thread() {

				public void run() {
					addMembers = new AddMembers();
					desktop.add(addMembers);
					try {
						addMembers.setSelected(true);
					} catch (java.beans.PropertyVetoException e) {
					}
				}
			};
			runner.start();
		}
		if (ae.getSource() == menu.listMember
				|| ae.getSource() == toolbar.button[3]) {
			Thread runner = new Thread() {

				public void run() {
					listMembers = new ListMembers();
					desktop.add(listMembers);
					try {
						listMembers.setSelected(true);
					} catch (java.beans.PropertyVetoException e) {
					}
				}
			};
			runner.start();
		}
		if (ae.getSource() == menu.searchBooksAndMembers
				|| ae.getSource() == toolbar.button[4]) {
			Thread runner = new Thread() {

				public void run() {
					search = new SearchBooksAndMembers();
					desktop.add(search);
					try {
						search.setSelected(true);
					} catch (java.beans.PropertyVetoException e) {
					}
				}
			};
			runner.start();
		}
		if (ae.getSource() == menu.borrowBook
				|| ae.getSource() == toolbar.button[6]) {
			Thread runner = new Thread() {

				public void run() {
					borrowBooks = new BorrowBooks();
					desktop.add(borrowBooks);
					try {
						borrowBooks.setSelected(true);
					} catch (java.beans.PropertyVetoException e) {
					}
				}
			};
			runner.start();
		}
		if (ae.getSource() == menu.returnBook
				|| ae.getSource() == toolbar.button[7]) {
			Thread runner = new Thread() {

				public void run() {
					returnBooks = new ReturnBooks();
					desktop.add(returnBooks);
					try {
						returnBooks.setSelected(true);
					} catch (java.beans.PropertyVetoException e) {
					}
				}
			};
			runner.start();
		}
		if (ae.getSource() == menu.listRequested) {
			Thread runner = new Thread() {

				public void run() {
					listRequested = new ListViewRequested();
					desktop.add(listRequested);
					try {
						listRequested.setSelected(true);
					} catch (java.beans.PropertyVetoException e) {
					}
				}
			};
			runner.start();
		}
		if (ae.getSource() == menu.listReturned) {
			Thread runner = new Thread() {

				public void run() {
					listReturned = new ListViewReturned();
					desktop.add(listReturned);
					try {
						listReturned.setSelected(true);
					} catch (java.beans.PropertyVetoException e) {
					}
				}
			};
			runner.start();
		}
		if (ae.getSource() == menu.exit || ae.getSource() == toolbar.button[8]) {
			dispose();
			MyConnection.disconnect();
			System.exit(0);
		}
		if (ae.getSource() == toolbar.button[5]
				|| ae.getSource() == menu.loadCsv) {
			Thread runner = new Thread() {

				public void run() {
					loadCsv = new LoadCsv();
					desktop.add(loadCsv);
					try {
						loadCsv.setSelected(true);
					} catch (java.beans.PropertyVetoException e) {
					}
				}
			};
			runner.start();
		}
		if (ae.getSource() == menu.listCertain) {
			Thread runner = new Thread() {

				public void run() {
					listCertain = new ListCertainBooks();
					desktop.add(listCertain);
					try {
						listCertain.setSelected(true);
					} catch (java.beans.PropertyVetoException e) {
					}
				}
			};
			runner.start();
		}
		if (ae.getSource() == menu.categories) {
			Thread runner = new Thread() {

				public void run() {
					listCategories = new ListCategories();
					desktop.add(listCategories);
					try {
						listCategories.setSelected(true);
					} catch (java.beans.PropertyVetoException e) {
					}
				}
			};
			runner.start();
		}
		if (ae.getSource() == menu.authors) {
			Thread runner = new Thread() {

				public void run() {
					listAuthors = new ListAuthors();
					desktop.add(listAuthors);
					try {
						listAuthors.setSelected(true);
					} catch (java.beans.PropertyVetoException e) {
					}
				}
			};
			runner.start();
		}
	}
}
