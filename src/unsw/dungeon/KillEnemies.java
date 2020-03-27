package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class KillEnemies extends BaseObjective {
	
	// Attributes
	private IntegerProperty enemiesLeft;
	private int total;
	
	/**
	 *  Constructor for KillEnemies
	 *  @param num The number of enemies left in dungeon.
	 */
	public KillEnemies(int num, Dungeon dungeon) {
		super("Kill Enemies", dungeon);
		this.total = num;
		this.enemiesLeft = new SimpleIntegerProperty(num);
		
		// Add required observers.
		// Observer for number of enemies left.
		this.enemiesLeft.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// If no enemies are left.
				if (checkStatus()) getStatus().set(true); 
			}
		});
		
		// Observer for whether objective is completed.
		this.getStatus().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.booleanValue()) {
					System.out.println("Killed all enemies.");
					dungeon.forceCheckObjectiveParameters("Kill Enemies");
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
			if (ent.getDescription().compareTo("Enemy") == 0) {
				Enemy obj = (Enemy) ent;
				if (obj.isAlive()) count++;
			}
		}
		setEnemiesLeft(count);
	}
	
	/**
	 * Set how many enemies should be left
	 * @param num Number of enemies to be left
	 */
	public void setEnemiesLeft(int num) {
		enemiesLeft.set(num);
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
	 * @return True if objective is complete, false otherwise.
	 */
	@Override
	public boolean checkStatus() {
		if (enemiesLeft.get() == 0) return true;
		return false;
	}
	
	/**
	 * Prints out the objectives.
	 */
	@Override
	public void printObjective() {
		System.out.println("Kill all enemies");
	}
	
	/**
	 * Formats a string containing all objectives within child objectives.
	 * @param space The amount of space needed before the objective description
	 * is displayed.
	 * @return A formatted string containing objectives.
	 */
	@Override
	public String objectiveStatus(String space) {
		String tmp = space + "Number of enemies left: " + enemiesLeft.get();
		return tmp;
	}
	
	/**
	 * Resets the objectives to its original state.
	 */
	@Override
	public void resetObjective() {
		this.getStatus().set(false);
		enemiesLeft.set(total);
	}
}
