package wave;

import java.awt.Point;
import java.util.ArrayList;

import monsters.Monster;
import steps.Circuit;
import tower.DamageTower;
import tower.FastTower;
import tower.FireTower;
import tower.IceTower;
import tower.NormalTower;
import tower.Tower;
import tower.TowerType;

public class WaveManager {

	private ArrayList<Tower> towers = null;
	private Wave currentWave = null;
	private Circuit circuit = null;

	public WaveManager(Circuit circuit) {
		towers = new ArrayList<Tower>();
		this.circuit = circuit;
	}

	public boolean isActive() {
		if (currentWave == null) {
			return false;
		} else if (currentWave.isOver()) {
			return false;
		}

		return true;

	}

	public void addTower(Point position, TowerType type) {

		for (Tower t : towers) {
			if (t.isOnTower(position)) {
				System.out.println("There is already a tower here "
						+ t.getPosition());
				return;
			}
		}

		if (Circuit.occupiedSquare(position)) {
			System.out.println("Impossible to build here");
			return;

		}

		Tower n = null;

		switch (type) {
		case NORMAL:
			n = new NormalTower(position);
			break;
		case FIRE:
			n = new FireTower(position);
			break;
		case FAST:
			n = new FastTower(position);
			break;
		case ICE:
			n = new IceTower(position);
			break;
		case DAMAGE:
			n = new DamageTower(position);
			break;
		default:
			n = new NormalTower(position);
			break;
		}

		towers.add(n);
		n.newWave(currentWave);
		n.start();
	}

	public void newWave(int number, int life, int speedInterval, int speed) {

		if (life <= 0 || number <= 0 || speedInterval <= 0 || speed <= 0
				|| circuit.getSteps().size() <= 0) {
			return;
		}

		if (currentWave != null && !currentWave.isOver()) {
			ArrayList<Monster> m = currentWave.getMonsters();
			for (int i = 0; i < m.size(); i++) {
				m.get(i).kill();
			}
			currentWave.interrupt();

		}

		currentWave = new Wave(number, life, speedInterval, speed,
				circuit.getSteps(), towers);

		for (Tower t : towers) {
			t.newWave(currentWave);
		}

		new Thread(currentWave).start();
	}

	public ArrayList<Tower> getTowers() {
		return towers;
	}

	public ArrayList<Monster> getMonsters() {
		if (currentWave != null && !currentWave.isOver()) {
			return currentWave.getMonsters();
		}
		return new ArrayList<Monster>();
	}

	public Tower getSelectedTower(Point p) {

		for (int i = 0; i < towers.size(); i++) {
			if (towers.get(i).isOnTower(p)) {
				return towers.get(i);
			}
		}

		return null;

	}
}
