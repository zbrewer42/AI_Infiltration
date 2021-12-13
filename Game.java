package NewGame;

import java.awt.*;
import javax.swing.JFrame;
import java.awt.image.BufferStrategy;
import java.util.*;

public class Game extends Canvas implements Runnable {
	/**
	 * 
	 */
	static Player p = new Player();
	static Block b = new Block();
	//static Sword s = new Sword(p.x, p.y, 200, 200);
	static ArrayList<Enemy> eList = new ArrayList<>();
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 720, HEIGHT = 480;
	private Thread thread;
	private boolean running = false;

	public Game() {
		new Window(WIDTH, HEIGHT, "Game", this);
		this.addKeyListener(new KeyInput());
	}

	public static int getW() {
		return WIDTH;
	}

	public static int getH() {
		return HEIGHT;
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running)
				render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}

		}
		stop();
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		p.render(g);
		p.s.render(g);
		p.g.render(g);
		b.render(g);
		for(Enemy e : eList) {
			e.render(g);
		}
		for(Bullet b : p.g.bList) {
			b.render(g);
		}
		g.dispose();

		bs.show();
		tick();
		try {Thread.sleep(2);} catch (Exception e) {}

	}

	public void tick() {
		if(Math.random() < .001) spawnE();
		if(p.health == 0) {
			System.out.println("You Lose");
			stop();}
	}
	
	public void spawnE() {
		int x = (int)(Math.random() * WIDTH)+1;
		int y = (int)(Math.random() * HEIGHT)+1;
		eList.add(new Enemy(x, y, 30, 30));
	}
	
	public static void main(String args[]) {
		new Game();
	}
}

class Window extends Canvas {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Window(int w, int h, String t, Game game) {
		JFrame frame = new JFrame(t);
		frame.setPreferredSize(new Dimension(w, h));
		frame.setMinimumSize(new Dimension(w, h));
		frame.setMaximumSize(new Dimension(w, h));

		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		frame.setVisible(true);
		game.start();
	}
}

