package game;

import java.awt.Graphics2D;
import java.awt.Image;

public class Background extends GameObject {
	Image image;

	public Background(Image i) {
		super(0, 0, 0, 0);
		image = i;
		hitbox.setRect(0, 0, image.getWidth(null), image.getHeight(null));
	}

	public void render(Graphics2D g) {
		g.drawImage(image, (int) (hitbox.getX() + Game.scrollX - 1), (int) (hitbox.getY() + Game.scrollY - 1),
				image.getWidth(null), image.getHeight(null), null);
	}
}
