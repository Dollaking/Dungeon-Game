package unsw.dungeon;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;

public class StartController {
	private PlayScreen playScreen;
	private HelpScreen helpScreen;
	
	@FXML
	private AnchorPane mainPane;
	private int MenuStartX;
	private int MenuStartY;
	private ArrayList <MenuButton>menuList;
	private String backgroundStyle;
	
	public StartController() {
		MenuStartX = 300;
		MenuStartY = 150;
		menuList = new ArrayList<MenuButton>();
	}
	
	@FXML
	public void initialize() {
		MenuButton playButton = new MenuButton("Play");
		MenuButton helpButton = new MenuButton("Help");
		MenuButton exitButton = new MenuButton("Exit");
		addToMenu(playButton);
		addToMenu(helpButton);
		addToMenu(exitButton);
		
		playButton.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent e) {
				playScreen.start();
			}
		});
		
		helpButton.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent e) {
				helpScreen.start();
			}
		});
		
		exitButton.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent e) {
				System.exit(0);
			}
		});
		
		
	}
	
	private void addToMenu(MenuButton b) {
		b.setLayoutX(MenuStartX);
		b.setLayoutY(MenuStartY + (menuList.size() * 75));
		mainPane.getChildren().add(b);
		menuList.add(b);
	}
	
	

	public void setPlayScreen(PlayScreen playScreen) {
		this.playScreen = playScreen;
	}
	
	public void setHelpScreen(HelpScreen helpScreen) {
		this.helpScreen = helpScreen;
	}
	
	public void setPaneBackground() {
		BackgroundImage image = new BackgroundImage(new Image("/UI/stonebrick.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, null);
		mainPane.setBackground(new Background(image));
	}

}
