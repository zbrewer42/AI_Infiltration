package NewGame;
import java.awt.*;
public class Bullet extends Weapon{
	int dir = 0;
	public Bullet(int ex, int why, int d) {
		super(ex, why, 5, 5);
		dir = d;
		dx = 2;
		
		
	}
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
	}
	
	
	}
