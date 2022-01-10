package NewGame;

import java.awt.*;
import java.awt.Graphics;
import java.util.*;

public class Enemy extends GameObject {
	int type;
	boolean alive;
	ArrayList <Bullet> bList;
	Bullet bull;
	int cooldown;
	int d = 0;
	public Enemy(int ex, int why, int wid, int hi) {
		super(ex, why, wid, hi);
		type = 1;
		alive = true;
		bList = new ArrayList<Bullet>();
		cooldown = 0; 
		bull = new Bullet(x, y);
		
	}
	public Enemy(int ex, int why, int wid, int hi, int t) {
		super(ex, why, wid, hi);
		type = t;
		alive = true;
		
	}
	public void render(Graphics g) {
		if(alive) {
			g.setColor(Color.BLUE);
			g.fillOval(x, y, w, h);
			if(x > 0 && x < Game.getW() && y > 0 && y < Game.getH())
			tick();
			
		}
		damage();
		
		
	}
	public void damage() {
		
		if(Game.p.s.hitE(this)) {alive = false;}
		if(this.collide(Game.p)) {
			Game.p.damage(1);
			if(Game.p.x < x) {
				Game.p.x-=5;}
			else {Game.p.x+= 5;}
			if(Game.p.y < y) {
				Game.p.y-=5;}
			else {Game.p.y+= 5;}
		}
		if(!alive) {x = y = 2000000;}
	}
	
	public void attack() {
		if(cooldown == 0) {
			bList.add(0, new Bullet(x,y));
			bList.get(0).dir = findDir();
			bList.get(0).tick();
			if((bList.get(0).x < 0) || (bList.get(0).y > Game.getW()) || (bList.get(0).y < 0) || (bList.get(0).y > Game.getH())) {
				bList.remove(0);
			}
		cooldown = 250;	
		}
		else cooldown--;
		for(Bullet b : bList) {
			if(b.touches(Game.p)) {
				Game.p.health -= 10;
			}
		}
	}
	
	
	public void move() {
	int m = (int)(Math.random() *10);
	if(m == 1) {x +=3;}
	else if(m == 2) {x -= 3;}
	else if(m == 3) {y += 3;}
	else if (m == 4) {y -= 3;}
	
	}
	
	public int findDir() {
		int x = findX(Game.p);
		int y = findY(Game.p);
		
		if (Math.abs(x) > Math.abs(y)) {
			if(x < 0)return 3;
			return 4;
		}
		if (Math.abs(x) < Math.abs(y)) {
			if(y < 0) return 2;
			return 1;
		}
		return 0;
	}
	
	public int findX(Player p) {
		return x - p.x;
	}
	public int findY(Player p) {
		return y - p.y;
	}
	
	public void tick() {
		//int[] slope = findSlope();
		//x += slope[2];
		//y+= slope[2];
		attack();
		//move();
		
	}
	

}
