package game;

import java.awt.Color;
//import java.awt.geom.Line2D;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Sword extends Weapon {
	int cooldown;
	Player player;
	boolean selected;
	boolean up, down, left, right;
	int dir;
	String direction;
	int length, width;

	public Sword(int x, int y, int l, int w, Player p) {
		super(x, y, 0, 0);
		selected = true;
		length = l;
		width = w;
		player = p;
		dir = 0;
	}

	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		if (player.space && player.cooldown > 30) {
			switch (player.d) {
			case down:
				hitbox = new Rectangle2D.Double(player.hitbox.getCenterX() - width / 2, player.hitbox.getMaxY(), width,
						length);
				break;
			case downleft:
				hitbox = new Rectangle2D.Double(player.hitbox.getX() - width, player.hitbox.getMaxY(), width, length);
				break;
			case downright:
				hitbox = new Rectangle2D.Double(player.hitbox.getMaxX(), player.hitbox.getMaxY(), width, length);
				break;
			case left:
				hitbox = new Rectangle2D.Double(player.hitbox.getX() - length, player.hitbox.getCenterY() - width / 2,
						length, width);
				break;
			case right:
				hitbox = new Rectangle2D.Double(player.hitbox.getMaxX(), player.hitbox.getCenterY() - width / 2, length,
						width);
				break;
			case up:
				hitbox = new Rectangle2D.Double(player.hitbox.getCenterX() - width / 2, player.hitbox.getY() - length,
						width, length);
				break;
			case upleft:
				hitbox = new Rectangle2D.Double(player.hitbox.getX() - width, player.hitbox.getY() - length, width,
						length);
				break;
			case upright:
				hitbox = new Rectangle2D.Double(player.hitbox.getMaxX(), player.hitbox.getY() - length, width, length);
				break;
			default:
				hitbox = new Rectangle2D.Double(player.hitbox.getCenterX(), player.hitbox.getCenterY(), 0, 0);
				break;
			}

//			if (player.up)
//
//				if (player.down)
//					hitbox = (new Rectangle2D.Double(player.hitbox.getX(), player.hitbox.getY() + 11, 11, 15));
//			if (player.right)
//				hitbox = (new Rectangle2D.Double(player.hitbox.getX() + 11, player.hitbox.getY(), 15, 11));
//			if (player.left)
//				hitbox = (new Rectangle2D.Double(player.hitbox.getX() - 15, player.hitbox.getY(), 15, 11));
//			if (player.up && player.left) {
//				hitbox.setFrameFromDiagonal(player.hitbox.getX(), player.hitbox.getY(), player.hitbox.getX() - 12,
//						player.hitbox.getY() - 12);
//			}
//			if (player.up && player.right) {
//				hitbox.setFrameFromDiagonal(player.hitbox.getX() + 11, player.hitbox.getY(), player.hitbox.getX() + 23,
//						player.hitbox.getY() - 12);
//			}
//			if (player.down && player.left) {
//				hitbox.setFrameFromDiagonal(player.hitbox.getX(), player.hitbox.getY() + 11, player.hitbox.getX() - 12,
//						player.hitbox.getY() + 23);
//			}
//			if (player.down && player.right) {
//				hitbox.setFrameFromDiagonal(player.hitbox.getX() + 11, player.hitbox.getY() + 11,
//						player.hitbox.getX() + 23, player.hitbox.getY() + 23);
//			}
//
//		} else
//			hitbox = new Rectangle2D.Double(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
		} else {
			hitbox = new Rectangle2D.Double(player.hitbox.getCenterX(), player.hitbox.getCenterY(), 0, 0);
		}
		g.fill(hitbox);

		tick();

		// System.out.println(x + " " + y + " " +w + " " + h);
	}

	public void tick() {
		for (Enemy e : player.game.enemies) {
			if (hitE(e)) {
				e.alive = false;
			}
		}
	}

//	public void rotateH() {
//	if (w < h) {
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
//
}