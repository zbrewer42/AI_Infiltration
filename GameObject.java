package Game;

public class GameObject {
	int x, y, w, h, dx, dy;

	public GameObject(int ex, int why, int wid, int hi) {
		x = ex;
		y = why;
		w = wid;
		h = hi;
		dx = dy = 0;
	}

	public void tick() {
	}

	public boolean collide(GameObject other) {
		if (this.x + this.w > other.x && this.y + this.h > other.y && this.x < other.x + other.w
				&& this.y < other.y + other.h) {
			return true;
		}
		return false;
	}

	public boolean collide(Block other) {
		if (this.x + this.w > other.x && this.y + this.h > other.y && this.x < other.x + other.w
				&& this.y < other.y + other.h) {
			return true;
		}
		return false;
	}

	public boolean collideN(Block b) {
		if ((this.y == b.y + b.h) && (this.x + this.w > b.x && this.x < b.x + b.w)) {
			System.out.println("cN");
			return true;
		}
		return false;
	}

	public boolean collideS(Block b) {
		if ((this.y + this.h == b.y) && (this.x + this.w > b.x && this.x < b.x + b.w)) {
			System.out.println("cS");
			return true;
		}
		return false;
	}

	public boolean collideW(Block b) {
		if ((this.x == b.x + b.w) && (this.y + this.h >= b.y && this.y <= b.y + b.h)) {
			System.out.println("cW");
			return true;
		}
		return false;
	}

	public boolean collideE(Block b) {
		if ((this.x + this.w == b.x) && (this.y + this.h > b.y && this.y < b.y + b.h)) {
			System.out.println("cE");
			return true;
		}
		return false;
	}

}