package Game;

import java.awt.Color;
import java.awt.Graphics;

public class Sword extends Weapon {
	int cooldown;

	public Sword(int ex, int why, int wid, int hi) {
		super(ex, why, wid, hi);
		selected = true;
		// TODO Auto-generated constructor stub
	}

	public void render(Graphics g) {
		if (Game.p.space && selected) {
			g.setColor(Color.CYAN);
			g.fillRect(x, y, w, h);
		}
		tick();

	}

	public void tick() {
		if (selected) {
			if (Game.p.lastMove.equals("north")) {
				if (Game.p.space) {
					x = Game.p.x;
					y = Game.p.y - h;
				}
			} else if (Game.p.lastMove.equals("south")) {
				if (Game.p.space) {
					x = Game.p.x;
					y = Game.p.y + Game.p.h;
				}
			} else if (Game.p.lastMove.equals("east")) {
				if (Game.p.space) {
					x = Game.p.x + Game.p.w;
					y = Game.p.y;
				}
			} else if (Game.p.lastMove.equals("west")) {
				if (Game.p.space) {
					x = Game.p.x - w;
					y = Game.p.y;
				}
			} else {
				x = y = 20000;
			}
		}

	}

}
