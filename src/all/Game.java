package all;

import gui.SelectorGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import monsters.Monster;
import steps.Circuit;
import tower.Tower;
import tower.TowerType;
import wave.WaveManager;

public class Game extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;

	private Circuit c = null;
	private WaveManager waveManager = null;

	private BufferedImage background = null;
	private BufferedImage doubleBuffer = null;

	private SelectorGUI selectorGUI = null;

	public Game(SelectorGUI selectorGUI) {
		c = new Circuit("circuit.p");
		waveManager = new WaveManager(c);
		this.selectorGUI = selectorGUI;

		init();
	}

	private void init() {
		doubleBuffer = new BufferedImage(c.getPixelX(), c.getPixelY(),
				BufferedImage.TYPE_INT_RGB);
		// setDoubleBuffered(true);

		Dimension d = new Dimension(c.getPixelX(), c.getPixelY());
		setMinimumSize(d);
		setMaximumSize(d);
		setPreferredSize(d);

		setBackground(Color.darkGray);

		background = c.getBufferedImage();

		this.setVisible(true);
		this.setSize(c.getPixelX(), c.getPixelY());
		this.addMouseListener(this);

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		draw(g);
	}

	private void draw(Graphics g) {
		Graphics bgg = doubleBuffer.getGraphics();

		if (c != null && waveManager != null) {
			bgg.drawImage(background, 0, 0, this);
			for (Monster m : waveManager.getMonsters()) {
				m.paint(bgg);
			}
			
			for (Tower t : waveManager.getTowers()) {
				t.paint(bgg);
			}

		}

		// bgg.setColor(Color.white);
		// bgg.drawRect((mousePosition.x / 10) * 10 - 20,
		// (mousePosition.y / 10) * 10 - 40, Constant.TOWER_PIXEL,
		// Constant.TOWER_PIXEL);

		g.drawImage(doubleBuffer, 0, 0, this);

	}

	// private void update() {
	// mousePosition = MouseInfo.getPointerInfo().getLocation();
	// }

	public void newWave(int number, int life, int speedInterval, int speed) {
		waveManager.newWave(number, life, speedInterval, speed);
	}

	public void newTower(Point position, TowerType type) {
		waveManager.addTower(position, type);
	}

	public void mouseClicked(MouseEvent e) {
		System.out.println("G " + e.getPoint());
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		if (selectorGUI.getAction() == Action.CREATION) {
			Point p = new Point(e.getX() + Constant.TOWER_PIXEL / 2, e.getY()
					+ Constant.TOWER_PIXEL / 2);
			System.out.println(p);
			newTower(p, selectorGUI.getType());
		}
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
