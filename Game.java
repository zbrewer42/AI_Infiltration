import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	/**
	 * 
	 */
	Player p;
	Enemy e;
	ArrayList<Solid> solids = new ArrayList<Solid>();
	LinkedList<Bullet> bullets = new LinkedList<Bullet>();

	private static final long serialVersionUID = 1L;
	// resolution goal is 240x160, temp is 720x480
	static final Rectangle2D borders = new Rectangle2D.Float(0, 0, 720, 480);
	private Thread thread;
	private boolean running = false;

	public Game() {
		new Window(getW() + 16, getH() + 39, "Game", this);
		p = new Player(this);
		e = new Enemy(200, 200, 10, 10, this);

		this.addKeyListener(new KeyInput(p));
	}

	public static int getW() {
		return (int) borders.getMaxX();
	}

	public static int getH() {
		return (int) borders.getMaxY();
	}

	public synchronized void start() {
		ArrayList<Point2D> tempPoints = new ArrayList<Point2D>();
		tempPoints.add(new Point2D.Float(50, 50));
		tempPoints.add(new Point2D.Float(50, 100));
		tempPoints.add(new Point2D.Float(100, 100));
		tempPoints.add(new Point2D.Float(100, 50));
		solids.add(new Solid(tempPoints));

		tempPoints = new ArrayList<Point2D>();
		tempPoints.add(new Point2D.Float(150, 150));
		tempPoints.add(new Point2D.Float(150, 200));
		tempPoints.add(new Point2D.Float(200, 200));
		tempPoints.add(new Point2D.Float(200, 150));
		solids.add(new Solid(tempPoints));

		tempPoints = new ArrayList<Point2D>();
		tempPoints.add(new Point2D.Float(100, 100));
		tempPoints.add(new Point2D.Float(150, 150));
		solids.add(new Solid(tempPoints));

		tempPoints = new ArrayList<Point2D>();
		tempPoints.add(new Point2D.Float(300, 250));
		tempPoints.add(new Point2D.Float(200, 200));
		solids.add(new Solid(tempPoints));

		tempPoints = new ArrayList<Point2D>();
		tempPoints.add(new Point2D.Float(300, 250));
		tempPoints.add(new Point2D.Float(400, 500));
		solids.add(new Solid(tempPoints));

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
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fill(borders);
		p.render(g2d);
		e.render(g2d);
		for (Bullet b : bullets)
			b.render(g2d);
		for (Solid s : solids)
			s.render(g2d);
		g2d.dispose();

		bs.show();
		tick();
		try {
			Thread.sleep(2);
		} catch (Exception e) {
		}

	}

	public void tick() {
		Iterator<Bullet> it = bullets.iterator();
		while (it.hasNext()) {
			Bullet b = it.next();
			if (b.hitbox.intersects(p.hitbox) || !b.hitbox.intersects(borders)) {
				it.remove();
			} else {

			}
		}

		scroll();
	}

	public void scroll() {
		float topBorder = 240, bottomBorder = 240, leftBorder = 360, rightBorder = 360;
		if (p.hitbox.getCenterY() < topBorder) {
			float move = (float) (topBorder - p.hitbox.getY());
			p.scroll(0, move);
			e.scroll(0, move);
			for (Bullet b : bullets)
				b.scroll(0, move);
			for (Solid s : solids)
				s.scroll(0, move);
		}

		if (p.hitbox.getCenterY() > bottomBorder) {
			float move = (float) (bottomBorder - p.hitbox.getY());
			p.scroll(0, move);
			e.scroll(0, move);
			for (Bullet b : bullets)
				b.scroll(0, move);
			for (Solid s : solids)
				s.scroll(0, move);
		}

		if (p.hitbox.getCenterX() < leftBorder) {
			float move = (float) (leftBorder - p.hitbox.getX());
			p.scroll(move, 0);
			e.scroll(move, 0);
			for (Bullet b : bullets)
				b.scroll(move, 0);
			for (Solid s : solids)
				s.scroll(move, 0);
		}

		if (p.hitbox.getCenterX() > rightBorder) {
			float move = (float) (rightBorder - p.hitbox.getX());
			p.scroll(move, 0);
			e.scroll(move, 0);
			for (Bullet b : bullets)
				b.scroll(move, 0);
			for (Solid s : solids)
				s.scroll(move, 0);
		}
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

		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		frame.setVisible(true);
		game.start();
	}
}
