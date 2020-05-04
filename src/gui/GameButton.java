package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;

public class GameButton extends Button {
	private final String FONT_PATH = "res/PixelTakhisis-ZajJ.ttf";
	private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('blue_button_pressed.png');";
	private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('blue_button.png');";


	public GameButton(String text) {
		setText(text);
		setButtonFont();
		setPrefHeight(83);
		setPrefWidth(210);
		setStyle(BUTTON_FREE_STYLE);
		initializeButtonListeners();
	}
	
	private void setButtonFont() {
		try {
		
			setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
		}
		catch (FileNotFoundException e) {
			setFont(Font.font("Codia", 23));
		}
	}
	
	private void setButtonPressedStyle() {
		setStyle(BUTTON_PRESSED_STYLE);
		setPrefHeight(73);
		setLayoutY(getLayoutY()+4);

	}

	private void setButtonFreeStyle() {
		setStyle(BUTTON_FREE_STYLE);
		setPrefHeight(83);
		setLayoutY(getLayoutY()-4);
	}
	
	private void initializeButtonListeners() {
		setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				setButtonPressedStyle();
				AudioClip mouse_pressed_sound = new AudioClip(ClassLoader.getSystemResource("mouse_pressed_sound.wav").toString());
				mouse_pressed_sound.setVolume(0.1);
				mouse_pressed_sound.play();
			}
		});
		
		setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				setButtonFreeStyle();
			}
		});
		
		setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				setEffect(new DropShadow());
				AudioClip mouse_enter_sound = new AudioClip(ClassLoader.getSystemResource("mouse_enter_sound.wav").toString());
				mouse_enter_sound.setVolume(0.1);
				mouse_enter_sound.play();
				
			}
		});
		
		setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				setEffect(null);
			}
			
		});
	}
	
}
