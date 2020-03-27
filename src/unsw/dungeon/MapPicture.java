package unsw.dungeon;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.Pane;

public class MapPicture extends Pane{

	public MapPicture(String imagePath) {
		setPrefWidth(500);
		setPrefHeight(300);
		BackgroundImage image = new BackgroundImage(new Image(imagePath), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
		setBackground(new Background(image));
		setLayoutX(150);
		setLayoutY(150);
		
	}
	
	
	
	

}
