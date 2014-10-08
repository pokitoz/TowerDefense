package steps;

import java.awt.Point;
import java.util.ArrayList;

import all.Constant;

public class Step {

	private Point init = null;
	private Point end = null;

	private Point nextInit = null;
	private Point oldEnd = null;

	private ArrayList<Point> beziers = null;

	private Orientation orientation = Orientation.NONE;

	public Step(Point init) {

		this.init = new Point(init.x * Constant.SQUARE_PIXEL
				+ Constant.SQUARE_PIXEL / 2, init.y * Constant.SQUARE_PIXEL
				+ Constant.SQUARE_PIXEL / 2);

		this.end = new Point(init.x * Constant.SQUARE_PIXEL
				+ Constant.SQUARE_PIXEL / 2, init.y * Constant.SQUARE_PIXEL
				+ Constant.SQUARE_PIXEL / 2);
	}

	public Point getInit() {
		return new Point(init);
	}

	public Point getEnd() {
		return new Point(end);
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setNextInit() {

		switch (orientation) {
		case N:
			nextInit = new Point(init.x, init.y + Constant.SQUARE_PIXEL / 2);
			break;
		case E:
			nextInit = new Point(init.x + Constant.SQUARE_PIXEL / 2, init.y);
			break;
		case W:
			nextInit = new Point(init.x - Constant.SQUARE_PIXEL / 2, init.y);
			break;
		case S:
			nextInit = new Point(init.x, init.y - Constant.SQUARE_PIXEL / 2);
			break;

		default:
			nextInit = null;
			System.out.println("Nextinit is null");
			break;
		}
	}

	public Point getNextInit() {
		if (nextInit == null) {
			return new Point(init);
		}
		return new Point(nextInit);
	}

	public void setEnd(Point end) {
		this.end = new Point(end.x * Constant.SQUARE_PIXEL
				+ Constant.SQUARE_PIXEL / 2, end.y * Constant.SQUARE_PIXEL
				+ Constant.SQUARE_PIXEL / 2);

		setOldEnd();

	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
		System.out.println("Orientation " + orientation);
		setNextInit();

	}

	public void setOldEnd() {
		switch (orientation) {
		case N:
			oldEnd = new Point(end.x, end.y - Constant.SQUARE_PIXEL / 2);
			break;
		case E:
			oldEnd = new Point(end.x - Constant.SQUARE_PIXEL / 2, end.y);
			break;
		case W:
			oldEnd = new Point(end.x + Constant.SQUARE_PIXEL / 2, end.y);
			break;
		case S:
			oldEnd = new Point(end.x, end.y + Constant.SQUARE_PIXEL / 2);
			break;

		default:
			oldEnd = null;
			System.out.println("oldEnd is null");
			break;
		}
	}

	public Point getOldEnd() {
		if (oldEnd == null) {
			return new Point(init);
		}
		return new Point(oldEnd);
	}

	public void setBezier(Point lastEnd) {
		bezierCurve(lastEnd, nextInit, init);
	}

	public ArrayList<Point> getBeziers() {
		return beziers;
	}

	private void bezierCurve(Point init, Point end, Point bezier) {

		beziers = new ArrayList<Point>();

		/**
		 * int x = (int) ((1 + Math.cos(Math.PI + t * Math.PI)) / 2);
		 */

		for (double t = 0.0; t <= 1; t += 0.1) {
			int x = (int) ((1 - t) * (1 - t) * init.x + 2 * (1 - t) * t
					* bezier.x + t * t * end.x);
			int y = (int) ((1 - t) * (1 - t) * init.y + 2 * (1 - t) * t
					* bezier.y + t * t * end.y);

			beziers.add(new Point(x, y));

		}
	}

	@Override
	public String toString() {
		return "" + orientation + " " + this.init;
	}

}
