package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class CollectTreasure extends BaseObjective {
	
	// Attributes
	private IntegerProperty numTreasure;
	private int total;
	
	/**
	 *  Constructor for CollectTreasure
	 *  @param num The number of treasure left in dungeon.
	 */
	public CollectTreasure(int num, Dungeon dungeon) {
		super("Collect Treasure", dungeon);
		this.total = num;
		this.numTreasure = new SimpleIntegerProperty(0);
		
		// Adds required observers.
		// Observer for number of treasure.
		this.numTreasure.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// If no treasure are left.
				if (checkStatus()) getStatus().set(true);
			}
		});
		
		// Observer for whether objective is completed.
		this.getStatus().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.booleanValue()) {
					System.out.println("Collected all treasure.");
					dungeon.forceCheckObjectiveParameters("Collect Treasure");
					dungeon.updateObjectives();
				}
			}
		});
		
	}
	
	/**
	 * Updates objectives based on a particular type of objective.
	 * @param type The objective type that needs to be updated.
	 */
	@Override
	public void forceUpdateParameter(String type) {
		int count = 0;
		for (Entity ent : this.getDungeon().getEntityList()) {
			if (ent.getDescription().compareTo("Treasure") == 0) {
				Treasure obj = (Treasure) ent;
				if (obj.isPickedUp()) count++;
			}
		}
		setNumTreasure(count);
	}
	
	/**
	 * Set Number of treasure
	 * @param num Number f treasure wanted to set
	 */
	public void setNumTreasure(int num) {
		numTreasure.set(num);
	}
	
	/**
	 * Updates the objectives status to be true if required conditions are met.
	 */
	@Override
	public void updateStatus() {
		// Does nothing.
	}
	
	/**
	 * Checks status of all objectives held are true using AND logic.
	 */
	@Override
	public boolean checkStatus() {
		if (numTreasure.get() == total) return true;
		return false;
	}
	
	/**
	 * Prints out the objectives.
	 */
	@Override
	public void printObjective() {
		System.out.println("Collect all treasure");
	}
	
	/**
	 * Formats a string containing all objectives within child objectives.
	 * @param space The amount of space needed before the objective description
	 * is displayed.
	 * @return A formatted string containing objectives.
	 */
	@Override
	public String objectiveStatus(String space) {
		String tmp = space + "Treasure left to collect: " + (total - numTreasure.get());
		return tmp;
	}
	
	/**
	 * Resets the objectives to its original state.
	 */
	@Override
	public void resetObjective() {
		this.getStatus().set(false);
		numTreasure.set(0);
	}
}
