package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Bullet extends Weapon {
	float xv, yv;
	float speed = 1;
	Character shooter;

	public Bullet(int x, int y, double angle) {
		super(x, y, 5, 5);
		yv = (float) (speed * Math.sin(angle));
		xv = (float) (speed * Math.cos(angle));
	}

	public Bullet(Character s, int x, int y, double angle) {
		super(x, y, 5, 5);
		yv = (float) (speed * Math.sin(angle));
		xv = (float) (speed * Math.cos(angle));
		shooter = s;
	}

	public void reset(int x, int y) {
		hitbox.setRect(x, y, hitbox.getWidth(), hitbox.getHeight());
	}

	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fill(hitbox);
		tick();
	}

	public void tick() {
		hitbox = new Rectangle2D.Float((float) hitbox.getX() + xv, (float) hitbox.getY() + yv,
				(float) hitbox.getWidth(), (float) hitbox.getHeight());
	}
}