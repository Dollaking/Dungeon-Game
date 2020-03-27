package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class BoulderToFloorSwitch extends BaseObjective {
	
	// Attributes
	private IntegerProperty numSwitches;
	private int total;
	
	/**
	 *  Constructor for BoulderToFloorSwitch
	 *  @param num The number of floor switches left in dungeon.
	 */
	public BoulderToFloorSwitch(int num, Dungeon dungeon) {
		super("Boulder to Floor Switches", dungeon);
		this.total = num;
		this.numSwitches = new SimpleIntegerProperty(0);
		
		// Add required observers.
		// Observer for number of switches left.
		this.numSwitches.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println(newValue.intValue());
				// If no switches are left.
				if (checkStatus()) {
					getStatus().set(true);
				} else {
					getStatus().set(false);
				}
			}
		});
		
		// Observer for whether objective is completed.
		this.getStatus().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.booleanValue()) {
					System.out.println("Moved all boulders to floor switches.");
					dungeon.forceCheckObjectiveParameters("Boulder to Floor Switches");
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
			if (ent.getDescription().compareTo("Floor Switch") == 0) {
				FloorSwitch obj = (FloorSwitch) ent;
				if (obj.isActivated()) count++;
			}
		}
		setNumSwitches(count);
	}
	
	/**
	 * Set Number of Switches
	 * @param num Number of switches
	 */
	public void setNumSwitches(int num) {
		numSwitches.set(num);
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
		if (numSwitches.get() == total) return true;
		return false;
	}
	
	/**
	 * Prints out the objectives.
	 */
	@Override
	public void printObjective() {
		System.out.println("Move Boulders to Floor Switches");
	}
	
	/**
	 * Formats a string containing all objectives within child objectives.
	 * @param space The amount of space needed before the objective description
	 * is displayed.
	 * @return A formatted string containing objectives.
	 */
	@Override
	public String objectiveStatus(String space) {
		String tmp = space + "Move Boulders to Floor Switches: " + numSwitches.get();
		return tmp;
	}
	
	/**
	 * Resets the objectives to its original state.
	 */
	@Override
	public void resetObjective() {
		this.getStatus().set(false);
		numSwitches.set(0);
	}
}
