package unsw.dungeon;

public interface MoveBehaviour {
	
	/**
	 * Performing a movement behaviour for the movable entity
	 * @param p The player entity
	 * @param e The enemy entity
	 * @param dungeon the current dungeon
	 */
	public void perform(Player p, Enemy e, Dungeon dungeon);
}
