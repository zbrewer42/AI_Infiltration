package game;

import java.awt.Color;
import java.awt.Graphics2D;

public class Sword extends Weapon {
	int cooldown;
	Player player;

	public Sword(int x, int y, int w, int h, Player p) {
		super(x, y, w, h);
		selected = true;
		player = p;
	}

	public void render(Graphics2D g) {
		if (player.space && selected) {
			g.setColor(Color.CYAN);
			g.draw(hitbox);
		}
		tick();

	}

	public void tick() {
		if (selected && player.space) {
			if (Math.toDegrees(player.angle) >= -90 - 45 && Math.toDegrees(player.angle) <= -90 + 45)
				System.out.print("up");
			if (Math.toDegrees(player.angle) >= 0 - 45 && Math.toDegrees(player.angle) <= 0 + 45)
				System.out.print("right");
			if (Math.toDegrees(player.angle) >= 90 - 45 && Math.toDegrees(player.angle) <= 90 + 45)
				System.out.print("down");
			if (Math.toDegrees(player.angle) >= 180 - 45 || Math.toDegrees(player.angle) <= -180 + 45)
				System.out.print("left");
			System.out.println();
		}

	}

//	public void rotateH() {
//		if (w < h) {
//			int temp = w;
//			w = h;
//			h = temp;
//		}
//
//	}
//
//	public void rotateV() {
//		if (w > h) {
//			int temp = w;
//			w = h;
//			h = temp;
//		}
//
//	}

}
