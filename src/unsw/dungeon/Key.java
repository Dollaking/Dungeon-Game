package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Key extends Unmovable{
	private BooleanProperty pickedUp;
	private int id;
	
	/**
	 * The Key constructor when the coords, linking id and dungeon is given
	 * @param x The x-coordinate of entity.
	 * @param y The y-coordinate of entity.
	 * @param id The linking id to the door.
	 * @param dungeon The dungeon in which entity exists.
	 */
	public Key(int x, int y, int id, Dungeon dungeon) {
		super(x, y, "Key", dungeon, true, false);
		this.pickedUp = new SimpleBooleanProperty(false);
		this.id = id;
		
	}

	/**
	 * Checks whether the key entity is picked up
	 * @return Boolean based on whether the key is picked up
	 */
	public boolean getPickedUp() {
		return pickedUp.getValue();
	}

	/**
	 * Setter for key pickup
	 * @param b Boolean for key pickup
	 */
	public void setPickedUp(boolean b) {
		this.pickedUp.setValue(b);
	}
	
	/**
	 * Key being picked up
	 */
	public void pickUpKey() {
		pickedUp.setValue(true);
		this.setVisibility(false);
		
		System.out.println("Picked Up Key");
	}

	/**
	 * Get the linking id of the key
	 * @return The linking id of key
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter for the link id
	 * @param id The linking id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Template interactEffect Function
	 */
	@Override
	public void interactEffect(Entity entity) {
		// TODO Auto-generated method stub
		if (entity.getDescription().equals("Player") && !this.getPickedUp()) {
			if (!((Player) entity).hasKey()) {
				System.out.println("Picked Up Key!");
				((Player) entity).addKey(this);
				pickUpKey();
			}

		}
	}
	
	/**
	 * Resets any unique attributes.
	 */
	@Override
	public void resetOther() {
		this.pickedUp.setValue(false);
	}

	
}
