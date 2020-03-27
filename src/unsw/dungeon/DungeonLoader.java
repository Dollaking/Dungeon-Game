package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;
    private Dungeon dungeon;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
        dungeon = new Dungeon();
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return A loaded up dungeon containing the required elements.
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        
        JSONObject jsonObjectives = json.getJSONObject("goal-condition");
        Objective objective = processObjective(jsonObjectives, dungeon);
        dungeon.setObjective(objective);
        
        // If exit is only objective then sets it to be visible.
        if (objective.getDescription().compareTo("Get Exit") == 0) {
			dungeon.getExit().setVisibility(true);
		} else if (objective.getDescription().compareTo("OR") == 0) {
			OrCompositeObjective obj = (OrCompositeObjective) objective;
			obj.showExit();
		}
        
        this.setDungeon(dungeon);
        return dungeon;
    }
    
    /**
     * Loads all entities into the dungeon.
     * @param dungeon The dungeon where game is being played.
     * @param json The json object thats gonna be loaded as an entity.
     */
    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");
        

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(x, y, dungeon);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y, dungeon);
            onLoad(wall);
            entity = wall;
            break;
        // TODO Handle other possible entities
        case "exit":
        	Exit exit = new Exit(x, y, dungeon);
        	onLoad(exit);
        	entity = exit;
        	break;
        case "treasure":
        	Treasure treasure = new Treasure(x, y, dungeon);
        	onLoad(treasure);
        	entity = treasure;
        	break;
        case "door":
        	Door door = new Door(x, y, json.getInt("id"), dungeon);
        	onLoad(door);
        	entity = door;
        	break;
        case "key":
        	Key key = new Key(x, y, json.getInt("id"), dungeon);
        	onLoad(key);
        	entity = key;
        	break;
        case "boulder":
        	Boulder boulder = new Boulder(x, y, dungeon);
        	onLoad(boulder);
        	entity = boulder;
        	break;
        case "switch":
        	FloorSwitch floorSwitch = new FloorSwitch(x, y, dungeon);
        	onLoad(floorSwitch);
        	entity = floorSwitch;
        	break;
        case "portal":
        	Portal portal = new Portal(x, y, json.getInt("id"), dungeon);
        	onLoad(portal);
        	entity = portal;
        	break;
        case "enemy":
        	Enemy enemy = new Enemy(x, y, dungeon, json.getString("enemyClass"));
        	onLoad(enemy);
        	entity = enemy;
        	break;
        case "sword":
        	Sword sword = new Sword(x, y, dungeon);
        	onLoad(sword);
        	entity = sword;
        	break;
        case "invincibility":
        	Potion potion = new Potion(x, y, dungeon);
        	onLoad(potion);
        	entity = potion;
        	break;
        }
        dungeon.addEntity(entity);
    }
    
    /**
     * Processes all the objectives into objective objects as recursive function.
     * @param jsonObjectives The JSON Object containing objective details.
     * @param dungeon The dungeon in which objectives are to be placed.
     * @return An objective object containing all objectives. 
     */
    public Objective processObjective(JSONObject jsonObjectives, Dungeon dungeon) {
    	Objective objective = null;
    	Object obj = null;
    	String goal = jsonObjectives.getString("goal");
    	if (goal.compareTo("AND") == 0) {
    		AndCompositeObjective newObj = new AndCompositeObjective(dungeon);
    		JSONArray array = jsonObjectives.getJSONArray("subgoals");
    		for (int i = 0; i < array.length(); i++) {
    			newObj.addObjective(processObjective(array.getJSONObject(i), dungeon));
    		}
     		objective = newObj;
    	} else if (goal.compareTo("OR") == 0) {
    		OrCompositeObjective newObj = new OrCompositeObjective(dungeon);
    		JSONArray array = jsonObjectives.getJSONArray("subgoals");
    		for (int i = 0; i < array.length(); i++) {
    			newObj.addObjective(processObjective(array.getJSONObject(i), dungeon));
    		}
    		objective = newObj;
    	} else {
    		Objective newObj = loadObjective(goal, dungeon);
    		objective = newObj;
    	}
    	return objective;
    }
    
    /**
     * Loads all basic objectives into an Objective object.
     * @param type The type of objective.
     * @param dungeon The dungeon in which objective that it takes place in.
     * @return The objective object that holds the objective.
     */
    public Objective loadObjective(String type, Dungeon dungeon) {
    	Objective objective = null;
    	switch (type) {
    	case "exit":
    		System.out.println("Loads up exit.");
    		GetExit newObj = new GetExit(dungeon.getExit(), dungeon);
    		objective = newObj;
    		break;
    	case "boulders":
    		BoulderToFloorSwitch newObj1 = new BoulderToFloorSwitch(dungeon.numOfClass("Floor Switch"), dungeon);
    		objective = newObj1;
    		break;
    	case "enemies":
    		KillEnemies newObj2 = new KillEnemies(dungeon.numOfClass("Enemy"), dungeon);
    		objective = newObj2;
    		break;
    	case "treasure":
    		CollectTreasure newObj3 = new CollectTreasure(dungeon.numOfClass("Treasure"), dungeon);
    		objective = newObj3;
    		break;
    	}
    	return objective;
    }
    
    /**
     * Sets the value of the dungeon attribute.
     * @param dungeon The dungeon attribute.
     */
    public void setDungeon(Dungeon dungeon) {
    	this.dungeon = dungeon;
    }
    
    /**
     * Returns the dungeon attribute for the game.
     * @return The dungeon attribute.
     */
    public Dungeon getDungeon() {
    	return this.dungeon;
    }

    public abstract void onLoad(Player player);

    public abstract void onLoad(Wall wall);
    
    // TODO Create additional abstract methods for the other entities
    
    public abstract void onLoad(Exit exit);
    
    public abstract void onLoad(Treasure treasure);

    public abstract void onLoad(Door door);
    
    public abstract void onLoad(Key key);
    
    public abstract void onLoad(Boulder boulder);
    
    public abstract void onLoad(FloorSwitch floorSwitch);
    
    public abstract void onLoad(Portal portal);
    
    public abstract void onLoad(Enemy enemy);
    
    public abstract void onLoad(Sword sword);
    
    public abstract void onLoad(Potion potion);

}
