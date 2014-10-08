package monsters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import steps.Orientation;
import steps.Step;
import all.Constant;

public class Monster {

	private int maxLife;
	private int life;
	private int speed;
	private boolean release;
	private boolean alive;

	private ArrayList<Step> steps;
	private Orientation o = null;
	private int currentStep;
	private double x;
	private double y;

	private int indexCurve;
	private ArrayList<Point> curvedPoint = null;

	private double unitStep = 0.7;

	public boolean slowSpeed = false;
	boolean fastSpeed = false;

	public Monster(int life, int speed, ArrayList<Step> circuitSteps) {
		curvedPoint = new ArrayList<Point>();

		this.maxLife = life;
		this.life = life;
		this.speed = speed;

		this.release = false;
		this.alive = true;

		this.currentStep = 0;
		this.steps = circuitSteps;
		this.o = steps.get(currentStep).getOrientation();

		this.x = circuitSteps.get(currentStep).getInit().x;
		this.y = circuitSteps.get(currentStep).getInit().y;

	}

	public void release() {
		release = true;
	}

	public void fastUp(int fast, int time) {
		if (fast <= 0) {
			return;
		}

		speed -= fast;
		fastSpeed = true;
		// Infinity
		if (time != -1) {
			new StateChange(this, fast, time, false).start();
		}
	}

	public synchronized void slowDown(int slow, int time) {
		if (slow <= 0) {
			return;
		}

		speed += slow;
		slowSpeed = true;

		// Infinity
		if (time != -1) {
			new StateChange(this, slow, time, true).start();
		}

	}

	public void run(long time) {

		if (!hasArrived() && isAlive()) {

			Point endStep = steps.get(currentStep).getOldEnd();
			if (endStep == null) {
				endStep = new Point((int) x, (int) y);
			}

			switch (o) {
			case E:
				x += unitStep;
				break;
			case W:
				x -= unitStep;
				break;
			case N:
				y += unitStep;
				break;
			case S:
				y -= unitStep;
				break;

			case C:

				x = curvedPoint.get(indexCurve).getX();
				y = curvedPoint.get(indexCurve).getY();
				indexCurve += 1;

				if ((indexCurve >= curvedPoint.size())) {
					x = steps.get(currentStep).getNextInit().x;
					y = steps.get(currentStep).getNextInit().y;
					o = steps.get(currentStep).getOrientation();
				}

				break;

			default:
				System.err.println("Problem with the orientation");
				break;
			}

			if ((int) x == endStep.x && (int) y == endStep.y
					&& currentStep + 1 != steps.size()) {

				o = Orientation.C;
				currentStep++;
				indexCurve = 0;
				curvedPoint = steps.get(currentStep).getBeziers();
			}

		}

	}

	public void paint(Graphics g) {
		if (release && isAlive() && !hasArrived()) {

			g.setColor(Color.orange);
			g.fillRect((int) x - Constant.MONSTER_PIXEL / 2, (int) y
					- Constant.MONSTER_PIXEL / 2, Constant.MONSTER_PIXEL,
					Constant.MONSTER_PIXEL);

			g.setColor(Color.red);
			g.fillRect(
					(int) x - Constant.MONSTER_PIXEL / 2,
					(int) y,
					(int) (((double) life / (double) maxLife) * Constant.MONSTER_PIXEL),
					Constant.MONSTER_PIXEL / 3);

			g.setColor(Color.black);
			g.drawRect((int) x - Constant.MONSTER_PIXEL / 2, (int) y
					- Constant.MONSTER_PIXEL / 2, Constant.MONSTER_PIXEL,
					Constant.MONSTER_PIXEL);

		}
	}

	public Point getPosition() {
		return new Point((int) x, (int) y);
	}

	public int getStep() {
		return currentStep;
	}

	public boolean isRelease() {
		return release;
	}

	public boolean isAlive() {
		return alive;
	}

	public boolean hasArrived() {
		Point end = steps.get(steps.size() - 1).getEnd();
		return (((int) x == end.x && (int) y == end.y));
	}

	public synchronized void attack(int damage) {
		life -= damage;
		if (life < 0) {
			alive = false;
		}
	}

	public void kill() {
		life = -20;
	}

}
