package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Treasure extends Unmovable{
	
	private BooleanProperty pickedUp;

	/**
	 * The constructor for treasure when coords and dungeon is given
	 * @param x The x-coordinate of entity.
	 * @param y The y-coordinate of entity.
	 * @param dungeon The dungeon in which entity exists.
	 */
	public Treasure (int x, int y, Dungeon dungeon) {
		super(x, y, "Treasure", dungeon, true, false);
		this.pickedUp = new SimpleBooleanProperty(false);
		
		// Adds required observers.
		this.pickedUp.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.booleanValue()) {
					dungeon.getPlayer().addTreasure();
					dungeon.forceCheckObjectiveParameters("Collect Treasure");
					setVisibility(false);
				}
			}
			
		});
	}

	/**
	 * Template interactEffect function
	 */
	@Override
	public void interactEffect(Entity entity) {
		// TODO Auto-generated method stub
		if (entity.getDescription().equals("Player")) {
			this.pickedUp.setValue(true);
			//this.getDungeon().removeEntity(this);
		}
	}
	
	/**
	 * Get whether the treasure is picked up
	 * @return Boolean whether treasure is picked up
	 */
	public boolean isPickedUp() {
		return pickedUp.get();
	}	

    /**
     * Resets any unique attributes.
     */
	@Override
	public void resetOther() {
		this.pickedUp.setValue(false);
	}

	
}
