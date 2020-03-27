package unsw.dungeon;

public class AndCompositeObjective extends CompositeObjective{
	
	
	/**
	 *  Constructor for AndCompositeObjective
	 */
	public AndCompositeObjective(Dungeon dungeon) {
		super("AND", dungeon);
	}
	
	/**
	 * Checks status of all objectives held are true using AND logic.
	 */
	public boolean checkStatus() {
		boolean complete = true;
		for (Objective objective : getObjectives()) {
			if (!objective.getStatusBoolean()) complete = false;
		}
		return complete;
	}
	
	/**
	 * Updates the objectives status to be true if required conditions are met.
	 */
	public void updateStatus() {
		for (Objective obj : getObjectives()) {
			if (obj.getDescription().compareTo("AND") == 0) {
				obj.updateStatus();
			} else if (obj.getDescription().compareTo("OR") == 0) {
				obj.updateStatus();
			}
		}		
		if (checkStatus()) this.getStatus().set(true);
	}

	/**
	 * Displays exit if it is the last objective left.
	 */
	public void showExit() {
		boolean complete = true;
		boolean found = false;
		GetExit obj = null;
		for (Objective objective : getObjectives()) {
			if (objective.getDescription().compareTo("Get Exit") == 0) {
				found = true;
				obj = (GetExit) objective;
			} else {
				if (!objective.getStatusBoolean()) complete = false;
			}
		}
		
		if (complete && found) {
			obj.getExit().setVisibility(true);;
		}
	}
}
