package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public abstract class BaseObjective implements Objective {
	
	// Attributes
	private BooleanProperty status;
	private String description;
	private Dungeon dungeon;
	
	/**
	 * Constructor for the base objective.
	 * @param desc The description of the objective.
	 * @param dungeon The dungeon in which the objective takes place.
	 */
	public BaseObjective(String desc, Dungeon dungeon) {
		this.description = desc;
		this.dungeon = dungeon;
		this.status = new SimpleBooleanProperty(false);
	}
	

	/**
	 * Checks whether status is true or false.
	 */
	@Override
	public boolean getStatusBoolean() {
		if (status.getValue()) return true;
		return false;
	}
		
	/**
	 * Sets status as true.
	 */
	@Override
	public void makeStatusTrue() {
		status.set(true);
	}
	
	/**
	 * Returns the description of the objective.
	 * @return The description of the objective.
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Getter for the dungeon in which the objective takes place.
	 * @return The dungeon in which the objective takes place.
	 */
	public Dungeon getDungeon() {
		return dungeon;
	}
	
	/**
	 * Getter for the status attribute of an objective.
	 * @return The status of the objective.
	 */
	public BooleanProperty getStatus() {
		return status;
	}
	
	/**
	 * Gets the number of objectives held within objective.
	 * @return The number of objectives held within the objective.
	 */
	@Override
	public int getObjectiveDepth() {
		return 1;
	}
	
	/**
	 * Prints out the objectives.
	 */
	public abstract void printObjective();
	
	/**
	 * Updates the objectives status to be true if required conditions are met.
	 */
	public abstract void updateStatus();
	
	/**
	 * Updates objectives based on a particular type of objective.
	 * @param type The objective type that needs to be updated.
	 */
	public abstract void forceUpdateParameter(String type);
	
	/**
	 * Checks status of all objectives held are true using AND logic.
	 * @return True if objective is complete, false otherwise.
	 */
	public abstract boolean checkStatus();
	
	/**
	 * Formats a string containing all objectives within child objectives.
	 * @param space The amount of space needed before the objective description
	 * is displayed.
	 * @return A formatted string containing objectives.
	 */
	public abstract String objectiveStatus(String space);
	
	/**
	 * Resets the objectives to its original state.
	 */
	public abstract void resetObjective();
}
