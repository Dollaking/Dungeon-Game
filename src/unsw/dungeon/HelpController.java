package unsw.dungeon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class HelpController {
private StartScreen startScreen;
private String fontPath;

@FXML
private AnchorPane mainHelp;

@FXML
private AnchorPane descriptionPane;

@FXML
private Pane menuBanner;

@FXML
private Pane helpMenu;

private HelpDescription defaultDesc;
private HelpDescription wall;
private HelpDescription exit;
private HelpDescription treasure;
private HelpDescription door;
private HelpDescription key;
private HelpDescription boulder;
private HelpDescription floorswitch;
private HelpDescription portal;
private HelpDescription enemy;
private HelpDescription sword;
private HelpDescription potion;
private HelpDescription hound;
private HelpDescription enforcer;

private ArrayList<HelpDescription> helpList;


	public HelpController() {
		fontPath = "src/resources/font/pixel.ttf";
		helpList = new ArrayList<HelpDescription> ();
		defaultDesc = new HelpDescription();
		wall = new HelpDescription();
		exit = new HelpDescription();
		treasure = new HelpDescription();
		door = new HelpDescription();
		key = new HelpDescription();
		boulder = new HelpDescription();
		floorswitch = new HelpDescription();
		portal = new HelpDescription();
		enemy = new HelpDescription();
		sword = new HelpDescription();
		potion = new HelpDescription();
		hound = new HelpDescription();
		enforcer = new HelpDescription();
	}
	
	@FXML
	public void initialize() {
		descriptionPane.setStyle("-fx-border-color: black");
		helpMenu.setStyle("-fx-border-color: black");
		createCloseButton();
		createTitle();
		createContent();
		createDescriptions();
		showDescription(defaultDesc);
	}
	
	public void createTitle() {
		Text title = new Text("HELP MENU");
		setCustomFont(title, 50);
		title.setLayoutX(295);
		title.setLayoutY(50);
		menuBanner.getChildren().add(title);
	}
	
	public void createCloseButton() {
		Button button = new Button();
		button.setStyle("-fx-font-size: 0pt; -fx-background-color: transparent; -fx-background-image: url('/UI/iconCross_blue.png');");
		button.setPrefWidth(16);
		button.setPrefHeight(15);
		button.setMinHeight(15);
		button.setMinWidth(16);
		button.setLayoutY(20);
		button.setLayoutX(760);
		
		button.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent e) {
				startScreen.start();
			}
		});
		
		mainHelp.getChildren().add(button);
	}
	
	public void createContent() {
		Text controlTitle = new Text("Controls");
		controlTitle.setFill(Color.DARKRED);
		setCustomFont(controlTitle, 40);
		controlTitle.setLayoutY(25);
		controlTitle.setLayoutX(5);
		Text controlContext = new Text("Press 'W', 'S', 'A', 'D' to move UP, DOWN, LEFT, RIGHT respectively!\nPress 'R' to reset game\nPress 'ESC' to pause the game\nPress 'ENTER' to attack!\nPress 'Q' to drop your key!");
		controlContext.setFill(Color.WHITESMOKE);
		controlContext.setTextAlignment(TextAlignment.LEFT);
		setCustomFont(controlContext, 23);
		controlContext.setWrappingWidth(500);
		controlContext.setLayoutY(50);
		controlContext.setLayoutX(5);
		Text entityTitle = new Text("Entities");
		entityTitle.setFill(Color.DARKRED);
		setCustomFont(entityTitle, 40);
		entityTitle.setLayoutY(205);
		entityTitle.setLayoutX(5);
		
		createEntityButton("/stoneWall.png", this.wall, 5, 215);
		createEntityButton("/exit.png", this.exit, 60, 215);
		createEntityButton("/gold_pile.png", this.treasure, 115, 215);
		createEntityButton("/closed_door.png", this.door, 170, 215);
		createEntityButton("/key.png", this.key, 225, 215);
		createEntityButton("/boulder.png", this.boulder, 280, 215);
		createEntityButton("/pressure_plate.png", this.floorswitch, 335, 215);
		createEntityButton("/portal.png", this.portal, 390, 215);
		createEntityButton("/deep_elf_master_archer.png", this.enemy, 445, 215);
		createEntityButton("/greatsword_1_new.png", this.sword, 5, 265);
		createEntityButton("/knight.png", this.enforcer, 60, 265);
		createEntityButton("/hound.png", this.hound, 115, 265);
		
		
		helpMenu.getChildren().add(controlTitle);
		helpMenu.getChildren().add(controlContext);
		helpMenu.getChildren().add(entityTitle);
		
	}
	
	public void createEntityButton(String path, HelpDescription hd, double x, double y) {
		Button button = new Button();
		button.setStyle("-fx-background-color: transparent; -fx-background-image: url('/UI/buttonSquare_brown.png');");	
		button.setPrefHeight(45);
		button.setPrefWidth(45);
		button.setMinWidth(45);
		button.setMaxWidth(45);
		Image image = new Image(path);
		ImageView imageView = new ImageView(image);
		imageView.maxWidth(32);
		imageView.maxHeight(32);
		button.setGraphic(imageView);
		
		button.setOnMousePressed(new EventHandler<MouseEvent> () {
			@Override
			public void handle(MouseEvent e) {
				if (e.getButton().equals(MouseButton.PRIMARY)) {
					button.setStyle("-fx-background-color: transparent; -fx-background-image: url('/UI/buttonSquare_brown.png');");	
				}
			}
		});
		
		button.setOnMouseReleased(new EventHandler<MouseEvent> () {
			@Override
			public void handle(MouseEvent e) {
				if (e.getButton().equals(MouseButton.PRIMARY)) {
					button.setStyle("-fx-background-color: transparent; -fx-background-image: url('/UI/buttonSquare_brown_pressed.png');");	
				}
			}
		});
		
		button.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent e) {
				showDescription(hd);
			}
		});
		button.setLayoutY(y);
		button.setLayoutX(x);
		
		helpMenu.getChildren().add(button);
	}
	
	public void setCustomFont(Text text, int size) {
		try {
			text.setFont(Font.loadFont(new FileInputStream("src/resources/font/pixel.ttf"), size));
		} catch (FileNotFoundException e) {
			text.setFont(Font.font("Verdana", size));
		}
	}
	
	public void createDescriptions() {
		injectDescriptions(this.defaultDesc,"The description of your selected entity will appear here", "Description");
		injectDescriptions(this.wall, "The wall blocks the movement of you, enemies and boulders.", "Wall");
		injectDescriptions(this.exit, "If you go through it the puzzle is complete", "Exit");
		injectDescriptions(this.treasure, "You collect this in order to complete objectives", "Treasure");
		injectDescriptions(this.door, "The door can be unlocked using a key. If locked you can not go through the door", "Door");
		injectDescriptions(this.key, "The key can be picked up and unlock doors. However, each key can only unlock 1 specific door. You can only pick up one key at a time", "Key");
		injectDescriptions(this.boulder, "Boulders are movable objects which the player can interact with. The boulder can not go through any tangible objects e.g Enemies, walls", "Boulder");
		injectDescriptions(this.floorswitch, "Switches are activated when boulders are on top of it. Activating switches will complete objectives", "Floor Switch");
		injectDescriptions(this.portal, "Teleports you to another linked portal", "Portal");
		injectDescriptions(this.enemy, "The elf constantly moves towards you and if the elf touches you, you are eliminated. The elf will run away from you when you have an invincibility potion activated", "Elf");
		injectDescriptions(this.sword, "The sword can be picked up and it is used to kill enemies. The sword attacks everything in a 1 block radius. The sword has 5 durability, everytime you swing the sword, its durability decreases. You can only carry one sword at a time", "Sword");
		injectDescriptions(this.potion, "When you pick up the potion, it will be activated immediately. When you run into enemies while the potion is active, it will eliminate the enemy! Enemies will run away from you when potion is active", "Invincibility Potion");
		injectDescriptions(this.hound, "Hound is an enemy that moves two times faster than a normal enemy!", "Hound");
		injectDescriptions(this.enforcer, "Enforcer is a tough enemy that would take 2 sword hits to eliminate!", "Enforcer");
		
	}
	
	private void injectDescriptions(HelpDescription entity, String text, String title) {
		Text textTitle = new Text(title);
		textTitle.setFill(Color.DARKRED);
		textTitle.setLayoutY(25);
		textTitle.setLayoutX(5);
		setCustomFont(textTitle, 40);
		entity.setText(text);
		entity.setFill(Color.WHITESMOKE);
		entity.setTitle(textTitle);
		descriptionPane.getChildren().add(entity);
		descriptionPane.getChildren().add(textTitle);
		helpList.add(entity);
	}
	
	private void showDescription(HelpDescription hd) {
		for (HelpDescription desc : helpList) {
			desc.setVisible(false);
			desc.getTitle().setVisible(false);
		}
	
		hd.setVisible(true);
		hd.getTitle().setVisible(true);
	}
	

	public void setStartScreen(StartScreen startScreen) {
		this.startScreen = startScreen;
	}
}
