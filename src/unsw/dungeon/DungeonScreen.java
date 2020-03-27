package unsw.dungeon;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class DungeonScreen {
	
	private Stage stage;
	private String title;
	private DungeonController controller;
	private Scene scene;
	private Dungeon dungeon;
	private StartScreen startScreen;
	
	/**
	 * Creates a stage for a required map.
	 * @param stage The stage being used.
	 * @throws IOException If file does not exist.
	 */
	public DungeonScreen(Stage stage) throws IOException {
		this.stage = stage;
		title = "Dungeon";
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("test.json");       
        controller = dungeonLoader.loadController();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
		loader.setController(controller);
		
		Parent root = loader.load();
		scene = new Scene(root);
		root.requestFocus();
		controller.setStartScreen(startScreen);
	}
	
	/**
	 * Starts the dungeon game.
	 * @param fileName The file name of the map to be used.
	 * @throws IOException If the file does not exist.
	 */
	public void start(String fileName) throws IOException {
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(fileName);       
        controller = dungeonLoader.loadController();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
		loader.setController(controller);
		
		Parent root = loader.load();
		scene = new Scene(root);
		root.requestFocus();
		controller.setStartScreen(startScreen);
		
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
		controller.getTimeline().play();
	}
	
	/**
	 * Setter for the start screen of the game.
	 * @param startscreen The start screen of the game.
	 */
	public void setStartScreen(StartScreen startscreen) {
		this.startScreen = startscreen;
	}
	
	/**
	 * Getter for the controller of the game.
	 * @return The controller of the game.
	 */
	public DungeonController getController() {
		return controller;
	}

}
