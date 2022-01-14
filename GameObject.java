import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class GameObject {
	Rectangle2D hitbox;
	float velocity;
	double angle = 0; // angles are in radians

	public GameObject(int x, int y, int w, int h) {
		hitbox = new Rectangle2D.Float(x, y, w, h);
	}

	public Line2D collide(Solid s) {
		for (Line2D l : s.lines) {
			if (l.intersects(hitbox)) {
				return l;
			}
		}
		return null;
	}

	public void tick() {

	}

	public void scroll(float mx, float my) {
		hitbox.setRect(hitbox.getMinX() + mx, hitbox.getMinY() + my, hitbox.getWidth(), hitbox.getHeight());
	}
}
