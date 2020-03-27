package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Door extends Unmovable{
	private BooleanProperty open;
	private int id;
	
	/**
	 * The constructor for door where coords, link id and dungeon is given
	 * @param x The x-coordinate of entity.
	 * @param y The y-coordinate of entity.
	 * @param id The linking id for Door, if the key id and door id are the same, it will unlock door
	 * @param dungeon The dungeon in which entity exists.
	 */
	public Door(int x, int y, int id, Dungeon dungeon) {
		super(x, y, "Door", dungeon, true, true);
		this.open = new SimpleBooleanProperty(false);
		this.id = id;
	}
	
	/**
	 * Check if door is open.
	 * @return The Boolean Property of whether the door is open or not.
	 */
	public BooleanProperty getOpen() {
		return open;
	}
	
	/**
	 * Getter for linking id
	 * @return Returns the linking id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter for linking id
	 * @param id Returns the linking id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Opens the Door
	 */
	public void openDoor() {
		open.setValue(true);
		this.setTangible(false);
	}
	
	/**
	 * Checks whether door is open
	 * @return returns true or false whether the door is open
	 */
	public boolean isOpen() {
		return open.getValue();
	}
	
	/**
	 * Checks whether the key is able to unlock the door
	 * @param id The linking id of the key
	 * @return Returns true or false based on whether the key can unlock the door
	 */
	public Boolean checkKeyCompatability(int id) {
		if (this.getId() == id) {
			return true;
		} else {
			System.out.println("Not right key!");
		}
		return false;
	}
	
	/**
	 * Template interactEffect function
	 */
	@Override
	public void interactEffect(Entity entity) {
		// TODO Auto-generated method stub
		if (!this.isOpen()) {
			if (entity.getDescription().equals("Player")) {
				Player p = (Player) entity;
				if (p.getKey() != null) {
					if (checkKeyCompatability(p.getKey().getId())) {
						System.out.println("Door is unlocked!");
						openDoor();
						p.removeKey();
					}
				}
			}
		}
		
	}
	
    /**
     * Resets any unique attributes.
     */
	@Override
	public void resetOther() {
		this.open.setValue(false);
	}

	
	
}
