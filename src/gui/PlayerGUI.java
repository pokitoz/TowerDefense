package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PlayerGUI extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4142642582360074228L;
	private String name = null;
	private int gold = 0;

	public PlayerGUI() {

		init();

	}

	private void init() {

		Dimension d = new Dimension(270, 100);

		setMinimumSize(d);
		setMaximumSize(d);
		setPreferredSize(d);

		setBackground(Color.BLACK);
		setBorder(null);

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

	}

}
