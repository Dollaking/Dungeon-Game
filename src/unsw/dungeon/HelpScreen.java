package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelpScreen {
	private Stage stage;
	private String title;
	private HelpController controller;
	private Scene scene;
	
	public HelpScreen(Stage stage) throws IOException {
		this.stage = stage;
		title = "Dungeon Menu";
		controller = new HelpController();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("helpView.fxml"));
		loader.setController(controller);
		
		Parent root = loader.load();
		scene = new Scene(root, 800, 600);
	}

	public void start() {
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}
	
	public HelpController getConroller() {
		return controller;
	}
}
