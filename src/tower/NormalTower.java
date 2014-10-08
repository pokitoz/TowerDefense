package tower;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import monsters.Monster;

public class NormalTower extends Tower {

	public NormalTower(Point position) {
		super(TowerType.NORMAL, position);
	}

	@Override
	public void shoot() {

		if (w != null && !w.isOver()) {

			for (Monster m : super.w.getMonsters()) {
				if (m.isAlive() && m.isRelease()) {
					if (super.inRange(m)) {
						m.attack(super.damage);
						break;
					}

				}
			}
		}
	}

	@Override
	public void upgrade() {
		// TODO Auto-generated method stub

	}

}
