package monsters;

public class StateChange extends Thread {

	private Monster m = null;
	private int stateChange = 0;
	private int delay;
	private boolean speed;

	public StateChange(Monster m, int stateChange, int delay, boolean speed) {
		this.m = m;
		this.stateChange = stateChange;
		this.delay = delay;
		this.speed = speed;
	}

	@Override
	public void run() {
		super.run();
		try {
			sleep(delay);
			if (speed) {
				m.fastUp(stateChange, -1);
				m.fastSpeed = false;
			} else {
				m.slowDown(stateChange, -1);
				m.slowSpeed = false;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
