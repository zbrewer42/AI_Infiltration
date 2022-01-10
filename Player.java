package NewGame;

import java.awt.Color;
import java.awt.Graphics;

class Player extends GameObject {
	int health;
	boolean north, south, east, west, space;
	//ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	String lastMove;
	int inv;
	Sword s;
	Gun g;

	public Player() {
		super(Game.getW() / 2, Game.getH() / 2, 20, 20);
		w = 20;
		north = south = east = west = space = false;
		lastMove = "north";
		s = new Sword(x, y, 20, 30);
		g = new Gun(x + w / 4, y - 5, 10, 10);
		health = 50;
		inv = 0;
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
		for(Block b : Game.blocks) {
			collideBlock(b);
		}

		updateMovement();
		
		

		x += dx;
		y += dy;
		//System.out.println(health);
	}
	
	public void damage(int val) {
		if(inv == 0) { health -= val; inv = 1;}
		else inv = 0;
	}
	public boolean checkDoor() {
		if (this.collideN(Game.d)) {
			north = false;
			return true;}
		if (this.collideS(Game.d)) {
			south = false;
			return true;}
		if (this.collideE(Game.d)) {
			east = false;
			return true;}
		if (this.collideW(Game.d)) {
			west = false;
			return true;}
		return false;
	}
	
	public void updateMovement() {
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
	}
	
	public void collideBlock(Block b) {
		if (this.collideN(b))
			north = false;
		if (this.collideS(b))
			south = false;
		if (this.collideE(b))
			east = false;
		if (this.collideW(b))
			west = false;
	}
	
}
