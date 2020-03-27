package unsw.dungeon;

public abstract class InteractableEntities extends Entity{
	
	/**
	 * Empty constructor for interactable entity.
	 */
	public InteractableEntities() {
		super();
	}
	
	/**
	 * Constructor for interactable entity.
	 * @param x The x-coordinate of entity.
	 * @param y The y-coordinate of entity.
	 * @param desc The description of the entity.
	 * @param dungeon The dungeon in which entity exists.
	 * @param visibility The visibility of the entity. True for visible or false otherwise.
	 * @param tangible The tangiblity of the entity. True for tangible or false otherwise.
	 */
	public InteractableEntities(int x, int y, String desc, Dungeon dungeon, boolean visibility, boolean tangible) {
		super(x, y, desc, dungeon, visibility, tangible);
	}
	
	//This makes adding new Entity very easy
	/**
	 * Gets and activate effect from the entity that the movable entity interact with. Serves as a template
	 */
	public abstract void interactEffect(Entity entity);
}
