package unsw.dungeon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class HelpDescription extends Text{
	private Text title;
	
	public HelpDescription() {
		super();
		setCustomFont();
		setTextAlignment(TextAlignment.LEFT);
		setWrappingWidth(250);
		setLayoutY(50);
		setLayoutX(5);
		
		this.setVisible(false);
	}
	
	public void setCustomFont() {
		try {
			setFont(Font.loadFont(new FileInputStream("src/resources/font/pixel.ttf"), 20));
		} catch (FileNotFoundException e) {
			setFont(Font.font("Verdana", 20));
		}
	}
	
	public void setTitle(Text title) {
		this.title = title;
		this.title.setVisible(false);
	}
	
	public Text getTitle() {
		return this.title;
	}	
}
