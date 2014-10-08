package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import all.Action;
import all.Game;
import tower.TowerType;

public class GameFrame extends JFrame implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;

	private Game game = null;
	private SelectorGUI selectorGUI = null;
	private PlayerGUI playerGUI = null;

	private boolean isRunning;
	private int fps = 20;

	public GameFrame() {
		selectorGUI = new SelectorGUI();
		playerGUI = new PlayerGUI();
		isRunning = true;
		game = new Game(selectorGUI);
		init();
	}

	private void init() {

		setTitle("Game");
		setVisible(true);
		addKeyListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultLookAndFeelDecorated(true);
		setSize(400, 400);

		JPanel left = new JPanel();
		JPanel right = new JPanel();

		left.add(selectorGUI);
		right.add(playerGUI);

		JPanel menu = new JPanel();
		BoxLayout boxLayout = new BoxLayout(menu, BoxLayout.LINE_AXIS);

		menu.setLayout(boxLayout);
		menu.add(left);
		// menu.add(Box.createHorizontalGlue());
		menu.add(right);

		menu.setBorder(BorderFactory.createEtchedBorder());

		JPanel total = new JPanel();
		boxLayout = new BoxLayout(total, BoxLayout.PAGE_AXIS);

		total.setLayout(boxLayout);
		total.add(game);

		JPanel m = new JPanel();
		m.add(menu);

		total.setBounds(0, 0, 0, 0);
		total.add(m);

		add(total);

	}

	public void run() {

		while (isRunning) {
			long time = System.currentTimeMillis();

			// update();
			game.repaint();

			// delay for each frame - time it took for one frame
			time = (1000 / fps) - (System.currentTimeMillis() - time);

			if (time > 0) {
				try {
					Thread.sleep(time);
				} catch (Exception e) {
				}
			}

		}
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_A) {

			int number = 10;
			int life = 1000;
			int speedInterval = 500;
			int speed = 10;
			game.newWave(number, life, speedInterval, speed);

		} else if (arg0.getKeyCode() == KeyEvent.VK_C) {
			selectorGUI.changeAction(Action.CREATION);
		} else if (arg0.getKeyCode() == KeyEvent.VK_S) {
			selectorGUI.changeAction(Action.SELECTION);
		} else if (arg0.getKeyCode() == KeyEvent.VK_I) {
			selectorGUI.changeTowerType(TowerType.ICE);
		} else if (arg0.getKeyCode() == KeyEvent.VK_N) {
			selectorGUI.changeTowerType(TowerType.NORMAL);

		}
	}

}
