package ui;
//import the packages for using the classes in them into the program

import javax.swing.*;

public class Toolbar extends JToolBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JButton[] button;
	public String[] imageName24 = { "images/Add24.gif", "images/List24.gif",
			"images/Add24.gif", "images/List24.gif", "images/Find24.gif", "images/csv.jpg",
			"images/Export24.gif", "images/Import24.gif", "images/Exit24.gif"};
	public String[] tipText = { "Add Books", "List All Books", "Add Members",
			"List Members", "Search", "Import CSV", "Borrow Books", "Return Books", "Exit"};

	public Toolbar() {
		button = new JButton[30];
		for (int i = 0; i < imageName24.length; i++) {
			if (i == 2 || i == 4 || i == 6 || i == 8)
				addSeparator();
			add(button[i] = new JButton(new ImageIcon(
					ClassLoader.getSystemResource(imageName24[i]))));
			button[i].setToolTipText(tipText[i]);
		}
	}
}