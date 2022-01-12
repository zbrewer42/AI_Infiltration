import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Bullet extends Weapon {
	float xv, yv;
	float speed = 3;

	public Bullet(int x, int y, float xv, float yv, double a) {
		super(x, y, 5, 5);
		this.yv = yv * speed;
		this.xv = xv * speed;
		angle = a;
	}

	public Bullet(int x, int y, float xv, float yv) {
		super(x, y, 10, 10);
		this.yv = yv * speed;
		this.xv = xv * speed;
		angle = 0;
	}

	public void reset(int x, int y) {
		hitbox.setRect(x, y, hitbox.getWidth(), hitbox.getHeight());
	}

	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fill(hitbox);
		tick();
	}

	public void tick() {
		hitbox = new Rectangle2D.Float((float) hitbox.getX() + xv, (float) hitbox.getY() + yv,
				(float) hitbox.getWidth(), (float) hitbox.getHeight());
	}

	public boolean touches(Player p) {
		p.health -= 10;
		return hitbox.intersects(p.hitbox);
	}

}
