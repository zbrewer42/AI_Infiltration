package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

class Player extends Character {
	// does not utilize the velocity or angle how the A.I. classes do.
	// movement will be translated into angle/velocity, not controlled by it.

	boolean up, down, left, right, space;
	int inv;

//	Sword s;
//	Gun g;

	public Player(int x, int y, int w, int h, Game g) {
		super(x, y, w, h, g);
		up = down = left = right = space = false;
		angle = 0;
		vx = vy = 0;

//		s = new Sword(x, y, 20, 30, this);
//		g = new Gun(x + w / 4, y - 5, 10, 10);
		health = 50;
		inv = 0;
	}

	public void render(Graphics2D g) {
		// s.render(g);
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

		if (!(vy == vx && vy == 0))
			angle = Math.atan2(vy, vx);
	}

	public void damage(int val) {
		if (inv == 0) {
			health -= val;
			inv = 1;
		} else
			inv = 0;
	}
}
