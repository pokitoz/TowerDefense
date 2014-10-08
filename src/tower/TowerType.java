package tower;

import java.awt.Color;

public enum TowerType {
	// public Tower(int range, int damage, int speed, Point position)
	NORMAL(100, 25, 200, Color.white), FIRE(50, 50, 200, Color.red), ICE(50,
			10, 400, Color.blue), FAST(50, 10, 400, Color.LIGHT_GRAY), DAMAGE(
			50, 500, 500, Color.ORANGE);

	private final int range;
	private final int damage;
	private final int speed;
	private final Color color;

	private TowerType(int range, int damage, int speed, Color color) {
		this.range = range;
		this.damage = damage;
		this.speed = speed;
		this.color = color;
	}

	public int getRange() {
		return range;
	}

	public int getDamage() {
		return damage;
	}

	public int getSpeed() {
		return speed;
	}

	public Color getColor() {
		return color;
	}

	public static int getNumber() {
		return TowerType.values().length;
	}
}
