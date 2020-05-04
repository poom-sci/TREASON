package item.enemy;

import java.util.ArrayList;

import gui.SpriteAnimation;
import item.character.Character;
import item.weapon.Gun;
import item.weapon.Weapon;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import logic.Action;

public class BossEnemy extends Character {
	protected Weapon weapon;

	public BossEnemy(int initX,int initY) {
		super( initX, initY,200,200,1000);
		this.image_Path = "boss.png";
		this.weapon=new Gun(1);
		
		createAnimation();
	}
	
	private void createAnimation() {
		imageView = new ImageView(new Image(image_Path));
//		imageView.setViewport(new Rectangle2D(0, 0, 71, 80));
		imageView.setFitHeight(250);
		imageView.setFitWidth(200);
		sprite = new SpriteAnimation(imageView, Duration.millis(2000), 4, 4, 0, 0, 80, 80);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();

		this.disX=60*5;
//		turnRight();
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
		jumpLeft = new Action("jumpLeft", false);
		jumpRight = new Action("jumpRight", false);

		actions.add(turnLeft);
		actions.add(turnRight);
		actions.add(walkRight);
		actions.add(walkLeft);
		actions.add(fireRight);
		actions.add(fireLeft);
		actions.add(jumpLeft);
		actions.add(jumpRight);
		
	}

	public void resetAction() {
		for (Action e : actions) {
			e.setAction(false);
		}
		sprite.pause();
	}

	public void doTurnLeft() {
		resetAction();
//		boundX = 30;
		sprite = new SpriteAnimation(imageView, Duration.millis(1000), 5, 5, 30, 96 * 1, 96, 96);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
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
		sprite = new SpriteAnimation(imageView, Duration.millis(1000), 8, 8, 30, 96 * 3, 96, 96);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
		walkRight.setAction(true);
		System.out.println("walkRight");
	}

	public void doWalkLeft() {
		resetAction();
//		boundX = 30;
		sprite = new SpriteAnimation(imageView, Duration.millis(1000), 8, 8, 30, 96 * 2, 96, 96);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
		walkLeft.setAction(true);
		System.out.println("walkLeft");
	}
	
	public void doFireRight() {
		System.out.println("******************************");
		resetAction();
		sprite.stop();
//		boundX = 30;
		sprite = new SpriteAnimation(imageView, Duration.millis(100), 5, 8, 30, 96 * 5, 96, 96);
		sprite.setCycleCount(1);
		sprite.play();
		fireRight.setAction(true);
		System.out.println("fireRight");
	}
	
	public void doFireLeft() {
		System.out.println("******************************");
		resetAction();
		sprite.stop();
//		boundX = 30;
		sprite = new SpriteAnimation(imageView, Duration.millis(100), 5, 8, 30, 96 *4, 96, 96);
		sprite.setCycleCount(1);
		sprite.play();
		fireLeft.setAction(true);
		System.out.println("fireLeft");
	}

	public void doJumpLeft() {
		System.out.println("******************************");
		resetAction();
		sprite.stop();
//		boundX = 30;
		sprite = new SpriteAnimation(imageView, Duration.millis(100), 5, 8, 30, 96 *4, 96, 96);
		sprite.setCycleCount(1);
		sprite.play();
		fireLeft.setAction(true);
		System.out.println("JumpLeft");
	}
	
	public void doJumpRight() {
		System.out.println("******************************");
		resetAction();
		sprite.stop();
//		boundX = 30;
		sprite = new SpriteAnimation(imageView, Duration.millis(100), 5, 8, 30, 96 *4, 96, 96);
		sprite.setCycleCount(1);
		sprite.play();
		fireLeft.setAction(true);
		System.out.println("JumpRight");
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
}
