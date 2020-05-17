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
	private Rectangle playerHp;

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
		this.weaponImage = new ImageView("gun.png");
		this.getChildren().add(weaponImage);
		this.weaponImage.setFitHeight(60);
		this.weaponImage.setFitWidth(60);

		this.weaponImage.setTranslateX(20);
		this.weaponImage.setTranslateY(50);
	}

	private void createNumber() {
		number = new Label("0");
		number.setFont(Font
				.loadFont(getClass().getClassLoader().getResource("PixelTakhisis-ZajJ.ttf").toExternalForm(), 23));

		number.setTranslateX(150);
		number.setTranslateY(40);

		this.getChildren().add(number);
	}

	private void createPlayerHp() {
		this.playerHp = new Rectangle(200, 25);
		this.playerHp.setTranslateX(20);
		this.playerHp.setTranslateY(15);

		this.getChildren().add(playerHp);
		playerHp.setFill(Color.GREEN);

	}

	public void changeWeaponImage(ImageView imageView) {
		this.getChildren().remove(this.weaponImage);
		this.weaponImage = imageView;
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
		return playerHp;
	}

	public void setNumber(String text) {
		this.number.setText(text);
	}

	public void setPlayerHP(int Hp) {
		playerHp.setWidth(Hp);
		int currentHp = (int) this.playerHp.getWidth();
		if (this.playerHp.getWidth() > 150) {
			playerHp.setFill(Color.GREEN);
		} else if (50 < currentHp && currentHp <= 150) {
			this.playerHp.setFill(Color.ORANGE);
		} else if (currentHp <= 50) {
			this.playerHp.setFill(Color.RED);
		}

	}

}
