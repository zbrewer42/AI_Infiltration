package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Enemy extends Character {
	int type;
	boolean alive;
	LinkedList<Bullet> bList;
	float xv, yv;

	public Enemy(int x, int y, int w, int h, Game g) {
		super(x, y, w, h, g);
		type = 1;
		alive = true;
		bList = new LinkedList<Bullet>();
		cooldown = 0;
		xv = yv = 0;
		sprites = new Image[32][5];
		try {
			sprites = Game.generateSprites(sprites,
					ImageIO.read(getClass().getResourceAsStream("/game/spriteSheets/test.png")), 16, 20);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Enemy(int x, int y, int w, int h, int t, Game g) {
		super(x, y, w, h, g);
		type = t;
		alive = true;
		xv = yv = 0;
		try {
			sprites = Game.generateSprites(sprites, ImageIO.read(getClass().getResource("/game/spriteSheets/Test.png")),
					16, 20);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics2D g) {
		if (alive) {
			g.drawImage(sprites[d.dir][a.val], (int) hitbox.getX(), (int) hitbox.getY(), (int) hitbox.getX() + W,
					(int) hitbox.getY() + H, 0, 0, 16, 20, null);
			g.setColor(Color.BLUE);
			g.draw(hitbox);

			for (Bullet b : bList) {
				b.render(g);
			}
			if (hitbox.intersects(Game.borders))
				tick();
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
		shoot(250);
		cooldown--;
	}

	public void trigMove() {
		yv = (float) (0.25 * Math.sin(angle));
		xv = (float) (0.25 * Math.cos(angle));
		super.trigMove(xv, yv);
	}

	public void tick() {
		aimAt(game.p);
		trigMove();
		attack();
		getDirection();
	}

	public void scroll(float mx, float my) {
		super.scroll(mx, my);
		for (Bullet b : bList)
			b.hitbox.setRect(b.hitbox.getMinX() + mx, b.hitbox.getMinY() + my, b.hitbox.getWidth(),
					b.hitbox.getHeight());
	}
}