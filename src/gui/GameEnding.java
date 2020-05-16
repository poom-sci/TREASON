package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class GameEnding extends AnchorPane {
	
	
	private Rectangle background;
	private ImageView statusImage;
	private boolean isWin;
	private int point;
	private double time;
	private TextField textField;
	private GameButton submitButton;
	private GameButton exitButton;
	
//	private final String FONT_PATH = "PixelTakhisis-ZajJ.ttf";
//	private final String BACKGROUND_IMAGE = "board.png";

	public GameEnding(boolean isWin,int point,double time) {
		super();
		this.isWin=isWin;
		this.point=point;
		this.time=time;

		createBackground();
		createStatusImage();
		createTextBox();
		createSubmitButton();
		createExitButton();
	}
	
	private void createBackground() {
		background=new Rectangle(1280,720);
		background.setFill(Color.BLACK);
		background.setOpacity(0.8);
		this.getChildren().add(background);
	}
	
	private void createStatusImage() {
		if(isWin) {
			statusImage = new ImageView("win.png");
			statusImage.setLayoutX(280);
			statusImage.setLayoutY(-100);
			statusImage.setFitHeight(800);
			statusImage.setFitWidth(800);
		}else {
			statusImage = new ImageView("gameover.png");
			statusImage.setLayoutX(1280 / 2 - 300 * 1.5);
			statusImage.setLayoutY(50);
			statusImage.setFitHeight(300 * 1.5);
			statusImage.setFitWidth(600 * 1.5);
		}
		
//		statusImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
//
//			@Override
//			public void handle(MouseEvent event) {
//				statusImage.setEffect(new DropShadow());
//				AudioClip mouse_enter_sound = new AudioClip(
//						ClassLoader.getSystemResource("mouse_enter_sound.wav").toString());
//				mouse_enter_sound.setVolume(0.1);
//				mouse_enter_sound.play();
//
//			}
//		});
//
//		statusImage.setOnMouseExited(new EventHandler<MouseEvent>() {
//
//			@Override
//			public void handle(MouseEvent event) {
//				// TODO Auto-generated method stub
//				statusImage.setEffect(null);
//			}
//
//		});
//		
		this.getChildren().add(statusImage);
//		
	}
	
	private void createTextBox() {
		Label nameScores = new Label("Name :");
		Label scoreScores = new Label("Score : " + point);
		Label timeScores = new Label("Time : " + time);
		
		textField = new TextField();
		try {
			nameScores.setTextFill(Color.web("EA8F3C"));
			nameScores.setFont(Font.loadFont(new FileInputStream("res/PixelTakhisis-ZajJ.ttf"), 23));

			scoreScores.setTextFill(Color.web("EA8F3C"));
			scoreScores.setFont(Font.loadFont(new FileInputStream("res/PixelTakhisis-ZajJ.ttf"), 23));

			timeScores.setTextFill(Color.web("EA8F3C"));
			timeScores.setFont(Font.loadFont(new FileInputStream("res/PixelTakhisis-ZajJ.ttf"), 23));

			textField.setFont(Font.loadFont(new FileInputStream("res/PixelTakhisis-ZajJ.ttf"), 23));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		nameScores.setLayoutX(500);
		nameScores.setLayoutY(408);
		scoreScores.setLayoutX(500);
		scoreScores.setLayoutY(460);
		timeScores.setLayoutX(500);
		timeScores.setLayoutY(510);
		textField.setLayoutX(600);
		textField.setLayoutY(400);
		
		this.getChildren().addAll(nameScores,scoreScores,timeScores,textField);

	}
	
	private void createSubmitButton() {
		
		submitButton = new GameButton("submit");
		this.getChildren().add(submitButton);
		submitButton.setLayoutX(400);
		submitButton.setLayoutY(550);
		
	}
	
	private void createExitButton() {
		
		exitButton = new GameButton("Exit");
		this.getChildren().add(exitButton);
		exitButton.setLayoutX(650);
		exitButton.setLayoutY(550);
		
	}

	public TextField getTextField() {
		return textField;
	}

	public GameButton getSubmitButton() {
		return submitButton;
	}

	public GameButton getExitButton() {
		return exitButton;
	}
	
	
	
	
	
	
}
