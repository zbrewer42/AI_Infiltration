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
	

	public Sword(int x, int y, int w, int h, Player p) {
		super(x, y, w, h);
		selected = true;
		player = p;
		dir = 0;
		}
		

	public void render(Graphics2D g) {
			g.setColor(Color.RED);
				if(player.space) {
					if(player.up)
						hitbox = new Rectangle2D.Double(player.hitbox.getX(), player.hitbox.getY()-15, 11, 15);
						
					if(player.down)
						hitbox = (new Rectangle2D.Double(player.hitbox.getX(), player.hitbox.getY()+11, 11, 15));
					if(player.right)
						hitbox = (new Rectangle2D.Double(player.hitbox.getX()+11, player.hitbox.getY(), 15, 11));
					if(player.left)
						hitbox = (new Rectangle2D.Double(player.hitbox.getX()-15, player.hitbox.getY(), 15, 11));
					if(player.up && player.left) {
						hitbox.setFrameFromDiagonal(player.hitbox.getX(), player.hitbox.getY(), player.hitbox.getX()-12, player.hitbox.getY()-12);
					}
					if(player.up && player.right) {
						hitbox.setFrameFromDiagonal(player.hitbox.getX()+11, player.hitbox.getY(), player.hitbox.getX()+23, player.hitbox.getY()-12);
					}
					if(player.down && player.left) {
						hitbox.setFrameFromDiagonal(player.hitbox.getX(), player.hitbox.getY()+11, player.hitbox.getX()-12, player.hitbox.getY()+23);
					}
					if(player.down && player.right) {
						hitbox.setFrameFromDiagonal(player.hitbox.getX()+11, player.hitbox.getY()+11, player.hitbox.getX()+23, player.hitbox.getY()+23);
					}
					
					
					
					
				}
				else hitbox = new Rectangle2D.Double(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
				
				g.fill(hitbox);
				
		tick();
		//System.out.println(x + " " + y + " " +w + " " + h);
	}

	public void tick() {
		if (selected) {
			if (Math.toDegrees(player.angle) >= -90 - 45 && Math.toDegrees(player.angle) <= -90 + 45) {
				System.out.print("up");// dir = 1;
				
			}
			if (Math.toDegrees(player.angle) >= 0 - 45 && Math.toDegrees(player.angle) <= 0 + 45) {
				System.out.print("right");//dir = 3;
				}
			if (Math.toDegrees(player.angle) >= 90 - 45 && Math.toDegrees(player.angle) <= 90 + 45) {
				System.out.print("down"); //dir = 2;
				}
			if (Math.toDegrees(player.angle) >= 180 - 45 || Math.toDegrees(player.angle) <= -180 + 45) {
				System.out.print("left"); //dir = 4;
				}
			System.out.println();
			for(Enemy e : Game.enemies) {
				if(hitE(e)) {
					e.alive = false;
				}
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
