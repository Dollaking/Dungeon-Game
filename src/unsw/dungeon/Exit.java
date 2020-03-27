package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Exit extends Unmovable{

	public Exit() {
		
	}
	/**
	 * Constructor for exit where coords and current dungeon is given
	 * @param x The x-coordinate of entity.
	 * @param y The y-coordinate of entity.
	 * @param dungeon The dungeon in which entity exists.
	 */
	public Exit(int x, int y, Dungeon dungeon) {
		super(x, y, "Exit", dungeon, false, false);
	}

	/**
	 * Template interactEffect
	 */
	@Override
	public void interactEffect(Entity entity) {
		System.out.println(this.getVisibility().get());
		if (entity.getDescription().equals("Player") && this.getVisibility().get()) {
			//Finish Game or advance to next stage
			getDungeon().forceCheckObjectiveParameters("Get Exit");
		}
		
	}

}
