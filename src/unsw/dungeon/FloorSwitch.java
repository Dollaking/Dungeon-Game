package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class FloorSwitch extends Unmovable{

	private BooleanProperty activated;
	
	/**
	 * Constructor for floor switch when coords and dungeon is given
	 * @param x The x-coordinate of entity.
	 * @param y The y-coordinate of entity.	
	 * @param dungeon The dungeon in which entity exists.
	 */
	public FloorSwitch(int x, int y, Dungeon dungeon) {
		super(x, y, "Floor Switch", dungeon, true, false);
		this.activated = new SimpleBooleanProperty(false);
		
		// Adds required observers.
		this.activated.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				dungeon.forceCheckObjectiveParameters("Boulder to Floor Switches");
			}
			
		});
	}
	
	/**
	 * Activates Floor Switch
	 */
	public void activateSwitch() {
		activated.setValue(true);
	}
	
	/**
	 * Deactivates Floor Switch
	 */
	public void deactivateSwitch() {
		activated.setValue(false);
	}
	
	/**
	 * Template Interact Effect
	 */
	@Override
	public void interactEffect(Entity entity) {
		System.out.println("Gets here");
		// TODO Auto-generated method stub
		if (entity.getDescription().equals("Boulder")) {
			activateSwitch();
		} else {
			deactivateSwitch();
		}
	}

	/**
	 * Checks whether the Floor switch is activated
	 * @return Boolean of whether floor switch is activated
	 */
	public boolean isActivated() {
		if (activated.get()) return true;
		return false;
	}
	
	@Override
	public void resetOther() {
		activated.set(false);
	}
	

}
