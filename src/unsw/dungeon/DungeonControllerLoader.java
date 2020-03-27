package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> unmovableEntities;
    private List<ImageView> movableEntities;

    //Images
    private Image playerImage;
    private Image attackImage;
    private Image wallImage;
    private Image exitImage;
    private Image treasureImage;
    private Image doorOpenImage;
    private Image doorCloseImage;
    private Image keyImage;
    private Image boulderImage;
    private Image floorSwitchImage;
    private Image portalImage;
    private Image enemyImage;
    private Image dogImage;
    private Image knightImage;
    private Image damagedKnightImage;
    private Image graveImage;
    private Image swordImage;
    private Image potionImage;
    

    public DungeonControllerLoader(String filename)
            throws FileNotFoundException {
        super(filename);
        unmovableEntities = new ArrayList<>();
        movableEntities = new ArrayList<>();
        playerImage = new Image("/human_new.png");
        attackImage = new Image("/sword_human.png");
        wallImage = new Image("/stoneWall.png");
        exitImage = new Image("/exit.png");
        treasureImage = new Image("/gold_pile.png");
        doorOpenImage = new Image("/open_door.png");
        doorCloseImage = new Image("/closed_door.png");
        keyImage = new Image("/key.png");
        boulderImage = new Image("/boulder.png");
        floorSwitchImage = new Image("/pressure_plate.png");
        portalImage = new Image("/portal.png");
        enemyImage = new Image("/deep_elf_master_archer.png");
        dogImage = new Image("/hound.png");
        knightImage = new Image("/knight.png");
        damagedKnightImage = new Image("/knightDamaged.png");
        graveImage = new Image("/cursed_grave.png");
        swordImage = new Image("/greatsword_1_new.png");
        potionImage = new Image("/brilliant_blue_new.png");
    }

    @Override
    public void onLoad(Player player) {
        ImageView view1 = new ImageView(playerImage);
        ImageView view2 = new ImageView(attackImage);
        trackPlayerSword(player, view1, view2);
        trackPlayerInvincible(player, view1, view2);
        addMovableEntity(player, view1);
        addMovableEntity(player, view2);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addUnmovableEntity(wall, view);
    }
    
	@Override
	public void onLoad(Exit exit) {
		ImageView view = new ImageView(exitImage);
        addUnmovableEntity(exit, view);
	}

	@Override
	public void onLoad(Treasure treasure) {
		ImageView view = new ImageView(treasureImage);
        addUnmovableEntity(treasure, view);
	}

	@Override
	public void onLoad(Door door) {
		ImageView view1 = new ImageView(doorCloseImage);
		ImageView view2 = new ImageView(doorOpenImage);
		trackDoorStatus(door, view1, view2);
        addUnmovableEntity(door, view1);
        //trackDoorStatus(door, view2);
        addUnmovableEntity(door, view2);
	}

	@Override
	public void onLoad(Key key) {
		ImageView view = new ImageView(keyImage);
        addUnmovableEntity(key, view);
	}

	@Override
	public void onLoad(Boulder boulder) {
		ImageView view = new ImageView(boulderImage);
        addMovableEntity(boulder, view);
	}

	@Override
	public void onLoad(FloorSwitch floorSwitch) {
		ImageView view = new ImageView(floorSwitchImage);
        addUnmovableEntity(floorSwitch, view);
	}

	@Override
	public void onLoad(Portal portal) {
		ImageView view = new ImageView(portalImage);
        addUnmovableEntity(portal, view);
	}

	@Override
	public void onLoad(Enemy enemy) {
		ImageView view1 = new ImageView();
		if (enemy.getType().compareTo("elf") == 0) {
			view1.setImage(enemyImage);
		} else if (enemy.getType().compareTo("dog") == 0) {
			view1.setImage(dogImage);
		} else if (enemy.getType().compareTo("knight") == 0) {
			view1.setImage(knightImage);
			ImageView view3 = new ImageView(damagedKnightImage);
			trackEnemyLife(enemy, view1, view3);
			addMovableEntity(enemy, view3);
		}
		ImageView view2 = new ImageView(graveImage);
        trackEnemyAlive(enemy, view1, view2);
		addMovableEntity(enemy, view1);
        addMovableEntity(enemy, view2);
	}

	@Override
	public void onLoad(Sword sword) {
		ImageView view = new ImageView(swordImage);
        addUnmovableEntity(sword, view);
	}

	@Override
	public void onLoad(Potion potion) {
		ImageView view = new ImageView(potionImage);
        addUnmovableEntity(potion, view);
	}

    private void addUnmovableEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        trackVisibility(entity, view);
        unmovableEntities.add(view);
    }
    
    private void addMovableEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        trackVisibility(entity, view);
        movableEntities.add(view);
    }
    

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity The entity whose visibility has changed.
     * @param node The node of entity on the grid pane.
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });
        entity.getVisibility().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.booleanValue()) node.setVisible(false);				
			}
        });
    }
    
    /**
     * Sets up observers to track entities visibility. If the entities visibility is
     * false then the entity disappears from the UI and vice versa.
     * @param entity The entity whose visibility has changed.
     * @param node The node of entity on the grid pane.
     */
    private void trackVisibility(Entity entity, Node node) {
    	entity.getVisibility().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.booleanValue()) {
					node.setVisible(true);				
				} else {
					node.setVisible(false);			
				}
			}
        });
    }
    
    /**
     * Sets up observers to track if the door is open. If open then displays image of
     * door open otherwise will display image of door closed.
     * @param door The door entity whose status of being opened is being tracked.
     * @param close The node of the closed door entity on the grid pane.
     * @param open The node of the open door entity on the grid pane.
     */
    private void trackDoorStatus(Door door, Node close, Node open) {
    	open.setVisible(false);
    	door.getOpen().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.booleanValue()) {
					System.out.println("Gets here");
					close.setVisible(false);
					open.setVisible(true);
				} else {
					close.setVisible(true);
					open.setVisible(false);
				}
			}
    		
    	});
    }
    
    /**
     * Sets up observers to track if the player has a sword or not. Changes sprites
     * based on whether player has sword.
     * @param player The player entity who holds the sword.
     * @param normal The sprite containing the the normal human.
     * @param swordsman The sprite containing the swordsman.
     */
    private void trackPlayerSword(Player player, Node normal, Node swordsman) {
    	swordsman.setVisible(false);
    	player.getHasSword().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.booleanValue()) {
					normal.setVisible(false);
					swordsman.setVisible(true);
				} else {
					normal.setVisible(true);
					swordsman.setVisible(false);
				}
				
			}
    	});
    }
    
    /**
     * Tracks whether the player is invinvicible or not. If the player is invincible,
     * then add a glow effect otherwise remove the glow effect.
     * @param player The player entity who holds the sword.
     * @param normal The sprite containing the the normal human.
     * @param swordsman The sprite containing the swordsman.
     */
    private void trackPlayerInvincible(Player player, Node normal, Node swordsman) {
    	Glow glow = new Glow();
    	glow.setLevel(0.9);
    	player.getIsInvincible().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.booleanValue()) {
					normal.setEffect(glow);
					swordsman.setEffect(glow);
				} else {
					normal.setEffect(null);
					swordsman.setEffect(null);
				}
			}
    		
    	});
    }
    
    /**
     * Sets up observers to track whether enemy is alive. If the enemy is dead replace
     * its sprite with a grave.
     * @param enemy The enemy entity who is attacking the player.
     * @param alive The sprite containing the alive enemy.
     * @param dead The sprite containing the grave.
     */
    private void trackEnemyAlive(Enemy enemy, Node alive, Node dead) {
    	dead.setVisible(false);
    	enemy.getAlive().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.booleanValue() == false) {
					alive.setVisible(false);
					dead.setVisible(true);
				} else {
					alive.setVisible(true);
					dead.setVisible(false);
				}
			}
    		
    	});
    }
    
    /**
     * Sets up observers for selected enemies that have more than one life. If enemy
     * is damaged then sprite is changed to display the said damage.
     * @param enemy The enemy entity who is attacking the player.
     * @param full The sprite of an undamaged enemy. 
     * @param partial The sprite of a damaged enemy.
     */ 
    private void trackEnemyLife(Enemy enemy, Node full, Node partial) {
    	partial.setVisible(false);
    	enemy.getLife().addListener(new ChangeListener<Number> () {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() == 1) {
					full.setVisible(false);
					partial.setVisible(true);
				} else if (newValue.intValue() == 0) {
					partial.setVisible(false);
				}
			}
    	});
    }
    
    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * @return The controller with required parameters.
     * @throws FileNotFoundException
     */
    public DungeonController loadController() throws FileNotFoundException {
        return new DungeonController(load(), unmovableEntities, movableEntities);
    }

}
