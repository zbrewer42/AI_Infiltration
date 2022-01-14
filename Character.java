package game;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Character extends GameObject {
	float vx, vy;
	int W, H;
	float speed = 0.5f;
	int health;
	int cooldown;
	Game game;

	public Character(int x, int y, int w, int h, Game g) {
		super(x, y, w, h);
		W = w;
		H = h;
		game = g;
	}

	public void trigMove() {
		// x direction
		hitbox.setRect(hitbox.getMinX() + vx, hitbox.getMinY(), W, H);
		collide(game.solids);
		for (Line2D l : touching) {
			Point2D normal = Solid.normal(l);

			if (normal.getX() == 1) {
				hitbox.setRect(hitbox.getMinX() - vx, hitbox.getMinY(), W, H);
			} else if (normal.getY() == -1.0) {
			} else {
				hitbox.setRect(hitbox.getMinX() - (vx + (vx * normal.getX() + vy * normal.getY()) * normal.getX()),
						hitbox.getMinY() - (vy + (vy * normal.getY() + vx * normal.getX()) * normal.getY()), W, H);
			}
		}
		// y direction
		hitbox.setRect(hitbox.getMinX(), hitbox.getMinY() + vy, W, H);
		collide(game.solids);
		for (Line2D l : touching) {
			Point2D normal = Solid.normal(l);

			if (normal.getY() == 1) {
				hitbox.setRect(hitbox.getMinX(), hitbox.getMinY() - vy, W, H);
			} else if (normal.getX() == 1.0) {
				// do nothing
			} else {
				hitbox.setRect(hitbox.getMinX() - (vx + (vx * normal.getX() + vy * normal.getY()) * normal.getX()),
						hitbox.getMinY() - (vy + (vy * normal.getY() + vx * normal.getX()) * normal.getY()), W, H);
			}
		}
	}

	public void trigMove(float mx, float my) {
		vx = mx;
		vy = my;
		// x direction
		hitbox.setRect(hitbox.getMinX() + vx, hitbox.getMinY(), W, H);
		collide(game.solids);
		for (Line2D l : touching) {
			Point2D normal = Solid.normal(l);

			if (normal.getX() == 1) {
				hitbox.setRect(hitbox.getMinX() - vx, hitbox.getMinY(), W, H);
			} else if (normal.getY() == -1.0) {
			} else {
				hitbox.setRect(hitbox.getMinX() - (vx + (vx * normal.getX() + vy * normal.getY()) * normal.getX()),
						hitbox.getMinY() - (vy + (vy * normal.getY() + vx * normal.getX()) * normal.getY()), W, H);
			}
		}
		// y direction
		hitbox.setRect(hitbox.getMinX(), hitbox.getMinY() + vy, W, H);
		collide(game.solids);
		for (Line2D l : touching) {
			Point2D normal = Solid.normal(l);

			if (normal.getY() == 1) {
				hitbox.setRect(hitbox.getMinX(), hitbox.getMinY() - vy, W, H);
			} else if (normal.getX() == 1.0) {
				// do nothing
			} else {
				hitbox.setRect(hitbox.getMinX() - (vx + (vx * normal.getX() + vy * normal.getY()) * normal.getX()),
						hitbox.getMinY() - (vy + (vy * normal.getY() + vx * normal.getX()) * normal.getY()), W, H);
			}
		}
	}
}
