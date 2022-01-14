import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;

class Player extends GameObject {
	// does not utilize the velocity or angle how the A.I. classes do.
	// movement will be translated into angle/velocity, not controlled by it.

	int health;
	boolean up, down, left, right, space;
	float vx, vy;
	int inv;
	Game game;
	LinkedList<Line2D> touching;
	static final int W = 11, H = 11;
	static final float speed = 0.5f;

//	Sword s;
//	Gun g;

	public Player(Game game) {
		super(10, 10, W, H);
		up = down = left = right = space = false;
		angle = 0;
		vx = vy = 0;
		this.game = game;
		touching = new LinkedList<Line2D>();

//		s = new Sword(x, y, 20, 30);
//		g = new Gun(x + w / 4, y - 5, 10, 10);
		health = 50;
		inv = 0;
	}

	public void render(Graphics2D g) {
		g.setColor(new Color(255, 255, 255));
		g.fill(hitbox);
		g.draw(new Line2D.Double(hitbox.getCenterX(), hitbox.getCenterY(), hitbox.getCenterX() + vx * 20,
				hitbox.getCenterY() + vy * 20));
		tick();
	}

	public void tick() {
		if (up && !down)
			vy = -speed;
		else if (!up && down)
			vy = speed;
		else
			vy = 0;

		if (left && !right)
			vx = -speed;
		else if (!left && right)
			vx = speed;
		else
			vx = 0;

		if (vx != 0)
			vy = (float) (vy / Math.sqrt(2));
		if (vy != 0)
			vx = (float) (vx / Math.sqrt(2));

		trigMove();
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

	public void collide(ArrayList<Solid> S) {
		touching = new LinkedList<Line2D>();
		for (Solid s : S) {
			for (Line2D line : s.lines) {
				if (line.intersects(hitbox))
					touching.add(line);
			}
		}
	}

	public void damage(int val) {
		if (inv == 0) {
			health -= val;
			inv = 1;
		} else
			inv = 0;
	}
}
