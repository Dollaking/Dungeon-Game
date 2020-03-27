package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Potion extends Unmovable{
	
	private BooleanProperty pickedUp;
	
	/**
	 * Constructor for potion entity for when coords and current dungeon is given
	 * @param x The x-coordinate of entity.
	 * @param y The y-coordinate of entity.
	 * @param dungeon The dungeon in which entity exists.
	 */
	public Potion(int x, int y, Dungeon dungeon) {
		super(x, y, "Potion", dungeon, true, false);
		this.pickedUp = new SimpleBooleanProperty(false);
	}
	
	/**
	 * Setter for picking up the potion
	 * @param b The boolean you want to set the potion to
	 */
	public void setPickedUp(boolean b) {
		this.pickedUp.setValue(b);
	}
	
	/**
	 * Getter for picking up the potion
	 * @return Whether the potion is picked up
	 */
	public boolean getPickedUp() {
		return this.pickedUp.getValue();
	}
	
	/**
	 * Picking up potion
	 * @param p The player who is picking the potion up
	 */
	public void pickUp(Player p) {
		p.addPotion(this);
		setPickedUp(true);
		this.setVisibility(false);	
	}

	/**
	 * Template interact function
	 */
	@Override
	public void interactEffect(Entity entity) {
		// TODO Auto-generated method stub
		if (entity.getDescription().equals("Player") && !getPickedUp()) {
			System.out.println("Picked Up Key!");
			Player p = (Player) entity;
			pickUp(p);
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
