package unsw.dungeon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class PlayController {
	private DungeonScreen dungeonScreen;
	private StartScreen startScreen;
	private String fontPath;
	private ArrayList<MapMenu> mapList;
	private int index;
	@FXML
	private AnchorPane playMenu;
	
	public PlayController() {
		fontPath = "src/resources/font/pixel.ttf";
		index = 0;
	}
	
	public void initialize() {
		mapList = new ArrayList<MapMenu>();
		createStartButton();
		//createTitle();
		createSliderButton();
		loadMapPictures();
		showMapPicture(mapList.get(0));
		createCloseButton();
		
	}
	
	public void setDungeonScreen(DungeonScreen dungeonScreen) {
		this.dungeonScreen = dungeonScreen;
	}
	
	public void setStartScreen(StartScreen startScreen) {
		this.startScreen = startScreen;
	}
	
	public void createStartButton() {
		MenuButton startButton = new MenuButton("Start");
		startButton.setLayoutX(300);
		startButton.setLayoutY(500);
		playMenu.getChildren().add(startButton);
		startButton.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent e) {
				try {
					dungeonScreen.start(mapList.get(index).getMapFileName());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	public void createTitle() {
		Label title = new Label("SELECT A MAP");
		setButtonFont(title);
		title.setLayoutX(295);
		title.setLayoutY(50);
		playMenu.getChildren().add(title);
	}
	
	public void setButtonFont(Label l) {
		try {
			l.setFont(Font.loadFont(new FileInputStream(fontPath), 35));
		} catch (FileNotFoundException e) {
			l.setFont(Font.font("Verdana", 25));
		}
	}
	
	public void createSliderButton() {
		Button left = new Button();
		left.setStyle("-fx-font-size: 0pt; -fx-background-color: transparent; -fx-background-image: url('/UI/arrowSilver_left.png');");
		left.setPrefWidth(22);
		left.setPrefHeight(21);
		left.setMinHeight(21);
		left.setLayoutY(290);
		left.setLayoutX(50);
		
		Button right = new Button();
		right.setStyle("-fx-font-size: 0pt; -fx-background-color: transparent; -fx-background-image: url('/UI/arrowSilver_right.png');");
		right.setPrefWidth(22);
		right.setPrefHeight(21);
		right.setMinHeight(21);
		right.setLayoutY(290);
		right.setLayoutX(750);
		
		left.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent e) {
				index--;
				if(index < 0) {
					index = mapList.size() - 1;
				}
				showMapPicture(mapList.get(index));
				System.out.println(index);
			}
		});
		
		right.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent e) {
				index++;
				if (index > (mapList.size() - 1)) {
					index = 0;
				}
				
				showMapPicture(mapList.get(index));
				
			}
		});
		
		playMenu.getChildren().add(left);
		playMenu.getChildren().add(right);		
	}
	
	public void loadMapPictures() {
		MapMenu portals = new MapMenu(new MapPicture("/mapimage/portalsMap.png"), "portals.json");
		MapMenu puzzle = new MapMenu(new MapPicture("/mapimage/puzzleMapImage.png"), "puzzle.json");
		mapList.add(puzzle);
		mapList.add(portals);
	}
	
	public void showMapPicture(MapMenu mm) {
		for (MapMenu old : mapList) {
			playMenu.getChildren().remove(old.getPicture());
		}
		playMenu.getChildren().add(mm.getPicture());
		
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
		
		button.setOnMousePressed(new EventHandler<MouseEvent> () {
			@Override
			public void handle(MouseEvent e) {
				if (e.getButton().equals(MouseButton.PRIMARY)) {
					button.setStyle("-fx-font-size: 0pt; -fx-background-color: transparent; -fx-background-image: url('/UI/iconCross_beige.png');");
				}
			}
		});
		button.setOnMouseReleased(new EventHandler<MouseEvent> () {
			@Override
			public void handle(MouseEvent e) {
				if (e.getButton().equals(MouseButton.PRIMARY)) {
					button.setStyle("-fx-font-size: 0pt; -fx-background-color: transparent; -fx-background-image: url('/UI/iconCross_blue.png');");
				}
			}
		});
		
		button.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent e) {
				startScreen.start();
			}
		});
		
		playMenu.getChildren().add(button);
	}
	
	
}
