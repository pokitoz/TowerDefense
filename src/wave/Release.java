package wave;

import java.util.ArrayList;

import monsters.Monster;

public class Release implements Runnable {

	private ArrayList<Monster> monsters = null;
	private int sleepTime = 0;

	public Release(ArrayList<Monster> monsters, int sleepTime) {
		this.monsters = monsters;
		this.sleepTime = sleepTime;
	}

	public void run() {
		for (int i = monsters.size() - 1; i >= 0; i--) {
			monsters.get(i).release();
			try {
				Thread.sleep(i * sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
