/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height;
    private ArrayList<Entity> entities;
    private Player player;
    private Objective objective;
    
    /**
     * Constructor for dungeon when empty
     */
    public Dungeon() {
    	
    }
    
    /**
     * Constructor for dungeon when width and height is given
     * @param width The width of dungeon
     * @param height The height of dungeon
     */
    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.objective = null;
    }

    /**
     * Getter of dungeon width
     * @return Width of dungeon
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter of dungeon height
     * @return Height of dungeon
     */
    public int getHeight() {
        return height;
    }

    /**
     * Getter of player of the dungeon
     * @return The player of the dungeon
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Setter for player
     * @param player The player set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Add entity into the entity list
     * @param entity The entity to be added into the entity list
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }
    
    /**
     * Remove entity from the entity list
     * @param entity Entity to be removed from entity list
     */
    public void removeEntity(Entity entity) {
    	this.entities.remove(entity);
    	
    }

    /**
     * Getter for exit
     * @return Exit
     */
    public Exit getExit() {
    	Exit newObj = new Exit();
    	for (Entity obj : entities) {
    		if (obj.getDescription().compareTo("Exit") == 0) {
    			newObj = (Exit) obj;
    			break;
    		}
    	}
    	return newObj;
    }
    
    /**
     * Number of a specific entity e.g. Player
     * @param type String of the type of the entity
     * @return Number of entities of specified type
     */
    public int numOfClass(String type) {
    	int num = 0;
    	for (Entity obj : entities) {
    		if (obj.getDescription().compareTo(type) == 0) num++;
    	}
    	return num;
    }
    
    /**
     * Setter for objective
     * @param objective The objective to be set
     */
    public void setObjective(Objective objective) {
    	this.objective = objective;
    }
    
    /**
     * Getter for objective
     * @return This dungeons objective
     */
    public Objective getObjective() {
    	return objective;
    }	

    /**
     * Updates all the objects
     */
    public void updateObjectives() {
    	objective.updateStatus();
    	System.out.println("Gets here " + objective.getStatusBoolean());
    	if (objective.getStatusBoolean()) System.out.println("Game is finished.");
    }
    
    /**
     * Check whether they are AND, OR or base objective
     * @param type Type of objective wanted
     */
    public void forceCheckObjectiveParameters(String type) {
    	if (objective.getDescription().compareTo(type) == 0) {
    		objective.forceUpdateParameter(type);
    		objective.updateStatus();
		} else if (objective.getDescription().compareTo("AND") == 0) {
			objective.forceUpdateParameter(type);
			objective.updateStatus();
			
			// Shows exit if it is the last objective left for AND Objectives.
			AndCompositeObjective obj = (AndCompositeObjective) objective;
			obj.showExit();
		} else if (objective.getDescription().compareTo("OR") == 0) {
			objective.forceUpdateParameter(type);
			objective.updateStatus();
			
			// Shows exit if it an objective for OR Objectives.
			OrCompositeObjective obj = (OrCompositeObjective) objective;
			obj.showExit();
		}
    }
    
    /**
     * Get entity list
     * @return Entity list
     */
    public ArrayList<Entity> getEntityList() {
    	return entities;
    }
    
    /**
     * Returns the first instance of a specific type of entity if it exists.
     * @param type A string description of the type of entity being searched for.
     * @return An entity object that either matches required parameters or null.
     */
    public Entity getSpecificEntity(String type) {
    	Entity obj = null;
    	for (Entity ent : entities) {
    		if (ent.getDescription().compareTo(type) == 0) {
    			obj = ent;
    			break;
    		}
    	}
    	return obj;
    }
    
    /**
     * Performs a tick per second for all entities that perform an action every
     * 1 second.
     */
    public void fullTick() {
    	for (Entity ent : entities) {
    		if (ent.getDescription().compareTo("Enemy") == 0) {
    			Enemy obj = (Enemy) ent;
    			if (obj.isAlive()) {
    				if (obj.getType().compareTo("elf") == 0) {
    					obj.move(getPlayer());
    				} 
    				if (obj.getType().compareTo("knight") == 0) {
    					obj.move(getPlayer());
    				}
    			}
    		}
    	}
    	if (player.getPotTimeLeft() > 0) {
    		player.decreasePotionTime();
    	} else if (player.getPotTimeLeft() == 0 && player.getIsInvincible().getValue()) {
    		player.removePotion();
    	}
    }
    /**
     * Performs a tick per half second for all entities that perform an action
     * every 0.5 seconds.
     */
    public void halfTick() {
    	for (Entity ent : entities) {
    		if (ent.getDescription().compareTo("Enemy") == 0) {
    			Enemy obj = (Enemy) ent;
    			if (obj.isAlive()) {
    				if (obj.getType().compareTo("dog") == 0) {
    					obj.move(getPlayer());
    				}
    				
    			}
    		}
    	}
    }
}
