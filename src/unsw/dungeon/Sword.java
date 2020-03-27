package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Sword extends Unmovable{
	private int durability;
	private BooleanProperty pickedUp;
	
	/**
	 * Sword Constructor when coords and current dungeon is given
	 * @param x The x-coordinate of entity.
	 * @param y The y-coordinate of entity.
	 * @param dungeon The dungeon in which entity exists.
	 */
	public Sword(int x, int y, Dungeon dungeon) {
		super(x, y, "Sword", dungeon, true, false);
		this.durability = 5;
		this.pickedUp = new SimpleBooleanProperty(false);
	}
	
	/**
	 * Setter for picked up
	 * @param b Boolean value for pick up
	 */
	public void setPickedUp(boolean b) {
		this.pickedUp.setValue(b);
	}
	
	/**
	 * Checks whether sword is picked up
	 * @return Boolean of whether the sword is picked up
	 */
	public boolean getPickedUp() {
		return this.pickedUp.getValue();
	}
	
	/**
	 * Reducing the durability of the sword
	 */
	public void reducedDurability() {
		this.durability--;
	}
	
	/**
	 * Get the durability of the sword
	 * @return Durability of the sword
	 */
	public int getDurability() {
		return durability;
	}
	
	/**
	 * Template interactEffect function
	 */
	@Override
	public void interactEffect(Entity entity) {
		// TODO Auto-generated method stub
		if (entity.getDescription().equals("Player") && !getPickedUp()) {
			
			Player p = (Player) entity;
			if (p.getSword() == null) {
				p.addSword(this);
				setPickedUp(true);
				this.setVisibility(false);
				System.out.println("You picked up a sword!");
			} else {
				System.out.println("You already have a sword!");
			}
		}
		
	}
    
    /**
     * Resets any unique attributes.
     */
	@Override
	public void resetOther() {
		this.pickedUp.setValue(false);
		this.durability = 5;
	}

}
