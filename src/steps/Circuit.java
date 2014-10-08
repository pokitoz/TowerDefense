package steps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import all.Constant;

public class Circuit {

	private static char circuit[][] = {
			{ 'o', 'o', 'o', 'o', 'o', 'o', 'p', 'p', 'p', 'o' },
			{ 'o', 'p', 'p', 'p', 'p', 'o', 'p', 'o', 'p', 'o' },
			{ 'o', 'p', 'o', 'o', 'p', 'p', 'p', 'o', 'p', 'o' },
			{ 'o', 'p', 'o', 'o', 'o', 'o', 'o', 'o', 'p', 'o' },
			{ 'o', 'p', 'p', 'p', 'o', 'o', 'o', 'o', 'p', 'o' },
			{ 'o', 'o', 'o', 'p', 'o', 'o', 'o', 'o', 'p', 'o' },
			{ 'o', 'o', 'o', 'p', 'o', 'o', 'o', 'o', 'p', 'o' },
			{ 'i', 'p', 'p', 'p', 'o', 'o', 'o', 'o', 'p', 'e' },
			{ 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o' },
			{ 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o' },
			{ 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o' },
			{ 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o' },
			{ 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o' } };

	private BufferedImage map = null;
	private Point initialPosition = null;
	private ArrayList<Step> steps = null;
	private ArrayList<Point> points = null;

	public Circuit(String path) {

		if (path != null) {
			fillArray(path);
		}

		map = new BufferedImage(circuit.length * Constant.SQUARE_PIXEL,
				circuit[0].length * Constant.SQUARE_PIXEL,
				BufferedImage.TYPE_INT_RGB);
		steps = new ArrayList<Step>();
		points = new ArrayList<Point>();
		loadMapImage();
		createPathPoints();
		createSteps();
	}

	private void fillArray(String path) {

		File f = new File(path);
		Scanner sc = null;
		try {
			sc = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if (f != null && sc != null && f.isFile()) {
			int x = sc.nextInt();
			int y = sc.nextInt();
			System.out.println(x + "  " + y);
			circuit = new char[x][y];

			sc.nextLine();
			int i = 0;
			while (sc.hasNext()) {
				String line = sc.nextLine();
				System.out.println(line);
				String lineArray[] = line.split(" ");
				for (int j = 0; j < circuit.length; j++) {
					circuit[j][i] = lineArray[j].charAt(0);
				}

				i++;
			}

		}
	}

	public void loadMapImage() {
		Graphics g = map.getGraphics();
		for (int j = 0; j < circuit[0].length; j++) {
			for (int i = 0; i < circuit.length; i++) {

				if (circuit[i][j] == 'o') {
					g.setColor(Color.darkGray);
					g.fillRect(i * Constant.SQUARE_PIXEL, j
							* Constant.SQUARE_PIXEL, Constant.SQUARE_PIXEL,
							Constant.SQUARE_PIXEL);
				} else if (circuit[i][j] == 'p') {
					g.setColor(Color.green);
					g.fillRect(i * Constant.SQUARE_PIXEL, j
							* Constant.SQUARE_PIXEL, Constant.SQUARE_PIXEL,
							Constant.SQUARE_PIXEL);

				} else if (circuit[i][j] == 'i') {
					g.setColor(Color.blue);
					g.fillRect(i * Constant.SQUARE_PIXEL, j
							* Constant.SQUARE_PIXEL, Constant.SQUARE_PIXEL,
							Constant.SQUARE_PIXEL);

				} else if (circuit[i][j] == 'e') {
					g.setColor(Color.red);
					g.fillRect(i * Constant.SQUARE_PIXEL, j
							* Constant.SQUARE_PIXEL, Constant.SQUARE_PIXEL,
							Constant.SQUARE_PIXEL);

				}
			}
		}

	}

	private void createPathPoints() {

		Point i = findInitialPoint();
		Point old = findInitialPoint();
		points.add(i);
		if (i != null) {
			while (circuit[i.x][i.y] != 'e') {
				i = findNextPoint(old, i);
				points.add(i);
				old = points.get(points.size() - 2);
			}
		}

	}

	private Point findNextPoint(Point old, Point current) {

		Point up = new Point(current.x - 1, current.y);
		Point down = new Point(current.x + 1, current.y);
		Point left = new Point(current.x, current.y - 1);
		Point right = new Point(current.x, current.y + 1);

		Point points[] = { up, down, left, right };

		for (int i = 0; i <= 3; i++) {

			if (points[i].x >= 0 && points[i].x < circuit.length
					&& points[i].y >= 0 && points[i].y < circuit[0].length
					&& (points[i].x != old.x || points[i].y != old.y)) {

				if (circuit[points[i].x][points[i].y] == 'e'
						|| circuit[points[i].x][points[i].y] == 'p') {
					return points[i];
				}
			}

		}

		return null;
	}

	private Point findInitialPoint() {

		for (int i = 0; i < circuit.length; i++) {
			for (int j = 0; j < circuit[0].length; j++) {
				if (circuit[i][j] == 'i') {
					initialPosition = new Point(i, j);
					return initialPosition;
				}
			}
		}

		System.out.println("Error, could not find the init point");
		System.exit(-20);
		return null;
	}

	private Orientation findOrientation(Point begin, Point end) {
		if (begin.x == end.x) {
			if (begin.y - end.y < 0) {
				return Orientation.N;
			} else if (begin.y - end.y > 0) {
				return Orientation.S;
			}
		} else if (begin.y == end.y) {
			if (begin.x - end.x < 0) {
				return Orientation.E;
			} else if (begin.x - end.x > 0) {
				return Orientation.W;
			}
		}

		return Orientation.NONE;

	}

	private void createSteps() {
		steps = new ArrayList<Step>();
		Point lastPoint = initialPosition;
		Step step = new Step(lastPoint);
		Point p = null;
		// System.out.println(initialPosition + " " + p);
		Orientation o = Orientation.NONE;

		for (int i = 1; i < points.size(); i++) {
			p = points.get(i);
			System.out.println("next " + p);
			o = findOrientation(lastPoint, p);
			if (o != step.getOrientation()
					&& step.getOrientation() != Orientation.NONE) {

				steps.add(step);
				Point oldEnd = step.getOldEnd();
				step = new Step(lastPoint);
				step.setOrientation(o);
				step.setBezier(oldEnd);

				i--;

			} else {
				step.setEnd(p);
				step.setOrientation(o);
				lastPoint = p;
			}
		}

		// Add last step
		steps.add(step);
		points = null;
		System.out.println(steps);

	}

	public ArrayList<Step> getSteps() {
		return steps;
	}

	public Point getInitialPosition() {
		return initialPosition;
	}

	public BufferedImage getBufferedImage() {
		return map;
	}

	public static boolean occupiedSquare(Point p) {

		Point arrayPoints[] = new Point[5];
		arrayPoints[0] = p;

		arrayPoints[1] = new Point(p.x + Constant.TOWER_PIXEL / 2, p.y
				+ Constant.TOWER_PIXEL / 2);
		arrayPoints[2] = new Point(p.x + Constant.TOWER_PIXEL / 2, p.y
				- Constant.TOWER_PIXEL / 2);
		arrayPoints[3] = new Point(p.x - Constant.TOWER_PIXEL / 2, p.y
				- Constant.TOWER_PIXEL / 2);
		arrayPoints[4] = new Point(p.x - Constant.TOWER_PIXEL / 2, p.y
				+ Constant.TOWER_PIXEL / 2);

		for (int i = 0; i < arrayPoints.length; i++) {
			int x = arrayPoints[i].x / Constant.SQUARE_PIXEL;
			int y = arrayPoints[i].y / Constant.SQUARE_PIXEL;

			System.out.println(x + " " + y);
			if (x < circuit.length && y < circuit[0].length && 0 <= x && 0 <= y)
				if (circuit[x][y] != 'o') {
					return true;
				}

		}
		return true;
	}

	public int getPixelX() {
		return circuit.length * Constant.SQUARE_PIXEL;
	}

	public int getPixelY() {
		return circuit[0].length * Constant.SQUARE_PIXEL;
	}

}
