package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

public class Enemy extends Character {
	int type;
	boolean alive;
	LinkedList<Bullet> bList;
	Bullet bull;
	int d = 0;
	float xv, yv;

	public Enemy(int x, int y, int w, int h, Game g) {
		super(x, y, w, h, g);
		type = 1;
		alive = true;
		bList = new LinkedList<Bullet>();
		cooldown = 0;
		bull = new Bullet(x, y, 0, 0);
		xv = yv = 0;
	}

	public Enemy(int x, int y, int w, int h, int t, Game g) {
		super(x, y, w, h, g);
		type = t;
		alive = true;
		xv = yv = 0;
	}

	public void render(Graphics2D g) {
		if (alive) {
			g.setColor(Color.BLUE);
			g.fill(hitbox);
			for (Bullet b : bList) {
				b.render(g);
			}
			if (hitbox.intersects(Game.borders))
				tick();

			g.draw(new Line2D.Double(hitbox.getCenterX(), hitbox.getCenterY(), hitbox.getCenterX() + xv * 20,
					hitbox.getCenterY() + yv * 20));
		}
		damage();

	}

	public void damage() {

//		if (game.p.s.hitE(this)) {
//			alive = false;
//		}
		if (this.hitbox.intersects(game.p.hitbox)) {
			game.p.damage(1);
			if (game.p.hitbox.getCenterX() < hitbox.getCenterX()) {
				game.p.trigMove(-5, 0);
			} else {
				game.p.trigMove(5, 0);
			}
			if (game.p.hitbox.getCenterY() < hitbox.getCenterY()) {
				game.p.trigMove(0, -5);
			} else {
				game.p.trigMove(0, 5);
			}
		}
		if (!alive) {
			hitbox = new Rectangle2D.Float((float) hitbox.getWidth(), (float) hitbox.getHeight(), Integer.MAX_VALUE,
					Integer.MAX_VALUE);
		}
	}

	public void attack() {
		if (cooldown == 0) {
			game.bullets.add(new Bullet((int) hitbox.getCenterX(), (int) hitbox.getCenterY(), xv, yv, angle));
			cooldown = 250;
		} else
			cooldown--;
	}

	public void trigMove() {
		yv = (float) (0.25 * Math.sin(angle));
		xv = (float) (0.25 * Math.cos(angle));
		super.trigMove(xv, yv);
	}

//	public int findX(Player p) {
//		return x - p.x;
//	}

//	public int findY(Player p) {
//		return y - p.y;
//	}

	public void tick() {
		aimAt(game.p);
		trigMove();
		attack();
	}

	public void scroll(float mx, float my) {
		super.scroll(mx, my);
		for (Bullet b : bList)
			b.hitbox.setRect(b.hitbox.getMinX() + mx, b.hitbox.getMinY() + my, b.hitbox.getWidth(),
					b.hitbox.getHeight());
	}
}
