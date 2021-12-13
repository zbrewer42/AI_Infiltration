package Game;

public class Weapon extends GameObject {
	boolean selected;

	public Weapon(int ex, int why, int wid, int hi) {
		super(ex, why, wid, hi);
		// TODO Auto-generated constructor stub
		selected = false;
	}

	public boolean hitE(Enemy e) {
		if (this.collide(e)) {
			return true;
		}
		return false;
	}

}
