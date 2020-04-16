package item.enemy;

import java.util.ArrayList;

import gui.SpriteAnimation;
import item.Entity;
import item.character.Character;
import item.weapon.Gun;
import item.weapon.Weapon;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import logic.Action;

public class Enemy extends Character {
	
	

	protected Weapon weapon;

	public Enemy() {
		super(30,60,30);
		this.image_Path = "main_character.png";
		this.weapon=new Gun(1);
		
		createAnimation();
	}
	
	private void createAnimation() {
		imageView = new ImageView(new Image(image_Path));
		imageView.setViewport(new Rectangle2D(30, 0, 96, 96));
		sprite = new SpriteAnimation(imageView, Duration.millis(1000), 5, 5, 30, 0, 96, 96);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();

//		turnRight();
		boundX = 32;
		boundY = 18;

		this.x = (int) box.getTranslateX();
		this.y = (int) box.getTranslateY();

		imageView.setLayoutX(this.x - boundX);
		imageView.setLayoutY(this.y - boundY);
		createAction();
		turnLeft.setAction(true);
	}

	private void createAction() {
		actions = new ArrayList<Action>();

		turnRight = new Action("isTurnRight", false);
		turnLeft = new Action("isTurnLeft", false);
		walkRight = new Action("isWalkRight", false);
		walkLeft = new Action("isWalkLeft", false);
		fireRight = new Action("fireRight", false);
		fireLeft = new Action("fireLeft", false);

		actions.add(turnLeft);
		actions.add(turnRight);
		actions.add(walkRight);
		actions.add(walkLeft);
		actions.add(fireRight);
		actions.add(fireLeft);
	}

	private void resetAction() {
		for (Action e : actions) {
			e.setAction(false);
		}
		sprite.pause();
	}

	public void doTurnLeft() {
		resetAction();
		boundX = 30;
		sprite = new SpriteAnimation(imageView, Duration.millis(1000), 5, 5, 30, 96 * 1, 96, 96);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
		turnLeft.setAction(true);
		System.out.println("turnLeft");
	}

	public void doTurnRight() {
		resetAction();
		boundX = 30;
		sprite = new SpriteAnimation(imageView, Duration.millis(1000), 5, 5, 30, 96 * 0, 96, 96);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
		turnRight.setAction(true);
		System.out.println("turnRight");
	}

	public void doWalkRight() {
		resetAction();
		boundX = 30;
		sprite = new SpriteAnimation(imageView, Duration.millis(1000), 8, 8, 30, 96 * 3, 96, 96);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
		walkRight.setAction(true);
		System.out.println("walkRight");
	}

	public void doWalkLeft() {
		resetAction();
		boundX = 30;
		sprite = new SpriteAnimation(imageView, Duration.millis(1000), 8, 8, 30, 96 * 2, 96, 96);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
		walkLeft.setAction(true);
		System.out.println("walkLeft");
	}
	
	public void doFireRight() {
		resetAction();
		boundX = 30;
		sprite = new SpriteAnimation(imageView, Duration.millis(500), 5, 8, 30, 96 * 5, 96, 96);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
		fireRight.setAction(true);
		System.out.println("fireRight");
	}
	
	public void doFireLeft() {
		resetAction();
		boundX = 30;
		sprite = new SpriteAnimation(imageView, Duration.millis(500), 5, 8, 30, 96 *4, 96, 96);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
		fireLeft.setAction(true);
		System.out.println("fireLeft");
	}
}
