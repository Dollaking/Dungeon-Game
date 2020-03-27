package unsw.dungeon;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class GetExit extends BaseObjective {

	private Exit exit;
	
	/**
	 * Constructor for GetExit.
	 * @param exit The exit object for the objective.
	 * @param dungeon The dungeon in which entity exists.
	 */
	public GetExit(Exit exit, Dungeon dungeon) {
		super("Get Exit", dungeon);
		this.exit = exit;

		// Observer for whether objective is completed.
		this.getStatus().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (checkStatus()) {
					dungeon.forceCheckObjectiveParameters("Get Exit");
					dungeon.updateObjectives();
				}
			}
		});
	}
	
	/**
	 * Checks if it is the only objective left.
	 * @return Whether the updats is the last one left.
	 */
	public Boolean checkOnlyObjective() {
		return true;
	}
	
	/**
	 * Updates the objectives status to be true if required conditions are met.
	 */
	@Override
	public void updateStatus() {
		// Does nothing.
	}
	
	/**
	 * Updates objectives based on a particular type of objective.
	 * @param type The objective type that needs to be updated.
	 */
	@Override
	public void forceUpdateParameter(String type) {
		if (type.compareTo("Get Exit") == 0) getStatus().set(true);
	}
	
	/**
	 * Getter for exit
	 * @return Exit object
	 */
	public Exit getExit() {
		return exit;
	}
	
	/**
	 * Checks status of all objectives held are true using AND logic.
	 * @return True if objective is complete, false otherwise.
	 */
	@Override
	public boolean checkStatus() {
		if (this.getStatusBoolean()) return true;
		return false;
	}
	
	/**
	 * Prints out the objectives.
	 */
	@Override
	public void printObjective() {
		System.out.println("Get to the Exit");
	}
	
	/**
	 * Formats a string containing all objectives within child objectives.
	 * @param space The amount of space needed before the objective description
	 * is displayed.
	 * @return A formatted string containing objectives.
	 */
	@Override
	public String objectiveStatus(String space) {
		String tmp = space + "Get to the Exit";
		return tmp;
	}
	
	/**
	 * Resets the objectives to its original state.
	 */
	@Override
	public void resetObjective() {
		this.getStatus().set(false);
		if (this.getDungeon().getObjective().getDescription().equals("Get Exit")) {
			this.exit.setVisibility(true);
		}	else if (this.getDungeon().getObjective().getDescription().compareTo("OR") == 0) {
			OrCompositeObjective obj = (OrCompositeObjective) this.getDungeon().getObjective();
			obj.showExit();
		}
		//this.getDungeon().forceCheckObjectiveParameters("Get Exit");
	}
}
