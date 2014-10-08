package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import all.Action;
import all.Constant;
import tower.Tower;
import tower.TowerType;

public class SelectorGUI extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4142642582360074228L;
	private TowerType type = null;
	private Tower selectedTower = null;
	private Action action = null;

	public SelectorGUI() {

		this.action = Action.CREATION;
		this.type = TowerType.NORMAL;
		this.selectedTower = null;
		init();

	}

	private void init() {
		Dimension d = new Dimension(160, 100);
		setMinimumSize(d);
		setMaximumSize(d);
		setPreferredSize(d);

		setBackground(Color.DARK_GRAY);
		addMouseListener(this);
		setVisible(true);
		setFocusable(true);

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.translate(10, 10);
		g.setColor(Color.white);

		for (int i = 0; i < TowerType.getNumber(); i++) {
			int x = i * (Constant.TOWER_PIXEL + 10);
			g.setColor(TowerType.values()[i].getColor());

			g.drawRect(x, 0, Constant.TOWER_PIXEL, Constant.TOWER_PIXEL);

			g.fillOval(x, 0, Constant.TOWER_PIXEL, Constant.TOWER_PIXEL);

		}

		g.translate(-10, -10);

	}

	public void changeAction(Action action) {
		this.action = action;
	}

	public void changeTowerType(TowerType type) {
		this.type = type;

	}

	public Action getAction() {
		return action;
	}

	public TowerType getType() {
		return type;
	}

	public void mouseClicked(MouseEvent e) {

		System.out.println("S " + e.getPoint());
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {

		int x = e.getX();
		int y = e.getY();

		for (int i = 0; i < TowerType.getNumber(); i++) {
			int x2 = i * (Constant.TOWER_PIXEL + 10) + 10;
			int y2 = 10;

			if (x2 <= x && x <= x2 + Constant.TOWER_PIXEL && y2 <= y
					&& y <= y2 + Constant.TOWER_PIXEL) {
				type = TowerType.values()[i];
			}
		}

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
