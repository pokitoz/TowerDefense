package wave;

import java.util.ArrayList;

import monsters.Monster;
import steps.Step;
import tower.Tower;

public class Wave extends Thread {

	private ArrayList<Monster> monsters = null;
	private ArrayList<Tower> towers = null;

	private int arrived;
	private int killed;
	private int total;
	private boolean finished;

	public Wave(int number, int life, int speedInterval, int speed,
			ArrayList<Step> steps, ArrayList<Tower> towers) {

		this.total = number;
		this.killed = 0;
		this.arrived = 0;
		this.total = number;

		monsters = new ArrayList<Monster>();
		for (int i = 0; i < number; i++) {
			monsters.add(new Monster(life, speed, steps));

		}

		(new Thread(new Release(monsters, speedInterval))).start();

		this.towers = towers;

	}

	public boolean isOver() {
		return finished;
	}

	public void run() {

		while (killed + arrived != total) {

			for (int i = 0; i < monsters.size(); i++) {
				long time = System.currentTimeMillis();

				if (!monsters.get(i).isAlive()) {
					killed++;
					monsters.remove(monsters.get(i));
				} else if (monsters.get(i).hasArrived()) {
					arrived++;
					monsters.remove(monsters.get(i));

				} else if (monsters.get(i).isRelease()) {

					monsters.get(i).run(time);
					System.out.println(time);
					try {
						Thread.sleep(100);
					} catch (Exception e) {

					}
				}

			}
		}

		finished = true;
		System.out.println("End of the wave");

	}

	public ArrayList<Monster> getMonsters() {
		return monsters;
	}

	public ArrayList<Tower> getTowers() {
		return towers;
	}

}
