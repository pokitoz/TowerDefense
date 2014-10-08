package tower;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import wave.Wave;
import monsters.Monster;
import all.Constant;

public abstract class Tower extends Thread {

	private int speed;
	private int range;
	protected int damage;
	protected Point position;
	protected Color color;
	protected int level;

	protected Wave w;
	protected boolean selected;

	private boolean activated;

	public Tower(TowerType type, Point position) {
		this.range = type.getRange();
		this.damage = type.getDamage();
		this.speed = type.getSpeed();
		this.position = new Point(position.x - Constant.TOWER_PIXEL, position.y
				- Constant.TOWER_PIXEL);
		this.color = type.getColor();
		this.activated = true;
		this.selected = false;

		this.level = 1;

	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public void newWave(Wave w) {
		this.w = w;
	}

	@Override
	public void run() {
		super.run();
		while (activated) {

			shoot();

			try {
				sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public abstract void shoot();

	public void paint(Graphics g) {

		g.setColor(color);
		if (selected) {
			g.drawOval(position.x - range + Constant.TOWER_PIXEL / 2,
					position.y - range + Constant.TOWER_PIXEL / 2, 2 * range,
					2 * range);
		}

		g.fillOval(position.x, position.y, Constant.TOWER_PIXEL,
				Constant.TOWER_PIXEL);
	}

	protected boolean inRange(Monster m) {

		int monsterX = m.getPosition().x;
		int monsterY = m.getPosition().y;

		int posX = position.x;
		int posY = position.y;

		if (((monsterX - posX) * (monsterX - posX) + (monsterY - posY)
				* (monsterY - posY)) <= range * range) {
			return true;
		}

		return false;

	}

	public abstract void upgrade();

	public Point getPosition() {
		return position;
	}


	public boolean isSelected(Point p) {
		// TODO
		return false;
	}

	public boolean isOnTower(Point position) {

		return false;
	}
}
