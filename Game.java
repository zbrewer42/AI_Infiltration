package Game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
	/**
	 * 
	 */
	static Player p = new Player();
	static Block b = new Block();
	// static Sword s = new Sword(p.x, p.y, 200, 200);
	static Enemy e = new Enemy(100, 100, 30, 30);
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 720, HEIGHT = 480;
	private Thread thread;
	private boolean running = false;

	public Game() {
		new Window(WIDTH, HEIGHT, "Game", this);
		this.addKeyListener(new KeyInput(p));
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
		e.render(g);
		for (Bullet b : p.g.bList) {
			b.render(g);
		}
		g.dispose();

		bs.show();

		try {
			Thread.sleep(2);
		} catch (Exception e) {
		}

	}

	public void tick() {

	}

	public static void main(String args[]) {
		new Game();
	}
}
