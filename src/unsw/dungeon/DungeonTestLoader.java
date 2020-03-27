package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class DungeonTestLoader extends DungeonLoader {
	
	private ArrayList<ImageView> unmovableEntities;
	private ArrayList<ImageView> movableEntities;
	
	public DungeonTestLoader(String filename) throws FileNotFoundException {
		super(filename);
		unmovableEntities = new ArrayList<>();
		movableEntities = new ArrayList<>();
	}
	
	/**
	 * Set a node in a GridPane to have its position track the position of an
	 * entity in the dungeon.
	 *
	 * By connecting the model with the view in this way, the model requires no
	 * knowledge of the view and changes to the position of entities in the
	 * model will automatically be reflected in the view.
	 * @param entity
	 * @param node
	 */
	private void trackPosition(Entity entity) {
	    //GridPane.setColumnIndex(node, entity.getX());
	    //GridPane.setRowIndex(node, entity.getY());
	    entity.x().addListener(new ChangeListener<Number>() {
	        @Override
	        public void changed(ObservableValue<? extends Number> observable,
	                Number oldValue, Number newValue) {
	            //GridPane.setColumnIndex(node, newValue.intValue());
	        	System.out.println("Moved along x");
	        }
	    });
	    entity.y().addListener(new ChangeListener<Number>() {
	        @Override
	        public void changed(ObservableValue<? extends Number> observable,
	                Number oldValue, Number newValue) {
	            //GridPane.setRowIndex(node, newValue.intValue());
	        	System.out.println("Moved along y");
	        }
	    });
	}

	/**
	 * Create a controller that can be attached to the DungeonView with all the
	 * loaded entities.
	 * @return
	 * @throws FileNotFoundException
	 */
	public DungeonController loadController() throws FileNotFoundException {
	    return new DungeonController(load(), unmovableEntities, movableEntities);
	}

	@Override
    public void onLoad(Player player) {
        addEntity(player);
    }

    @Override
    public void onLoad(Wall wall) {
        addEntity(wall);
    }
    
	@Override
	public void onLoad(Exit exit) {
        addEntity(exit);
	}

	@Override
	public void onLoad(Treasure treasure) {
        addEntity(treasure);
	}

	@Override
	public void onLoad(Door door) {
        addEntity(door);
	}

	@Override
	public void onLoad(Key key) {
        addEntity(key);
	}

	@Override
	public void onLoad(Boulder boulder) {
        addEntity(boulder);
	}

	@Override
	public void onLoad(FloorSwitch floorSwitch) {
        addEntity(floorSwitch);
	}

	@Override
	public void onLoad(Portal portal) {
        addEntity(portal);
	}

	@Override
	public void onLoad(Enemy enemy) {
        addEntity(enemy);
	}

	@Override
	public void onLoad(Sword sword) {
        addEntity(sword);
	}

	@Override
	public void onLoad(Potion potion) {
        addEntity(potion);
	}

    private void addEntity(Entity entity) {
        trackPosition(entity);
        //entities.add(view);
    }
}

