package unsw.dungeon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {
	private String fontPath = "src/resources/font/pixel.ttf";
	
    @FXML
    private GridPane squares;
    
    @FXML
    private Label objectives;
    
    @FXML
    private Pane original;

    private Stage popupStage;
    
    private Stage deathStage;
    
    private Stage finishedStage;
    
    private List<ImageView> unmovableEntities;
    
    private List<ImageView> movableEntities;

    private Player player;

    private Dungeon dungeon;
    
    private Timeline timeline;
    
    private int sideBarWidth;
    
    private StartScreen startscreen;

    /**
     * Constructor for the dungeon controller, which handles UI interactions with
     * the backend.
     * @param dungeon The dungeon the controller handles interactions with.
     * @param unmovableEntities Entities that cannot move.
     * @param movableEntities Entities that can move.
     */
    public DungeonController(Dungeon dungeon, List<ImageView> unmovableEntities, 
    		List<ImageView> movableEntities) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.unmovableEntities = new ArrayList<>(unmovableEntities);
        this.movableEntities = new ArrayList<>(movableEntities);
        this.sideBarWidth = 8;
        
        this.popupStage = null;
        this.deathStage = null;
        this.finishedStage = null;
        
        // Sets up a listener for if the player is alive.
        player.getIsAlive().addListener(new ChangeListener<Boolean> () {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue.booleanValue()) {
					deathStage.show();
				}
				
			}
        });
        
        // Sets up a listener for if objective is completed.
        dungeon.getObjective().getStatus().addListener(new ChangeListener<Boolean> () {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.booleanValue()) {
					finishedStage.show();
				}
			}
        	
        });
    }
    
    /**
     * Initialises the dungeon map.
     */
    @FXML
    public void initialize() {
    	this.objectives = new Label();
        Image ground = new Image("/newOtherGround.png");
        squares.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        
        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }
        
        // Sets up required UI designs for inventory and objectives.
        this.setInventoryName();
        this.setSlotsUp();
        this.setObjectiveFrame();
        
        
        // Adds all unmovable entities first.
        for (ImageView entity : unmovableEntities)
            squares.getChildren().add(entity);
        
        // Then adds all movable entities next.
        for (ImageView entity : movableEntities) {
        	squares.getChildren().add(entity);
        }
        
        // Generates a pause screen.
        popupStage = genPauseScreen();
        
        // Generates a death screen.
        deathStage = generalScreen("Wasted", 25);
        
        // Generates a finished game screen.
        finishedStage = generalScreen("Congratulations", 50);
        
        //Timeline loop
        timeline = new Timeline(
	    			new KeyFrame(Duration.seconds(0.5), e-> {
	    				dungeon.halfTick();
	    			}),
        			new KeyFrame(Duration.seconds(1.0), e -> {
        				dungeon.fullTick();
        				updateLabels();
        			})

        		);
        timeline.setCycleCount(Timeline.INDEFINITE);
        
    
    }
    
    /**
     * Updates the side panel holding inventory and objectives.
     */
    public void updateLabels() {
    	objectives.setText(dungeon.getObjective().objectiveStatus(""));
    	this.updateInventory();
	}
    
    /**
     * Sets up the banner holding the objective title and objectives.
     */
    public void setObjectiveFrame() {
    	// Creates label and adds to squares.
    	Image objectiveName = new Image("/inventoryName.png");
        Label object = new Label("Objectives");
        object.setPadding(new Insets(5, 5, 5, 10));
        object.setTextFill(Color.DARKGRAY);
        squares.add(new ImageView(objectiveName), dungeon.getWidth(), 4, sideBarWidth, 1);
        squares.add(object,dungeon.getWidth(), 4, sideBarWidth, 1);
        
        // Creates background image for objectives and add it to squares.
        Image objBack = new Image("/objectiveBack.png");
        squares.add(new ImageView(objBack), dungeon.getWidth(), 5, sideBarWidth, 10);
        objectives.setText(dungeon.getObjective().objectiveStatus(""));
        int objDepth = dungeon.getObjective().getObjectiveDepth();
        objectives.setPadding(new Insets(10,10,10,10));
        objectives.setTextFill(Color.DARKGRAY);
        squares.add(objectives, dungeon.getWidth(), 4, sideBarWidth, objDepth+1);
    }
    
    /**
     * Sets up the banner holding the title inventory.
     */
    public void setInventoryName() {
    	// Creates label and adds to squares.
        Image inventoryName = new Image("/inventoryName.png");
        Label invent = new Label("Inventory");
        invent.setTextFill(Color.DARKGRAY);
        invent.setPadding(new Insets(5, 5, sideBarWidth, 10));
        squares.add(new ImageView(inventoryName), dungeon.getWidth(), 0, sideBarWidth, 1);
        squares.add(invent,dungeon.getWidth(), 0, sideBarWidth, 1);
    }
    
    /**
     * Sets the inventory slots up with requried sprites.
     */
    public void setSlotsUp() {
    	Image slot = new Image("/emptySlot.png");
    	for (int x = dungeon.getWidth(); x < dungeon.getWidth() + sideBarWidth; x++) {
        	for (int y = 1; y < 4; y++) {
                squares.add(new ImageView(slot), x, y);
            }
        }
    }
    
    /**
     * Updates the inventory slots with new sprites.
     */
    public void updateInventory() {
    	this.setSlotsUp();
    	
    	boolean swordShown = false;
    	boolean potionShown = false;
    	boolean keyShown = false;
    	int treasureShown = 0;
    	
    	// Cycles through the slots and adds required sprites based on
    	// player inventory.
    	for (int y = 1; y < 4; y++) {
    		for (int x = dungeon.getWidth(); x < dungeon.getWidth() + sideBarWidth; x++) {
    			if (player.hasSword() && swordShown == false) {
        			// Sword based on durability.
    				if (player.getSword().getDurability() == 5) {
    					Image sword = new Image("/greatsword_1_new.png");
    					squares.add(new ImageView(sword), x, y);
    				} else if (player.getSword().getDurability() <= 4 
    						&& player.getSword().getDurability() >= 2) {
    					Image sword = new Image("/brokenSword.png");
    					squares.add(new ImageView(sword), x, y);
    				} else {
    					Image sword = new Image("/brokenSword2.png");
    					squares.add(new ImageView(sword), x, y);
    				}
                	swordShown = true;
                } else if (player.hasPotion() && potionShown == false) {
                	// Potion
                	Image potion = new Image("/brilliant_blue_new.png");
                	squares.add(new ImageView(potion), x, y);
                	potionShown = true;
                } else if (player.hasKey() && keyShown == false) {
                	// Key
                	Image key = new Image("/key.png");
                	squares.add(new ImageView(key), x, y);
                	keyShown = true;
                } else if (player.getTreasure() > 0 && treasureShown != player.getTreasure()) {
                	// Gold
                	Image gold = new Image("/gold_pile.png");
                	squares.add(new ImageView(gold), x, y);
                	treasureShown++;
                }
    		}
    	}
    }
    
    /**
     * Resets the dungeon entities.
     */
    public void reset() {
    	for (Entity e : dungeon.getEntityList()) {
    		e.resetEntity();
    	}
    	Objective obj = dungeon.getObjective();
    	obj.resetObjective();
    	updateLabels();
    }
    
    /**
     * Pauses or resumes the timeline based on whether the scene is already
     * paused.
     */
    public void pauseResume() {  	           
    	if (timeline.getStatus().equals(Status.PAUSED)) {
    		squares.setEffect(null);
    		popupStage.hide();
    		resume();
    	} else {
    		squares.setEffect(new GaussianBlur());
            popupStage.show();
    		pause();
    	}
    }
    
    /**
     * Pauses the timeline.
     */
    public void pause() {
    	timeline.pause();
    }
    
    /**
     * Resumes the timeline.
     */
    public void resume() {
    	timeline.play();
    }
    
    /**
     * Setter for the start screen.
     * @param startScreen The start screen of the game.
     */
    public void setStartScreen(StartScreen startScreen) {
    	this.startscreen = startScreen;
    }
    
    /**
     * Creates a modal for the pause screen.
     * @return The modal containing the pause screen.
     */
    public Stage genPauseScreen() {
    	// Creates a label holding the title.
    	Label pause = new Label("Paused");
    	try {
    		pause.setFont(Font.loadFont(new FileInputStream(fontPath), 25));
    	} catch (FileNotFoundException e) {
    		pause.setFont(Font.font("Verdana", 20));
    	}
    	// Creats a box to hold all the contents of the pause screen.
    	VBox pauseRoot = new VBox(5);
    	pauseRoot.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        pauseRoot.getChildren().add(pause);
        pauseRoot.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        pauseRoot.setAlignment(Pos.CENTER);
        pauseRoot.setPadding(new Insets(20));
        
        // Creates a button for resume.
        Button resume = new MenuButton("Resume");
        pauseRoot.getChildren().add(resume);
        
        // Creates a button for resume.
        Button reset = new MenuButton("Reset");
        pauseRoot.getChildren().add(reset);
        
        // Creates a button to return to the menu.
        Button menu = new MenuButton("Main Menu");
        pauseRoot.getChildren().add(menu);
        
        // Creates the stage and scene.
		Stage stage = new Stage(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(pauseRoot, Color.TRANSPARENT);
        stage.setScene(scene);
        
        // Sets up an event handler if button is clicked for resume.
        resume.setOnAction(event -> {
        	popupStage.hide();
        	pauseResume();
        });
        
        // Sets up an event handler if button is clicked for reset.
        reset.setOnAction(event -> {
        	this.reset();
        	squares.setEffect(null);
        	popupStage.hide();
        	resume();
        });
        
        // Sets up an event handler if button is clicked for menu.
        menu.setOnAction(event -> {
        	this.reset();
        	squares.setEffect(null);
        	popupStage.hide();
        	startscreen.start();
        });
        
        // Sets up event handler to check keypresses while in pause menu.
        scene.setOnKeyPressed(event -> {
        	handleKeyPress(event);
        });

        return stage;
    }
    
    /**
     * Generates a general screen based on a message and size of message.
     * @param message The message to be displayed.
     * @param size The font size of the message.
     * @return The stage which holds all the required elements.
     */
    public Stage generalScreen(String message, int size) {
    	// Creates a label holding the title.
    	Label finished = new Label(message);
    	try {
    		finished.setFont(Font.loadFont(new FileInputStream(fontPath), size));
    	} catch (FileNotFoundException e) {
    		finished.setFont(Font.font("Verdana", size));
    	}
    	
    	// Creats a box to hold all the contents of the pause screen.
    	VBox finishedRoot = new VBox(5);
    	finishedRoot.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    	finishedRoot.getChildren().add(finished);
    	finishedRoot.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
    	finishedRoot.setAlignment(Pos.CENTER);
    	finishedRoot.setPadding(new Insets(20));
        
        // Creates a button for resume.
        Button reset = new MenuButton("Reset");
        finishedRoot.getChildren().add(reset);
        
        // Creates a button to return to the menu.
        Button menu = new MenuButton("Main Menu");
        finishedRoot.getChildren().add(menu);
        
        // Creates the stage and scene.
		Stage stage = new Stage(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(finishedRoot, Color.TRANSPARENT);
        stage.setScene(scene);
    	
        // Sets up an event handler if button is clicked for reset.
        reset.setOnAction(event -> {
        	if (message.compareTo("Congratulations") == 0) {
        		finishedStage.hide();
        	} else if (message.compareTo("Wasted") == 0) {
        		deathStage.hide();
        	}
        	this.reset();
        	resume();
        });
        
        // Sets up an event handler if button is clicked for menu.
        menu.setOnAction(event -> {
        	this.reset();
        	pause();
        	squares.setEffect(null);
        	if (message.compareTo("Congratulations") == 0) {
        		finishedStage.hide();
        	} else if (message.compareTo("Wasted") == 0) {
        		deathStage.hide();
        	}
        	startscreen.start();
        });
        
        return stage;
    }
    
    /**
     * Getter for the timeline of the game.
     * @return The timeline fo the game.
     */
    public Timeline getTimeline() {
    	return this.timeline;
    }
    
    /**
     * Handles any key press event.
     * @param event The event code.
     */
    @FXML
    public void handleKeyPress(KeyEvent event) {
    	if (!timeline.getStatus().equals(Status.PAUSED)) {
    		switch (event.getCode()) {
            case W:
                player.moveUp();
                break;
            case S:
                player.moveDown();
                break;
            case A:
                player.moveLeft();
                break;
            case D:
                player.moveRight();
                break;
            case ENTER:
            	player.attackEnemy();
            	break;
            case Q:
            	player.dropKey();
            	break;
            case R:
            	reset();
            case ESCAPE:
            	pauseResume();
            	break;
            default:
                break;
            }
    	} else {
    		switch (event.getCode()) {
        	case ESCAPE:
        		pauseResume();
        		break;
			default:
				break;
        	}
    	}
    }
    

}

