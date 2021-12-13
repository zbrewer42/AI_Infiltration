package Game;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Weapon {

	public Bullet(int ex, int why) {
		super(ex, why, 5, 5);
		dx = 2;

	}

	public void render(Graphics g) {

		g.setColor(Color.WHITE);
		g.fillOval(x, y, w, h);
		tick();
	}

	public void tick() {
		if (Game.p.g.direction == 1)
			y -= 2;
		if (Game.p.g.direction == 2)
			y += 2;
		if (Game.p.g.direction == 3)
			x += 2;
		if (Game.p.g.direction == 4)
			x -= 2;
	}

}
