package tower;

import java.awt.Point;

import monsters.Monster;

public class IceTower extends Tower {

	public IceTower(Point position) {
		super(TowerType.ICE, position);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void upgrade() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shoot() {
		if (w != null && !w.isOver()) {

			for (Monster m : super.w.getMonsters()) {
				if (m.isAlive() && m.isRelease() && !m.slowSpeed) {
					if (super.inRange(m)) {
						m.slowDown(20, 6000);
						break;
					}

				}
			}
		}

	}

}
