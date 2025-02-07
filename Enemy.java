package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Enemy extends Character {
	int type;
	boolean alive;
	int[][] pathfinding;

	public Enemy(int x, int y, int w, int h, Game g) {
		super(x, y, w, h, g);
		type = 1;
		alive = true;
		cooldown = 0;
		vx = vy = 0;
		sprites = new Image[32][5];
		try {
			sprites = Game.generateSprites(sprites,
					ImageIO.read(getClass().getResourceAsStream("/game/spriteSheets/test.png")), 16, 20);
		} catch (IOException e) {
			e.printStackTrace();
		}
		pathfinding = Arrays.copyOf(g.walkable, g.walkable.length);
	}

	public Enemy(int x, int y, int w, int h, int t, Game g) {
		super(x, y, w, h, g);
		type = t;
		alive = true;
		vx = vy = 0;
		try {
			sprites = Game.generateSprites(sprites, ImageIO.read(getClass().getResource("/game/spriteSheets/Test.png")),
					16, 20);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics2D g) {
		if (alive) {
			g.drawImage(sprites[d.dir][a.val], (int) (hitbox.getX() + Game.scrollX),
					(int) (hitbox.getY() + Game.scrollY), W, H, null);
			g.setColor(Color.BLUE);
			// g.draw(hitbox);

			// if (hitbox.intersects(game.screen))
			if (onScreen())
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
			hitbox = new Rectangle2D.Double(hitbox.getWidth(), hitbox.getHeight(), Integer.MAX_VALUE,
					Integer.MAX_VALUE);
		}
	}

	public void attack() {
		shoot(30);
		cooldown--;
	}

	public void trigMove() {
		vy = Math.sin(angle);
		vx = Math.cos(angle);
		super.trigMove(vx, vy);
	}

	public void findPath() {

	}

	public void tick() {
		aimAt(game.p);
		trigMove();
		attack();
		getDirection();
	}
}