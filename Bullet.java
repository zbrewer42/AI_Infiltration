package NewGame;
import java.awt.*;
public class Bullet extends Weapon{
	int dir = 0; //1 is up, 2 is down, 3 is right, 4 is left
	public Bullet(int ex, int why, int d) {
		super(ex, why, 5, 5);
		dir = d;
		dx = 2;
		
		
	}
	public Bullet(int ex, int why) {
		super(ex, why, 10, 10);
		dx= 2;
		dir = 0;
	}
	public void reset(int ex, int why) {x = ex; y = why;}
	public void render(Graphics g) {
		
		g.setColor(Color.WHITE);
		g.fillOval(x, y, w, h);
		tick();
	}
	public void tick() {
		if(dir == 1)
			y -= 2;
		if(dir == 2)
			y += 2;	
		if(dir == 3)
			x += 2;
		if(dir == 4)
			x -= 2;
		//else {x += dx; y+= dy;}	
	}
	
	public boolean touches(Player p) {
		//right border
		if(x+w == p.x && y > p.y && y < p.y+p.h)
			return true;
		//left border
		else if(x == p.x+w && y > p.y && y < p.y+p.h) 
			return true;
		//top border
		else if(y == p.y+p.h && x > p.x && x < p.x+w)
			return true;
		//bottom border
		else if(y+h == p.y && x > p.x && x < p.x+w)
			return true;
		return false;
		
	}
	
	
	}
