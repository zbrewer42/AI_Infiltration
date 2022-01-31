package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Solid extends Polygon {
	ArrayList<Line2D.Double> lines;

	public Solid(ArrayList<Point2D> points) {
		lines = new ArrayList<Line2D.Double>();
		for (int i = 0; i < points.size(); i++) {
			if (i + 1 < points.size()) {
				lines.add(new Line2D.Double(points.get(i), points.get(i + 1)));
			} else {
				lines.add(new Line2D.Double(points.get(i), points.get(0)));
			}
		}
	}

	public void scroll(double mx, double my) {
		for (Line2D l : lines) {
			l.setLine(l.getX1() + mx, l.getY1() + my, l.getX2() + mx, l.getY2() + my);
		}
	}

	static Point2D normal(Line2D line) {
		double angle = Math.atan2(line.getY2() - line.getY1(), line.getX2() - line.getX1());
		Point2D normal = new Point2D.Double(Math.sin(angle), -Math.cos(angle));
		return normal;
	}

	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		for (Line2D.Double l : lines) {
			g.draw(l);
		}
	}
}