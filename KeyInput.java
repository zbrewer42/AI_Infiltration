import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	Player player;

	public KeyInput(Player p) {
		player = p;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		// System.out.println();

		if (key == KeyEvent.VK_UP) {
			player.up = true;
		}
		if (key == KeyEvent.VK_DOWN) {
			player.down = true;
		}
		if (key == KeyEvent.VK_RIGHT) {
			player.right = true;
		}
		if (key == KeyEvent.VK_LEFT) {
			player.left = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP)
			player.up = false;
		if (key == KeyEvent.VK_DOWN)
			player.down = false;
		if (key == KeyEvent.VK_RIGHT)
			player.right = false;
		if (key == KeyEvent.VK_LEFT)
			player.left = false;
	}
}
