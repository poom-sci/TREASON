package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class PlayerInfoBox extends AnchorPane {
	
	private Rectangle background;
	private ImageView weaponImage;
	private Label number;
	private Rectangle playerHP;
	
//	private final String FONT_PATH = "PixelTakhisis-ZajJ.ttf";
//	private final String BACKGROUND_IMAGE = "board.png";

	public PlayerInfoBox() {
		super();

		createBackground();
		createWeaponImage();
		createNumber();
		createPlayerHp();


	}

	private void createBackground() {
		background = new Rectangle(250, 120);
		background.setFill(Color.ANTIQUEWHITE);
		background.setOpacity(0.5);
		this.getChildren().add(background);
	}
	
	private void createWeaponImage() {
		this.weaponImage=new ImageView("gun.jpg");
		this.getChildren().add(weaponImage);
		this.weaponImage.setFitHeight(60);
		this.weaponImage.setFitWidth(60);
		
		this.weaponImage.setTranslateX(20);
		this.weaponImage.setTranslateY(50);
	}
	
	private void createNumber() {
		number=new Label("0");
		try {
			number.setFont(Font.loadFont(new FileInputStream("res/PixelTakhisis-ZajJ.ttf"), 30));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		number.setTranslateX(150);
		number.setTranslateY(40);
		
		this.getChildren().add(number);
	}
	
	private void createPlayerHp() {
		this.playerHP=new Rectangle(200,25);
		this.playerHP.setTranslateX(20);
		this.playerHP.setTranslateY(15);
		
		this.getChildren().add(playerHP);
		playerHP.setFill(Color.GREEN);
	
	}
	
	public void changeWeaponImage(ImageView imageView) {
		this.getChildren().remove(this.weaponImage);
		this.weaponImage=imageView;
		this.weaponImage.setTranslateX(20);
		this.weaponImage.setTranslateY(50);
		this.getChildren().add(weaponImage);
	}
	
	public ImageView getWeaponImage() {
		return weaponImage;
	}

	public Label getNumber() {
		return number;
	}

	public Rectangle getPlayerHP() {
		return playerHP;
	}
	
	public void setNumber(String text) {
		this.number.setText(text);
	}

	public void setPlayerHP(Rectangle playerHP) {
		this.playerHP = playerHP;
	}
	
	
	
	
}
