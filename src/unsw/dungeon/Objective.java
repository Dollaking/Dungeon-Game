package unsw.dungeon;

import javafx.beans.property.BooleanProperty;

public interface Objective {
	public boolean getStatusBoolean();
	public void makeStatusTrue();
	public String getDescription();
	public String objectiveStatus(String space);
	public void printObjective();
	public void updateStatus();
	public void forceUpdateParameter(String type);
	public boolean checkStatus();
	public int getObjectiveDepth();
	public void resetObjective();
	public BooleanProperty getStatus();
}
