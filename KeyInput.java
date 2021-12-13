package Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	Player player;

	public KeyInput(Player player) {
		this.player = player;
	}

	public void KeyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT)
			player.west = true;
		if (key == KeyEvent.VK_RIGHT)
			player.east = true;
		if (key == KeyEvent.VK_UP)
			player.north = true;
		if (key == KeyEvent.VK_DOWN)
			player.west = true;
	}
}
