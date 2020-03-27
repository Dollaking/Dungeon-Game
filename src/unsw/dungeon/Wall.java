package unsw.dungeon;

public class Wall extends Unmovable{
	/**
	 * Constructor when coords and current dungeon is given
	 * @param x The x-coordinate of entity.
	 * @param y The y-coordinate of entity.
	 * @param dungeon The dungeon in which entity exists.
	 */
    public Wall(int x, int y, Dungeon dungeon) {
		super(x, y, "Wall", dungeon, true, true);
    }

	@Override
	public void interactEffect(Entity entity) {
		// TODO Auto-generated method stub
		
	}

}
