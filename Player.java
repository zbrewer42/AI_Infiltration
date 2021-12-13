package Game;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends GameObject {
	boolean north, south, east, west, space;
	// ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	String lastMove;
	Sword s;
	Gun g;

	public Player() {
		super(Game.getW() / 2, Game.getH() / 2, 20, 20);
		w = 20;
		north = south = east = west = space = false;
		lastMove = "north";
		s = new Sword(x, y, 20, 20);
		g = new Gun(x + w / 4, y - 5, 10, 10);
	}

	public void render(Graphics g) {

		g.setColor(new Color(255, 255, 255));
		g.fillOval(x, y, w, h);
		tick();

		// s.render(g);

	}

	public void checkBorders() {
		if (x > Game.getW()) {
			east = false;
		}
		if (x < 0)
			west = false;
		if (y > Game.getH())
			south = false;
		if (y < 0)
			north = false;
	}

	public void tick() {
		if (this.collideN(Game.b))
			north = false;
		if (this.collideS(Game.b))
			south = false;
		if (this.collideE(Game.b))
			east = false;
		if (this.collideW(Game.b))
			west = false;

		if (north) {
			dy = -1;
			lastMove = "north";
		} else if (south) {
			dy = 1;
			lastMove = "south";
		} else
			dy = 0;
		if (east) {
			dx = 1;
			lastMove = "east";
		} else if (west) {
			dx = -1;
			lastMove = "west";
		} else
			dx = 0;

		x += dx;
		y += dy;
	}

}