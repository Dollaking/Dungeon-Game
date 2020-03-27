package unsw.dungeon;

public class Dummy extends Enemy{
	/**
	 * Constructor to dummy for when coords and dungeon is given
	 * @param x The x-coordinate of entity.
	 * @param y The y-coordinate of entity.
	 * @param d The dungeon in which entity exists.
	 */
	public Dummy(int x, int y, Dungeon d) {
		super(x, y, d, "Dummy");
	}

}
