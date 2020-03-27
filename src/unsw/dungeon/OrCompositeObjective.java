package unsw.dungeon;

public class OrCompositeObjective extends CompositeObjective {
	
	/**
	 * Constructor for OrCompositeObjective
	 */
	public OrCompositeObjective(Dungeon dungeon) {
		super("OR", dungeon);
	}
	
	/**
	 * Checks status of all objectives held are true using OR logic.
	 */
	public boolean checkStatus() {
		boolean complete = false;
		for (Objective objective : getObjectives()) {
			if (objective.getStatusBoolean()) complete = true;
		}
		return complete;
	}
	
	/**
	 * Updates the objectives status to be true if required conditions are met.
	 */
	@Override
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
	 * Displays exit.
	 */
	public void showExit() {
		boolean found = false;
		GetExit obj = null;
		for (Objective objective : getObjectives()) {
			if (objective.getDescription().compareTo("Get Exit") == 0) {
				found = true;
				obj = (GetExit) objective;
			}
		}
		
		if (found) {
			obj.getExit().setVisibility(true);
		}
	}
}
