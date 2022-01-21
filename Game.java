package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	/**
	 * 
	 */
	Player p;
	ArrayList<Enemy> enemies;
	Background bg;
	Door d;
	ArrayList<Solid> solids = new ArrayList<Solid>();
	LinkedList<Bullet> bullets = new LinkedList<Bullet>();
	int cooldown = 500;
	private static final long serialVersionUID = 1L;
	// resolution goal is 240x160, temp is 720x480
	static final Rectangle2D borders = new Rectangle2D.Float(0, 0, 720, 480);
	private Thread thread;
	private boolean running = false;

	public Game() {
		p = new Player(357, 1565, 16, 20, this);
		d = new Door(200, 100, 100, 100, this);
		enemies = new ArrayList<Enemy>();
		enemies.add(new Enemy(300, 1000, 11, 11, this));
		try {
			bg = new Background(ImageIO.read(getClass().getResource("/game/backgrounds/Tutorial.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		new Window(getW() + 16, getH() + 39, "Game", this);
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
		tempPoints.add(new Point2D.Float(302, 36));
		tempPoints.add(new Point2D.Float(417, 36));
		tempPoints.add(new Point2D.Float(453, 72));
		tempPoints.add(new Point2D.Float(453, 224));
		tempPoints.add(new Point2D.Float(648, 419));
		tempPoints.add(new Point2D.Float(648, 540));
		tempPoints.add(new Point2D.Float(408, 780));
		tempPoints.add(new Point2D.Float(408, 834));
		tempPoints.add(new Point2D.Float(445, 871));
		tempPoints.add(new Point2D.Float(610, 871));
		tempPoints.add(new Point2D.Float(648, 909));
		tempPoints.add(new Point2D.Float(648, 1170));
		tempPoints.add(new Point2D.Float(610, 1208));
		tempPoints.add(new Point2D.Float(445, 1208));
		tempPoints.add(new Point2D.Float(408, 1245));
		tempPoints.add(new Point2D.Float(408, 1447));
		tempPoints.add(new Point2D.Float(379, 1476));
		tempPoints.add(new Point2D.Float(379, 1539));
		tempPoints.add(new Point2D.Float(373, 1545));
		tempPoints.add(new Point2D.Float(373, 1549));
		tempPoints.add(new Point2D.Float(381, 1557));
		tempPoints.add(new Point2D.Float(381, 1579));
		tempPoints.add(new Point2D.Float(372, 1588));
		tempPoints.add(new Point2D.Float(347, 1588));
		tempPoints.add(new Point2D.Float(338, 1579));
		tempPoints.add(new Point2D.Float(338, 1557));
		tempPoints.add(new Point2D.Float(346, 1549));
		tempPoints.add(new Point2D.Float(346, 1545));
		tempPoints.add(new Point2D.Float(340, 1539));
		tempPoints.add(new Point2D.Float(340, 1476));
		tempPoints.add(new Point2D.Float(311, 1447));
		tempPoints.add(new Point2D.Float(311, 1245));
		tempPoints.add(new Point2D.Float(274, 1208));
		tempPoints.add(new Point2D.Float(109, 1208));
		tempPoints.add(new Point2D.Float(71, 1170));
		tempPoints.add(new Point2D.Float(71, 909));
		tempPoints.add(new Point2D.Float(109, 871));
		tempPoints.add(new Point2D.Float(274, 871));
		tempPoints.add(new Point2D.Float(311, 834));
		tempPoints.add(new Point2D.Float(311, 780));
		tempPoints.add(new Point2D.Float(71, 540));
		tempPoints.add(new Point2D.Float(71, 419));
		tempPoints.add(new Point2D.Float(266, 224));
		tempPoints.add(new Point2D.Float(266, 72));
		solids.add(new Solid(tempPoints));

		tempPoints = new ArrayList<Point2D>();
		tempPoints.add(new Point2D.Float(279, 959));
		tempPoints.add(new Point2D.Float(440, 959));
		tempPoints.add(new Point2D.Float(440, 1120));
		tempPoints.add(new Point2D.Float(279, 1120));
		solids.add(new Solid(tempPoints));

		tempPoints = new ArrayList<Point2D>();
		tempPoints.add(new Point2D.Float(359, 269));
		tempPoints.add(new Point2D.Float(360, 269));
		tempPoints.add(new Point2D.Float(551, 460));
		tempPoints.add(new Point2D.Float(551, 499));
		tempPoints.add(new Point2D.Float(360, 690));
		tempPoints.add(new Point2D.Float(359, 690));
		tempPoints.add(new Point2D.Float(168, 499));
		tempPoints.add(new Point2D.Float(168, 460));
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
		bg.render(g2d);
		p.render(g2d);
		d.render(g2d);
		for (Enemy e : enemies)
			e.render(g2d);
		for (Bullet b : bullets)
			b.render(g2d);
//		for (Solid s : solids)
//			s.render(g2d);
		
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
			if (b.shooter != p && b.hitbox.intersects(p.hitbox)) {
				it.remove();
				p.damage(1);
				System.out.println(p.health);
			} else if (!b.hitbox.intersects(borders) || b.collide(solids).size() > 0) {
				it.remove();
			}
		}

		scroll();
	}

	public void scroll() {
		float topBorder = 240, bottomBorder = 240, leftBorder = 360, rightBorder = 360, movex = 0, movey = 0;
		if (p.hitbox.getCenterY() < topBorder) {
			movey = (float) (topBorder - p.hitbox.getY());
		} else if (p.hitbox.getCenterY() > bottomBorder) {
			movey = (float) (bottomBorder - p.hitbox.getY());
		}

		if (p.hitbox.getCenterX() < leftBorder) {
			movex = (float) (leftBorder - p.hitbox.getX());
		} else if (p.hitbox.getCenterX() > rightBorder) {
			movex = (float) (rightBorder - p.hitbox.getX());
		}
			scrollAll(movex, movey);
		
	}
	
	public void scrollAll(float movex, float movey) {
		bg.scroll(movex, movey);
		p.scroll(movex, movey);
		for (Enemy e : enemies)
			e.scroll(movex, movey);
		for (Bullet b : bullets)
			b.scroll(movex, movey);
		for (Solid s : solids)
			s.scroll(movex, movey);
	}
	public void spawnE() {
		if(cooldown == 0) {
			enemies.add(new Enemy(100, 100, 11, 11, this));
			cooldown = 200;
		}
		else cooldown--;
	}
	public static Image[][] generateSprites(Image[][] s, BufferedImage spriteSheet, int w, int h) {
		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s[i].length; j++) {
				s[i][j] = spriteSheet.getSubimage(i * w, j * h, w, h);
			}
		}
		return s;
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
