package item.character;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.text.StyledEditorKit.BoldAction;

import gui.GameButton;
import gui.SpriteAnimation;
import item.weapon.Weapon;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import logic.Action;

public class MainCharacter extends Character {

	public MainCharacter(int initX,int initY) {
		super( initX, initY,30,60,200);
		this.image_Path = "main_character.png";
		this.inventory= new ArrayList<Weapon>();
		
		createAnimation();
		

	}

	private void createAnimation() {
		
		imageView = new ImageView(new Image(image_Path));
		imageView.setViewport(new Rectangle2D(30, 0, 96, 96));
		
		sprite = new SpriteAnimation(imageView, Duration.millis(1000), 5, 5, 30, 0, 96, 96);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
		
		boundX = 32;
		boundY = 18;
		
		this.x = (int) box.getTranslateX();
		this.y = (int) box.getTranslateY();

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
		dieLeft=new Action("dieLeft", false);
		dieRight=new Action("dieRight", false);

		actions.add(turnLeft);
		actions.add(turnRight);
		actions.add(walkRight);
		actions.add(walkLeft);
		actions.add(fireRight);
		actions.add(fireLeft);
		actions.add(dieLeft);
		actions.add(dieRight);
	}

	private void resetAction() {
		for (Action e : actions) {
			e.setAction(false);
		}
		sprite.stop();
	}

	public void doTurnLeft() {
		resetAction();
//		boundX = 30;
		sprite = new SpriteAnimation(imageView, Duration.millis(1000), 5, 5, 30, 96 * 1, 96, 96);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
//		resetAction();
		turnLeft.setAction(true);
		System.out.println("turnLeft");
	}

	public void doTurnRight() {
		resetAction();
//		boundX = 30;
		sprite = new SpriteAnimation(imageView, Duration.millis(1000), 5, 5, 30, 96 * 0, 96, 96);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
		turnRight.setAction(true);
		System.out.println("turnRight");
	}

	public void doWalkRight() {
		resetAction();
//		boundX = 30;
		sprite = new SpriteAnimation(imageView, Duration.millis(500), 8, 8, 30, 96 * 3, 96, 96);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
		walkRight.setAction(true);
		System.out.println("walkRight");
	}

	public void doWalkLeft() {
		resetAction();
//		boundX = 30;
		sprite = new SpriteAnimation(imageView, Duration.millis(500), 8, 8, 30, 96 * 2, 96, 96);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
		walkLeft.setAction(true);
		System.out.println("walkLeft");
	}
	
	public void doFireRight() {
		resetAction();
//		boundX = 30;
		sprite = new SpriteAnimation(imageView, Duration.millis(200), 5, 8, 30, 96 * 5, 96, 96);
		sprite.setCycleCount(1);
		sprite.play();
		fireRight.setAction(true);
		System.out.println("fireRight");
	}
	
	public void doFireLeft() {
		resetAction();
//		boundX = 30;
		sprite = new SpriteAnimation(imageView, Duration.millis(200), 5, 8, 30, 96 *4, 96, 96);
		sprite.setCycleCount(1);
		sprite.play();
		fireLeft.setAction(true);
		System.out.println("fireLeft");
	}
	
	public void doDieLeft() {
		resetAction();
//		boundX = 30;
		sprite = new SpriteAnimation(imageView, Duration.millis(2000), 8, 8, 30, 96 *6, 96, 96);
		sprite.setCycleCount(1);
		sprite.play();
		dieLeft.setAction(true);
		System.out.println("DieLeft");
	}
	
	public void doDieRight() {
		resetAction();
//		boundX = 30;
		sprite = new SpriteAnimation(imageView, Duration.millis(2000), 8, 8, 30, 96*7 , 96, 96);
		sprite.setCycleCount(1);
		sprite.play();
		dieRight.setAction(true);
		System.out.println("DieRight");
	}

	

	
}
