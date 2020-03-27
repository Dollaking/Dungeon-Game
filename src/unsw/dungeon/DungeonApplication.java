package unsw.dungeon;        

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

public class DungeonApplication extends Application {
	
	/**
	 * Loads up required controller for the application.
	 * @param primaryStage The main stage to be used for said application.
	 */
    @Override
    public void start(Stage primaryStage) throws IOException {
    	DungeonScreen dungeonScreen = new DungeonScreen(primaryStage);
    	StartScreen startScreen = new StartScreen(primaryStage);
    	PlayScreen playScreen = new PlayScreen(primaryStage);
    	HelpScreen helpScreen = new HelpScreen(primaryStage);
    	
    	dungeonScreen.setStartScreen(startScreen);
    	startScreen.getConroller().setPlayScreen(playScreen);
    	startScreen.getConroller().setHelpScreen(helpScreen);
    	playScreen.getConroller().setDungeonScreen(dungeonScreen);
    	playScreen.getConroller().setStartScreen(startScreen);
    	helpScreen.getConroller().setStartScreen(startScreen);
    	startScreen.start();
    	
    }

    public static void main(String[] args) {
        launch(args);
    }
}
