package unsw.dungeon;

public abstract class Unmovable extends InteractableEntities {
	
	// Can add, tangibility and visibility.
	
	/**
	 * For Empty Entities, E.g. when removing the sword from the player inventory, an empty sword entity is used instead
	 */
	public Unmovable() {
		
	}
	/**
	 * Constructor for Unmovable entity with coords, description and the current dungeon
	 * @param x The x-coordinate of entity.
	 * @param y The y-coordinate of entity.
     * @param desc The description of the entity
     * @param dungeon The dungeon in which entity exists.
	 */
	public Unmovable(int x, int y, String desc, Dungeon dungeon, boolean visibility, boolean tangible) {
		super(x, y, desc, dungeon, visibility, tangible);
	}

}