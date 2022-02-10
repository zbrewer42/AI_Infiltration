package game;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;

public class GameObject {
	Rectangle2D hitbox;
	// double velocity;
	double angle = 0; // angles are in radians
	LinkedList<Line2D> touching;

	public GameObject(int x, int y, int w, int h) {
		hitbox = new Rectangle2D.Double(x, y, w, h);
		touching = new LinkedList<Line2D>();
	}

	public LinkedList<Line2D> collide(ArrayList<Solid> S) {
		touching = new LinkedList<Line2D>();
		for (Solid s : S) {
			for (Line2D line : s.lines) {
				if (line.intersects(hitbox))
					touching.add(line);
			}
		}
		return touching;
	}

	public void tick() {

	}

	public void scroll(double mx, double my) {
		hitbox.setRect(hitbox.getMinX() + mx, hitbox.getMinY() + my, hitbox.getWidth(), hitbox.getHeight());
	}

	public void aimAt(GameObject o) {
		angle = Math.atan2(o.hitbox.getCenterY() - hitbox.getCenterY(), o.hitbox.getCenterX() - hitbox.getCenterX());
	}
}