package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
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
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

enum GameState {
	MENU, RUNNING, GAME_OVER, INSTRUCTIONS, PAUSE, POWERUP1, POWERUP2, POWERUP3
}

public class Game extends Canvas implements Runnable {
	/**
	 * 
	 */

	GameState state = GameState.MENU;

	GameState prevState = GameState.GAME_OVER;

	Menu menu = new Menu(this);
	Font font;
	FontMetrics metrics;
	
	Player p;
	Door d;
	Door d2;
	int level;
	LinkedList<Enemy> enemies;
	Background bg;
	Background lv1;
	Background lv3;
	ArrayList<Solid> solids = new ArrayList<Solid>();
	ArrayList<Laser> lasers = new ArrayList<Laser>();

	LinkedList<Bullet> bullets = new LinkedList<Bullet>();

	int cooldown = 2000;

	private static final long serialVersionUID = 1L;
	// static final Dimension resolution = new Dimension(240, 160);
	static final Rectangle2D borders = new Rectangle2D.Float(0, 0, 240, 160);
	Dimension currentSize = getSize();
	double scaleX, scaleY;
	private Thread thread;
	boolean running = false;

	public Game() {
		font = new Font("courier new", 1, 50);

		p = new Player(360 - 8, 1560, 16, 20, this);
		enemies = new LinkedList<Enemy>();
		enemies.add(new Enemy(300, 900, 16, 20, this));
		
		level = 0;
		try {
			bg = new Background(ImageIO.read(getClass().getResourceAsStream("/game/backgrounds/Tutorial.png")),0,0);
			lv1 = new Background(ImageIO.read(getClass().getResourceAsStream("/game/backgrounds/lv3.png")), 1000, 0);
			lv3 = new Background(ImageIO.read(getClass().getResourceAsStream("/game/backgrounds/lv3.png")), 2000, 0);

			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		d = new Door((int)(bg.hitbox.getCenterX()), (int) (bg.hitbox.getY() + 100), 20, 20, this, 1);
		d2 = new Door((int)(d.hitbox.getCenterX() + 990), (int)(d.hitbox.getCenterY()), 20, 20, this, 1);
		scaleX = scaleY = 1;
		new Window(getW(), getH(), "Game", this);
		this.addKeyListener(new KeyInput(p));
		this.addMouseListener(menu);
	}

	public static int getW() {
		return (int) borders.getMaxX();
	}

	public static int getH() {
		return (int) borders.getMaxY();
	}

	public synchronized void start() {
		ArrayList<Point2D> tempPoints = new ArrayList<Point2D>();
		tempPoints.add(new Point2D.Double(302, 36));
		tempPoints.add(new Point2D.Double(417, 36));
		tempPoints.add(new Point2D.Double(453, 72));
		tempPoints.add(new Point2D.Double(453, 224));
		tempPoints.add(new Point2D.Double(648, 419));
		tempPoints.add(new Point2D.Double(648, 540));
		tempPoints.add(new Point2D.Double(408, 780));
		tempPoints.add(new Point2D.Double(408, 834));
		tempPoints.add(new Point2D.Double(445, 871));
		tempPoints.add(new Point2D.Double(610, 871));
		tempPoints.add(new Point2D.Double(648, 909));
		tempPoints.add(new Point2D.Double(648, 1170));
		tempPoints.add(new Point2D.Double(610, 1208));
		tempPoints.add(new Point2D.Double(445, 1208));
		tempPoints.add(new Point2D.Double(408, 1245));
		tempPoints.add(new Point2D.Double(408, 1447));
		tempPoints.add(new Point2D.Double(379, 1476));
		tempPoints.add(new Point2D.Double(379, 1539));
		tempPoints.add(new Point2D.Double(373, 1545));
		tempPoints.add(new Point2D.Double(373, 1549));
		tempPoints.add(new Point2D.Double(381, 1557));
		tempPoints.add(new Point2D.Double(381, 1579));
		tempPoints.add(new Point2D.Double(372, 1588));
		tempPoints.add(new Point2D.Double(347, 1588));
		tempPoints.add(new Point2D.Double(338, 1579));
		tempPoints.add(new Point2D.Double(338, 1557));
		tempPoints.add(new Point2D.Double(346, 1549));
		tempPoints.add(new Point2D.Double(346, 1545));
		tempPoints.add(new Point2D.Double(340, 1539));
		tempPoints.add(new Point2D.Double(340, 1476));
		tempPoints.add(new Point2D.Double(311, 1447));
		tempPoints.add(new Point2D.Double(311, 1245));
		tempPoints.add(new Point2D.Double(274, 1208));
		tempPoints.add(new Point2D.Double(109, 1208));
		tempPoints.add(new Point2D.Double(71, 1170));
		tempPoints.add(new Point2D.Double(71, 909));
		tempPoints.add(new Point2D.Double(109, 871));
		tempPoints.add(new Point2D.Double(274, 871));
		tempPoints.add(new Point2D.Double(311, 834));
		tempPoints.add(new Point2D.Double(311, 780));
		tempPoints.add(new Point2D.Double(71, 540));
		tempPoints.add(new Point2D.Double(71, 419));
		tempPoints.add(new Point2D.Double(266, 224));
		tempPoints.add(new Point2D.Double(266, 72));
		solids.add(new Solid(tempPoints));

		tempPoints = new ArrayList<Point2D>();
		tempPoints.add(new Point2D.Double(279, 959));
		tempPoints.add(new Point2D.Double(440, 959));
		tempPoints.add(new Point2D.Double(440, 1120));
		tempPoints.add(new Point2D.Double(279, 1120));
		solids.add(new Solid(tempPoints));

		tempPoints = new ArrayList<Point2D>();
		tempPoints.add(new Point2D.Double(359, 269));
		tempPoints.add(new Point2D.Double(360, 269));
		tempPoints.add(new Point2D.Double(551, 460));
		tempPoints.add(new Point2D.Double(551, 499));
		tempPoints.add(new Point2D.Double(360, 690));
		tempPoints.add(new Point2D.Double(359, 690));
		tempPoints.add(new Point2D.Double(168, 499));
		tempPoints.add(new Point2D.Double(168, 460));
		solids.add(new Solid(tempPoints));
		makeLvl1();
		
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	public void makeLvl1() {
		ArrayList<Point2D> tempPoints = new ArrayList<Point2D>();
		tempPoints.add(new Point2D.Double(1302, 1565));
		tempPoints.add(new Point2D.Double(1417, 1565));
		tempPoints.add(new Point2D.Double(1455, 1527));
		tempPoints.add(new Point2D.Double(1455, 1374));
		tempPoints.add(new Point2D.Double(1528, 1302));
		tempPoints.add(new Point2D.Double(1528, 291));
		tempPoints.add(new Point2D.Double(1455, 219));
		tempPoints.add(new Point2D.Double(1455, 66));
		tempPoints.add(new Point2D.Double(1417, 28));
		tempPoints.add(new Point2D.Double(1302, 28));
		tempPoints.add(new Point2D.Double(1264, 66));
		tempPoints.add(new Point2D.Double(1264, 219));
		tempPoints.add(new Point2D.Double(1191, 291));
		tempPoints.add(new Point2D.Double(1191, 1302));
		tempPoints.add(new Point2D.Double(1264, 1374));
		tempPoints.add(new Point2D.Double(1264, 1527));
		tempPoints.add(new Point2D.Double(1302, 1565));
		solids.add(new Solid(tempPoints));

		tempPoints.removeAll(tempPoints);
		tempPoints.add(new Point2D.Double(1191, 1210));

		tempPoints.add(new Point2D.Double(1508, 1210));
		//solids.add(new Solid(tempPoints));
		lasers.add(new Laser(1191, 1210, 30, 30, (1508-1191), this));

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
		Timer renderTimer = new Timer("render");
		TimerTask RENDER = new RenderTask(this, renderTimer);

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		renderTimer.schedule(RENDER, 17);
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
//			if (running)
//				render();
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
		currentSize = getSize();
		currentSize.setSize(currentSize.width, currentSize.width);
		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
		Graphics g = bs.getDrawGraphics();

		Graphics2D menuG2D = (Graphics2D) g;

		// if ratio is greater than 1.5, it is too wide. if it is less than 1.5, it is
		// if ratio is greater than 1.5, it is too wide. if it is less than 1.5, it is
		// too tall
		if ((currentSize.width + 0.0) / currentSize.height > 1.5) {
			scaleX = (currentSize.height * 1.5) / borders.getWidth();
			scaleY = (currentSize.height + 0.0) / borders.getHeight();
//			g2d.scale((currentSize.height * 1.5) / resolution.width, (currentSize.height + 0.0) / resolution.height);
		} else if ((currentSize.width + 0.0) / currentSize.height < 1.5) {
			scaleX = (currentSize.width + 0.0) / borders.getWidth();
			scaleY = (currentSize.width / 1.5) / borders.getHeight();
//			g2d.scale((currentSize.width + 0.0) / resolution.width, (currentSize.width / 1.5) / resolution.height);
		} else {
			scaleX = (currentSize.width + 0.0) / borders.getWidth();
			scaleY = (currentSize.height + 0.0) / borders.getHeight();
//			g2d.scale((currentSize.width + 0.0) / resolution.width, (currentSize.height + 0.0) / resolution.height);
		}
		g2d.scale(scaleX, scaleY);

		g2d.setColor(Color.BLACK);
		g2d.fill(borders);

		if (p.health <= 0)
			state = GameState.GAME_OVER;

		if (state == GameState.MENU) {

			menu.render(menuG2D);

		}

		if (state == GameState.INSTRUCTIONS) {

			menu.renderInstructions(menuG2D);
		}

		else if (state == GameState.RUNNING) {

			renderGame(g2d);
			menuG2D.setColor(Color.WHITE);
			menuG2D.fill(new Rectangle2D.Float(1350, 50, 100, 100));
			
			
			metrics = menuG2D.getFontMetrics(font);
			menuG2D.setFont(new Font("courier new", 1, 30));
			menuG2D.drawString((""+ ((bg.hitbox.getX()) + ", "+bg.hitbox.getY())), 100, 100);
			
		}

		else if (state == GameState.PAUSE) {

			menu.renderPause(menuG2D);

		}

		else if (state == GameState.GAME_OVER) {

			menu.renderGameOver(menuG2D);
			//stop();

		}
		else if(state == GameState.POWERUP1) {
			menu.renderPowerMenu1(menuG2D);
		}

		g2d.dispose();

		bs.show();
		tick();
		try {
			Thread.sleep(2);
		} catch (Exception e) {
		}
	}

	public void renderGame(Graphics2D g2d) {
		
		bg.render(g2d);
	
		lv1.render(g2d);
		lv3.render(g2d);
		p.render(g2d);
		d.render(g2d);
		d2.render(g2d);
		for (Enemy e : enemies)
			e.render(g2d);
		for (Bullet b : bullets)
			b.render(g2d);
		for (Laser l : lasers) {
			l.render(g2d);
		}
		g2d.setColor(Color.WHITE);
		
		
		

		// g2d.fill(new Rectangle2D.Float(200, 25, 10, 10));
	for (Solid s : solids)
			s.render(g2d);

	}

	public void tick() {
		Iterator<Bullet> bi = bullets.iterator();
		while (bi.hasNext()) {
			Bullet b = bi.next();
			if (b.shooter != p && b.hitbox.intersects(p.hitbox)) {
				bi.remove();
				p.damage(5);
				System.out.println(p.health);
			} else if (!b.hitbox.intersects(borders) || b.collide(solids).size() > 0) {
				bi.remove();
			}
			
			
		}

		Iterator<Enemy> ei = enemies.iterator();
		while (ei.hasNext()) {
			Enemy e = ei.next();
			for (Bullet B : bullets)
				if (!B.shooter.getClass().equals(e.getClass()) && e.hitbox.intersects(B.hitbox))
					e.alive = false;
			if (e.alive == false) {
				ei.remove();
			}
			
		}
		for(Bullet b : bullets) {
			for(Laser l : lasers) {
				if(l.hit1.hitbox.intersects(b.hitbox)) {l.deactivate(l.hit1);}
				if(l.hit2.hitbox.intersects(b.hitbox)) {l.deactivate(l.hit2);}
			}
		}
		
		
		scroll();
	}

	public void scroll() {
		// float topBorder = 240, bottomBorder = 240, leftBorder = 360, rightBorder =
		// 360, movex = 0, movey = 0;

		double hBorder = borders.getHeight() / 2, wBorder = borders.getWidth() / 2;

		double movex = 0, movey = 0;
		if (p.hitbox.getCenterY() < hBorder) {
			movey = (float) (hBorder - p.hitbox.getCenterY());
		} else if (p.hitbox.getCenterY() > hBorder) {
			movey = (float) (hBorder - p.hitbox.getCenterY());
		}

		if (p.hitbox.getCenterX() < wBorder) {
			movex = (float) (wBorder - p.hitbox.getCenterX());
		} else if (p.hitbox.getCenterX() > wBorder) {
			movex = (float) (wBorder - p.hitbox.getCenterX());
		}

		bg.scroll(movex, movey);
		lv1.scroll(movex, movey);
		lv3.scroll(movex, movey);
		p.scroll(movex, movey);
		d.scroll(movex, movey);
		d2.scroll(movex, movey);
		for (Enemy e : enemies)
			e.scroll(movex, movey);
		for (Bullet b : bullets)
			b.scroll(movex, movey);
		for (Solid s : solids)
			s.scroll(movex, movey);
		for(Laser l : lasers)
			l.scroll(movex, movey);
		
		if (cooldown == 0) {
			enemies.add(new Enemy((int) (bg.hitbox.getX() + 300), (int) (bg.hitbox.getY() + 240), 16, 20, this));
			cooldown = 2000;
		} else
			cooldown--;
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

	class RenderTask extends TimerTask {
		Game game;
		Timer timer;

		public RenderTask(Game game, Timer timer) {
			this.game = game;
			this.timer = timer;
		}

		public void run() {
			game.render();
			timer.schedule(new RenderTask(game, timer), 17);
		}

	}
}

class Window extends Canvas {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Window(int w, int h, String t, Game game) {
		JFrame frame = new JFrame(t);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		frame.setVisible(true);
		game.start();
	}
}