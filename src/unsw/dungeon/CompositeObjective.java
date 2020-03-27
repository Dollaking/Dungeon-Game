package unsw.dungeon;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public abstract class CompositeObjective extends BaseObjective {

	// Attributes
	private ArrayList<Objective> objectives;
	
	/**
	 *  Constructor for CompositeObjective
	 */
	public CompositeObjective(String desc, Dungeon dungeon) {
		super(desc, dungeon);
		this.objectives = new ArrayList<Objective>();
		
		// Observer for whether objective is completed.
		getStatus().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.booleanValue()) {
					dungeon.updateObjectives();
				}
			}
		});
	}
		
	/**
	 * Adds an objective to the list of objective.
	 * @param objective The objective to be added to list.
	 */
	public void addObjective(Objective objective) {
		if (!objectives.contains(objective)) objectives.add(objective);
	}
		
	/**
	 * Removes an objective from the list of objective.
	 * @param objective The objective to be removed to list.
	 */
	public void removeObjective(Objective objective) {
		if (objectives.contains(objective)) objectives.remove(objective);
	}
	
	/**
	 * Getter for all objectives held.
	 * @return The objectives held.
	 */
	public ArrayList<Objective> getObjectives() {
		return objectives;
	}
	
	/**
	 * Updates objectives based on a particular type of objective.
	 * @param type The objective type that needs to be updated.
	 */
	@Override
	public void forceUpdateParameter(String type) {
		for (Objective obj : objectives) {
			obj.forceUpdateParameter(type);
		}
	}
	
	/**
	 * Get the total number of objectives.
	 */
	@Override
	public int getObjectiveDepth() {
		int total = 1;
		for (Objective obj : objectives) {
			total += obj.getObjectiveDepth();
		}
		return total;
	}
	
	/**
	 * Resets the objectives to its original state.
	 */
	@Override
	public void resetObjective() {
		this.getStatus().set(false);
		for (Objective obj : objectives) obj.resetObjective();
	}
	
	/**
	 * Formats a string containing all objectives within child objectives.
	 * @param space The amount of space needed before the objective description
	 * is displayed.
	 * @return A formatted string containing objectives.
	 */
	public String objectiveStatus(String space) {
		String tmp = space + this.getDescription() + ": ";
		if (checkStatus()) {
			tmp = tmp + "Complete\n";
		} else {
			tmp = tmp + "Incomplete\n";
		}
		
		space = space + "  ";
		for (Objective obj : this.getObjectives()) {
			tmp = tmp + space + obj.objectiveStatus(space) + "\n";
		}
		return tmp;
	}
	
	/**
	 * Prints out the objectives.
	 */
	@Override
	public void printObjective() {
		String space = "";
		System.out.println(objectiveStatus(space));
	}

	public abstract boolean checkStatus();
	
	public abstract void updateStatus();

}
