package Game;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy extends GameObject {
	int type;
	boolean alive;

	public Enemy(int ex, int why, int wid, int hi) {
		super(ex, why, wid, hi);
		type = 1;
		alive = true;

	}

	public Enemy(int ex, int why, int wid, int hi, int t) {
		super(ex, why, wid, hi);
		type = t;
		alive = true;

	}

	public void render(Graphics g) {
		if (alive) {
			g.setColor(Color.BLUE);
			g.fillOval(x, y, w, h);
			tick();
		}
		if (Game.p.s.hitE(this)) {
			alive = false;
		}
	}

}
