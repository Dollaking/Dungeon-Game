package unsw.dungeon;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MenuButton extends Button{
	private String fontPath = "src/resources/font/pixel.ttf";
	private String buttonNormal = "-fx-background-color: transparent; -fx-background-image: url('/UI/buttonLong_blue.png');";
	private String buttonPressed = "-fx-background-color: transparent; -fx-background-image: url('/UI/buttonLong_blue_pressed.png');";
	
	public MenuButton(String text) {
		initialiseButtonListeners();
		setText(text);
		setButtonFont();
		setNormalStyle();
	}
	
	
	public void setButtonFont() {
		try {
			setFont(Font.loadFont(new FileInputStream(fontPath), 25));
		} catch (FileNotFoundException e) {
			setFont(Font.font("Verdana", 25));
		}
	}
	
	private void setNormalStyle() {
		setStyle(buttonNormal);		
		setPrefWidth(190);
		setPrefHeight(49);
		setLayoutY(getLayoutY() - 4);
		setTextFill(Color.BLACK);
	}
	
	private void setPressedStyle() {
		setStyle(buttonPressed);
		setPrefWidth(190);
		setPrefHeight(45);
		setLayoutY(getLayoutY() + 4);
		setTextFill(Color.WHITE);
	}
	
	private void initialiseButtonListeners() {
		
		setOnMousePressed(new EventHandler<MouseEvent> () {
			@Override
			public void handle(MouseEvent e) {
				if (e.getButton().equals(MouseButton.PRIMARY)) {
					setPressedStyle();
				}
			}
		});
		setOnMouseReleased(new EventHandler<MouseEvent> () {
			@Override
			public void handle(MouseEvent e) {
				if (e.getButton().equals(MouseButton.PRIMARY)) {
					setNormalStyle();
				}
			}
		});
		setOnMouseEntered(new EventHandler<MouseEvent> () {
			@Override
			public void handle(MouseEvent e) {
				if (e.getButton().equals(MouseButton.PRIMARY)) {
					setEffect(new DropShadow());
				}
			}
		});
		setOnMouseExited(new EventHandler<MouseEvent> () {
			@Override
			public void handle(MouseEvent e) {
				if (e.getButton().equals(MouseButton.PRIMARY)) {
					setEffect(null);
				}
			}
		});
		

		
	}
	

	public void normalButton(MouseEvent e) {
		if (e.getButton().equals(MouseButton.PRIMARY)) {
			Button b = (Button) e.getSource();
			System.out.println(b.getText());
		}
	}


}
