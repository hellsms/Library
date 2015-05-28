package ui;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;

public class StatusBar extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel statusBar = new JLabel("  ");

	public StatusBar() {
		statusBar.setFont(new Font("Tahoma", Font.BOLD, 9));
		this.add(statusBar);
		this.setBorder(new SoftBevelBorder(
				javax.swing.border.BevelBorder.LOWERED));
	}
}