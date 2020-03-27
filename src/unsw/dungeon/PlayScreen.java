package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PlayScreen {
	private Stage stage;
	private String title;
	private PlayController controller;
	private Scene scene;
	
	public PlayScreen(Stage stage) throws IOException {
		this.stage = stage;
		title = "Dungeon Menu";
		controller = new PlayController();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("playScreen.fxml"));
		loader.setController(controller);
		
		Parent root = loader.load();
		scene = new Scene(root, 800, 600);
	}

	public void start() {
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}
	
	public PlayController getConroller() {
		return controller;
	}
}
