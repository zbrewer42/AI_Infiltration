package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Gun extends Weapon {
	ArrayList<Bullet> bList = new ArrayList<Bullet>();
	int cooldown = 0;
	int direction = 1; // 1 = north, 2 = south, 3 = east, 4 = west

	public Gun(int ex, int why, int wid, int hi) {
		super(ex, why, wid, hi);
		// TODO Auto-generated constructor stub
		selected = false;
		for (int i = 1; i <= 3; i++) {
			bList.add(new Bullet(x, y));
		}
	}

	public void render(Graphics g) {
		if (selected) {
			g.setColor(Color.CYAN);
			g.fillRect(x, y, w, h);
		}
		tick();
	}

	public void tick() {

		if (selected) {
			if (Game.p.lastMove.equals("north")) {

				x = Game.p.x + Game.p.w / 3;
				y = Game.p.y - h;
				direction = 1;
			} else if (Game.p.lastMove.equals("south")) {

				x = Game.p.x + Game.p.w / 3;
				y = Game.p.y + Game.p.h;
				direction = 2;
			} else if (Game.p.lastMove.equals("east")) {

				x = Game.p.x + Game.p.w;
				y = Game.p.y + Game.p.h / 3;
				direction = 3;
			} else if (Game.p.lastMove.equals("west")) {

				x = Game.p.x - w;
				y = Game.p.y + Game.p.h / 3;
				direction = 4;
			}
			fire();
		}
	}

	public void fire() {
		if (Game.p.space) {
			bList.add(0, new Bullet(x, y));
			bList.get(0).tick();
			if (bList.get(bList.size() - 1).hitE(Game.e)) {
				Game.e.alive = false;
			}
		} else {
			bList.removeAll(bList);
		}

	}

}
