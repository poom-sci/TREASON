package components.character;

import java.util.ArrayList;
import java.util.Collections;

import components.Entity;
import components.weapon.Weapon;
import gui.SpriteAnimation;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public abstract class GameCharacter extends Entity {

	protected Rectangle lowBox;
	protected Rectangle highBox;

	protected boolean pensioner=false;

	protected ArrayList<Boolean> action;

	protected Duration turnTime;
	protected Duration walkTime;
	protected Duration fireTime;
	protected Duration dieTime;

	protected int pictureWidth;
	protected int pictureHeight;
	protected int pictureOffsetX;
	protected int pictureOffsetY;
	protected int pictureColumn;

	protected boolean isOnFloor=false;

	protected int currentHP;
	protected int maxHP;
	protected int point;
	protected int finalPositionX;

	protected ArrayList<Weapon> weaponsInventory;


	protected int weaponKey = 0;

	public GameCharacter(String image_path, int initX, int initY, int width, int height, int currentHP) {
		super(image_path,initX, initY, width, height);

		this.image_Path = image_path;

		this.lowBox = new Rectangle(10, 10);
		this.lowBox.setFill(Color.ALICEBLUE);

		this.highBox = new Rectangle(10, 10);
		this.highBox.setFill(Color.RED);

		this.currentHP = currentHP;
		this.maxHP = currentHP;
		this.weaponsInventory = new ArrayList<Weapon>();


		this.pictureWidth = 100;
		this.pictureHeight = 100;
		createAction();

	}

	public void createAnimation() {
		
		imageView = new ImageView(new Image(image_Path));
		imageView.setViewport(new Rectangle2D(pictureOffsetX, pictureOffsetY, pictureWidth, pictureHeight));

		sprite = new SpriteAnimation(imageView, Duration.millis(1000), 5, 5, pictureOffsetX, pictureOffsetY,
				pictureWidth, pictureHeight);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
	

		setX(initX);
		setY(initY);


	}

	protected void createAction() {
		
		action=new ArrayList<>(Collections.nCopies(8, false));
		
//		�ӡѺ action
//		turnLeft
//		turnRight
//		walkLeft
//		walkRight
//		fireLeft
//		fireRight
//		dieLeft
//		dieRight
	}

	public void resetAction() {
		for (int i=0;i<action.size();i++) {
			action.set(i, false);
		}
		if(sprite!=null) {
			sprite.stop();
		}
	}
	
	public void doTurnLeft() {
		resetAction();
		sprite = new SpriteAnimation(imageView, turnTime, 5, 5, pictureOffsetX, pictureOffsetY + pictureHeight * 1,
				pictureWidth, pictureHeight);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
		action.set(0, true);
//		turnLeft.setAction(true);
	}

	public void doTurnRight() {
		resetAction();
		sprite = new SpriteAnimation(imageView, turnTime, 5, 5, pictureOffsetX, pictureOffsetY + pictureHeight * 0,
				pictureWidth, pictureHeight);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
		action.set(1, true);
//		turnRight.setAction(true);
	}

	public void doWalkLeft() {
		resetAction();
		sprite = new SpriteAnimation(imageView, walkTime, 5, 8, pictureOffsetX, pictureOffsetY + pictureHeight * 2,
				pictureWidth, pictureHeight);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
		action.set(2, true);
//		walkLeft.setAction(true);
	}

	public void doWalkRight() {
		resetAction();
		sprite = new SpriteAnimation(imageView, walkTime, 5, 8, pictureOffsetX, pictureOffsetY + pictureHeight * 3,
				pictureWidth, pictureHeight);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
		action.set(3, true);
//		walkRight.setAction(true);
	}

	public void doFireLeft() {
		resetAction();
		sprite = new SpriteAnimation(imageView, fireTime, 5, 8, pictureOffsetX, pictureOffsetY + pictureHeight * 4,
				pictureWidth, pictureHeight);
		sprite.setCycleCount(1);
		sprite.play();
		action.set(4, true);
//		fireLeft.setAction(true);
	}

	public void doFireRight() {
		resetAction();
		sprite = new SpriteAnimation(imageView, fireTime, 5, 8, pictureOffsetX, pictureOffsetY + pictureHeight * 5,
				pictureWidth, pictureHeight);
		sprite.setCycleCount(1);
		sprite.play();
		action.set(5, true);
//		fireRight.setAction(true);

	}

	public void doDieLeft() {
		resetAction();
		sprite = new SpriteAnimation(imageView, dieTime, 5, 8, pictureOffsetX, pictureOffsetY + pictureHeight * 6,
				pictureWidth, pictureHeight);
		sprite.setCycleCount(1);
		sprite.play();
		action.set(6, true);
//		dieLeft.setAction(true);
	}

	public void doDieRight() {
		resetAction();
		sprite = new SpriteAnimation(imageView, dieTime, 5, 8, pictureOffsetX, pictureOffsetY + pictureHeight * 7,
				pictureWidth, pictureHeight);
		sprite.setCycleCount(1);
		sprite.play();
		action.set(7, true);
//		dieRight.setAction(true);
	}
	
	public void jump(int velocityY) {
		if(isOnFloor) {
			this.velocityY=velocityY;
			this.isOnFloor=false;
		}

	}

	public boolean getIsTurnLeft() {
		return action.get(0);
	}

	public boolean getIsTurnRight() {
		return action.get(1);
	}
	
	public boolean getIsWalkLeft() {
		return action.get(2);
	}

	public boolean getIsWalkRight() {
		return action.get(3);
	}

	public boolean getIsFireLeft() {
		return action.get(4);
	}
	
	public boolean getIsFireRight() {
		return action.get(5);
	}
	
	public boolean getIsDieLeft() {
		return action.get(6);
	}
	
	public boolean getIsDieRight() {
		return action.get(7);
	}


	public boolean isRight() {
		return getIsTurnRight() || getIsWalkRight() || getIsFireRight()||getIsDieRight();
	}

	public void decreaseCurrentHP(int damage) {
		setCurrentHP(this.currentHP - damage);
	}

	public void increaseCurrentHP(int heal) {
		int healedHP = this.currentHP + heal;
		if (healedHP > this.maxHP) {
			healedHP = this.maxHP;
		}
		setCurrentHP(healedHP);
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;

	}

	public ArrayList<Weapon> getWeaponsInventory() {
		return weaponsInventory;
	}

	public boolean isDie() {
		return currentHP<=0;
	}

	public boolean checkTurn(ArrayList<Entity> platforms) {
		if (!isOnFloor) {
			return false;
		}

		if (isRight()) {
			this.lowBox.setTranslateX(getX() + 30);
			this.lowBox.setTranslateY(getY() + 60);

			this.highBox.setTranslateX(getX() + 30);
			this.highBox.setTranslateY(getY());
		} else {
			this.lowBox.setTranslateX(getX() - 10);
			this.lowBox.setTranslateY(getY() + 60);

			this.highBox.setTranslateX(getX() - 30);
			this.highBox.setTranslateY(getY());
		}

		for (Entity platform : platforms) {

			boolean isCollisionPlatformHighBoxlow = this.highBox.getBoundsInParent()
					.intersects(platform.getBox().getBoundsInParent());

			if (isCollisionPlatformHighBoxlow) {
				return true;
			}

			boolean isCollisionPlatformLowBoxlow = this.lowBox.getBoundsInParent()
					.intersects(platform.getBox().getBoundsInParent());

			if (isCollisionPlatformLowBoxlow) {
				return false;
			}

		}
		return true;
	}

	public Rectangle getLowBox() {
		return lowBox;
	}

	public Rectangle getHighBox() {
		return highBox;
	}

	public int getWeaponKey() {
		return weaponKey;
	}

	public void changeWeaponLeft() {
		weaponKey -= 1;
		if (weaponKey < 0) {
			weaponKey += weaponsInventory.size();
		}
		weaponKey = weaponKey % weaponsInventory.size();
	}

	public void changeWeaponRight() {
		weaponKey += 1;
		weaponKey = weaponKey % weaponsInventory.size();
	}

	public Weapon getWeapon() {
		return weaponsInventory.get(weaponKey);
	}

	public boolean isOnFloor() {
		return isOnFloor;
	}

	public void setOnFloor(boolean isOnFloor) {
		this.isOnFloor = isOnFloor;
	}


	public int getMaxHP() {
		return maxHP;
	}

	public int getPoint() {
		return point;
	}

	public boolean isPensioner() {
		return pensioner;
	}

	public void setPensioner(boolean pensioner) {
		this.pensioner = pensioner;
	}


	public int getFinalPositionX() {
		return finalPositionX;
	}

	public void setFinalPositionX(int finalPositionX) {
		this.finalPositionX = finalPositionX;
	}

	

}
