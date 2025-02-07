package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Solid extends Polygon {
	ArrayList<Line2D.Double> lines;
	Polygon polygon = null;

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

	public Solid(ArrayList<Point2D> points, boolean isPolygon) {
		if (isPolygon) {
			int[] xPoints = new int[points.size()], yPoints = new int[points.size()];
			for (int i = 0; i < points.size(); i++) {
				xPoints[i] = (int) points.get(i).getX();
				yPoints[i] = (int) points.get(i).getY();
			}
			polygon = new Polygon(xPoints, yPoints, xPoints.length);
		}
		lines = new ArrayList<Line2D.Double>();
		for (int i = 0; i < points.size(); i++) {
			if (i + 1 < points.size()) {
				lines.add(new Line2D.Double(points.get(i), points.get(i + 1)));
			} else {
				lines.add(new Line2D.Double(points.get(i), points.get(0)));
			}
		}
	}

	static Point2D normal(Line2D line) {
		double angle = Math.atan2(line.getY2() - line.getY1(), line.getX2() - line.getX1());
		Point2D normal = new Point2D.Double(Math.round(Math.sin(angle) * 1000) / 1000.0,
				Math.round(-Math.cos(angle) * 1000) / 1000.0);
		return normal;
	}

	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		for (Line2D.Double l : lines) {
			g.draw(l);
		}
		if (polygon != null)
			g.fill(polygon);
	}
}