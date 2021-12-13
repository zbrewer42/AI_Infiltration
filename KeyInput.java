package NewGame;

import java.awt.event.*;
public class KeyInput extends KeyAdapter {
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		//System.out.println();
		
		if(key == KeyEvent.VK_UP) {
	
			Game.p.north = true;
			System.out.println("north");
		}
		if(key == KeyEvent.VK_DOWN) {
			System.out.println("South");
			Game.p.south = true;}
		if(key == KeyEvent.VK_RIGHT) {
			System.out.println("east");
			Game.p.east = true;}
		if(key == KeyEvent.VK_LEFT) {
			System.out.println("west");
			Game.p.west = true;}
		if(key == KeyEvent.VK_SPACE) {
			Game.p.space = true;}
			if(key == KeyEvent.VK_1) {
				Game.p.s.selected = true;
				System.out.println("Sword is selected");
				
		}
			if(key == KeyEvent.VK_2) {
				Game.p.g.selected = true;
				Game.p.s.selected = false;
				System.out.println("Gun is selected");
				
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP)
			Game.p.north = false;
		if(key == KeyEvent.VK_DOWN)
			Game.p.south = false;
		if(key == KeyEvent.VK_RIGHT)
			Game.p.east = false;
		if(key == KeyEvent.VK_LEFT)
			Game.p.west = false;
		if(key == KeyEvent.VK_SPACE)
			Game.p.space = false;
	}
}
