package unsw.dungeon;

public class MoveAwayPlayer implements MoveBehaviour{

	/**
	 * The Algo utilise the maximum manhattan function to run away from player
	 * @param p The player entity.
	 * @param e The enemy entity.
	 * @param dungeon The dungeon in which the player and enemy entity exist.
	 */
	@Override
	public void perform(Player p, Enemy e, Dungeon dungeon) {
		// Generates required dummy to determine where entity can move.
		Dummy left = new Dummy(e.getX() - 1, e.getY(), dungeon);
		Dummy right = new Dummy(e.getX() + 1, e.getY(), dungeon);
		Dummy up = new Dummy(e.getX(), e.getY() - 1, dungeon);
		Dummy down = new Dummy(e.getX(), e.getY() + 1, dungeon);
		
		Dummy max = null;
		int maxValue = -2;
		
		
		int currentDistance = manhattan(p, e);
		
		// Determines best movement to make to get closer to target.
		if (manhattan(p, left) > maxValue) {
			maxValue = manhattan(p, left);
			max = left;
		}		
		if (manhattan(p, right) > maxValue) {
			maxValue = manhattan(p, right);
			max = right;
		}	
		if (manhattan(p, up) > maxValue) {
			maxValue = manhattan(p, up);
			max = up;
		}	
		if (manhattan(p, down) > maxValue) {
			maxValue = manhattan(p, down);
			max = down;
		}
		if (maxValue > currentDistance) {
			if (max.equals(down)) {
				e.moveDown();
			} else if (max.equals(up)) {
				e.moveUp();
			} else if (max.equals(right)) {
				e.moveRight();
			} else if (max.equals(left)) {
				e.moveLeft();
			}
		}
		
	}
	
	/**
	 * Manhattan formula
	 * @param p Player Entity
	 * @param e Enemy Entity
	 * @return The distance from player and enemy entity.
	 */
	private int manhattan(Movable p, Movable e) {
		if (e.isPathBlocked(e.getX(), e.getY())) {
			return -1;
		}
		return Math.abs(p.getX() - e.getX()) + Math.abs(p.getY() - e.getY());
	}

}
