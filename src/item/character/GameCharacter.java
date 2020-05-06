package item.character;

import java.util.ArrayList;

import gui.SpriteAnimation;
import item.Entity;
import item.bullet.RocketBullet;
import item.bullet.SwordSlice;
import item.consumable.Consumable;
import item.weapon.Weapon;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import logic.Action;

public abstract class GameCharacter extends Entity {
//	protected ImageView imageView;
//	protected Rectangle box;

	protected Rectangle lowBox;
	protected Rectangle highBox;
//	protected Animation sprite;
//
//	protected int width;
//	protected int height;
//	protected int x;
//	protected int y;

	protected ArrayList<Action> actions;

	protected Action turnLeft;
	protected Action turnRight;
	protected Action walkRight;
	protected Action walkLeft;
	protected Action fireRight;
	protected Action fireLeft;
	protected Action dieRight;
	protected Action dieLeft;
	protected Action jumpLeft;
	protected Action jumpRight;

	protected Duration turnTime;
	protected Duration walkTime;
	protected Duration fireTime;
	protected Duration dieTime;
	protected Duration jumpTime;

	protected int pictureWidth;
	protected int pictureHeight;
	protected int pictureOffsetX;
	protected int pictureOffsetY;
	protected int pictureColumn;

	protected boolean isOnFloor = false;

	protected int currentHP;
	protected int maxHP;
	protected Rectangle currentHPBox;

	protected ArrayList<Weapon> inventory;
	protected ArrayList<Consumable> items;
	protected int weaponKey = 0;

	public GameCharacter(String image_path, int initX, int initY, int width, int height, int currentHP) {
		super(image_path,initX, initY, width, height);

		this.image_Path = image_path;
		box.setFill(Color.BLACK);

		this.lowBox = new Rectangle(10, 10);
		this.lowBox.setFill(Color.ALICEBLUE);

		this.highBox = new Rectangle(10, 10);
		this.highBox.setFill(Color.RED);

		this.currentHP = currentHP;
		this.maxHP = currentHP;
		currentHPBox = new Rectangle(currentHP, 20);
		setCurrentHPColor();
		this.inventory = new ArrayList<Weapon>();

		this.pictureWidth = 96;
		this.pictureHeight = 96;
		this.pictureOffsetX = 30;
		this.pictureOffsetY = 0;
//
//		turnTime = Duration.millis(1000);
//		walkTime = Duration.millis(1000);
//		fireTime = Duration.millis(200);
//		dieTime = Duration.millis(2000);
//		jumpTime = Duration.millis(1000);

		createAnimation();

	}

	public void createAnimation() {
		imageView = new ImageView(new Image(image_Path));
		imageView.setViewport(new Rectangle2D(pictureOffsetX, pictureOffsetY, pictureWidth, pictureHeight));
		sprite = new SpriteAnimation(imageView, Duration.millis(1000), 5, 5, pictureOffsetX, pictureOffsetY,
				pictureWidth, pictureHeight);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();

		boundX = 32;
		boundY = 18;
		setX(initX);
		setY(initY);

		createAction();
		turnLeft.setAction(true);

	}

	protected void createAction() {
		actions = new ArrayList<Action>();

		turnRight = new Action("isTurnRight", false);
		turnLeft = new Action("isTurnLeft", false);
		walkRight = new Action("isWalkRight", false);
		walkLeft = new Action("isWalkLeft", false);
		fireRight = new Action("fireRight", false);
		fireLeft = new Action("fireLeft", false);
		dieLeft = new Action("dieLeft", false);
		dieRight = new Action("dieRight", false);

		actions.add(turnLeft);
		actions.add(turnRight);
		actions.add(walkRight);
		actions.add(walkLeft);
		actions.add(fireRight);
		actions.add(fireLeft);
		actions.add(dieLeft);
		actions.add(dieRight);
	}

	public void resetAction() {
		for (Action e : actions) {
			e.setAction(false);
		}
		sprite.pause();
	}

	public void doTurnRight() {
		resetAction();
//		boundX = 30;
		sprite = new SpriteAnimation(imageView, turnTime, 5, 5, pictureOffsetX, pictureOffsetY + pictureHeight * 0,
				pictureWidth, pictureHeight);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
		turnRight.setAction(true);
	}

	public void doTurnLeft() {
		resetAction();
//		boundX = 30;
		sprite = new SpriteAnimation(imageView, turnTime, 5, 5, pictureOffsetX, pictureOffsetY + pictureHeight * 1,
				pictureWidth, pictureHeight);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
		turnLeft.setAction(true);
	}

	public void doWalkLeft() {
		resetAction();
//		boundX = 30;
		sprite = new SpriteAnimation(imageView, walkTime, 8, 8, pictureOffsetX, pictureOffsetY + pictureHeight * 2,
				pictureWidth, pictureHeight);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
		walkLeft.setAction(true);
	}

	public void doWalkRight() {
		resetAction();
//		boundX = 30;
		sprite = new SpriteAnimation(imageView, walkTime, 8, 8, pictureOffsetX, pictureOffsetY + pictureHeight * 3,
				pictureWidth, pictureHeight);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
		walkRight.setAction(true);
	}

	public void doFireLeft() {
		resetAction();
		sprite = new SpriteAnimation(imageView, fireTime, 5, 8, pictureOffsetX, pictureOffsetY + pictureHeight * 4,
				pictureWidth, pictureHeight);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
		fireLeft.setAction(true);
	}

	public void doFireRight() {
		resetAction();
		sprite = new SpriteAnimation(imageView, fireTime, 5, 8, pictureOffsetX, pictureOffsetY + pictureHeight * 5,
				pictureWidth, pictureHeight);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
		fireRight.setAction(true);

	}

	public void doDieLeft() {
		resetAction();
//		boundX = 30;
		sprite = new SpriteAnimation(imageView, dieTime, 8, 8, pictureOffsetX, pictureOffsetY + pictureHeight * 6,
				pictureWidth, pictureHeight);
		sprite.setCycleCount(1);
		sprite.play();
		dieLeft.setAction(true);
	}

	public void doDieRight() {
		resetAction();
//		boundX = 30;
		sprite = new SpriteAnimation(imageView, dieTime, 8, 8, pictureOffsetX, pictureOffsetY + pictureHeight * 7,
				pictureWidth, pictureHeight);
		sprite.setCycleCount(1);
		sprite.play();
		dieRight.setAction(true);
	}

	protected void setCurrentHPColor() {
		if (this.currentHP > 150) {
			currentHPBox.setFill(Color.GREEN);
		} else if (50 < this.currentHP && this.currentHP <= 150) {
			this.currentHPBox.setFill(Color.ORANGE);
		} else if (this.currentHP <= 50) {
			this.currentHPBox.setFill(Color.RED);
		}
	}

	public boolean getIsTurnLeft() {
		return turnLeft.isAction();
	}

	public boolean getIsTurnRight() {
		return turnRight.isAction();
	}

	public boolean getIsWalkRight() {
		return walkRight.isAction();
	}

	public boolean getIsWalkLeft() {
		return walkLeft.isAction();
	}

	public boolean getIsFireRight() {
		return fireRight.isAction();
	}

	public boolean getIsFireLeft() {
		return fireLeft.isAction();
	}

	public boolean isRight() {
		return turnRight.isAction() || walkRight.isAction() || fireRight.isAction();
	}

	public Action getAction() {
		for (Action e : actions) {
			if (e.isAction()) {
				return e;
			}
		}
		return null;
	}

	public ArrayList<Action> getActions() {
		return actions;
	}

	public void decreasedCurrentHP(int damage) {
		setCurrentHP(this.currentHP - damage);
		setCurrentHPColor();
	}

	public void increaseCurrentHP(int heal) {
		int healedHP = this.currentHP + heal;
		if (healedHP > this.maxHP) {
			healedHP = this.maxHP;
		}
		setCurrentHP(healedHP);
		setCurrentHPColor();
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
		this.currentHPBox.setWidth(currentHP);
		setCurrentHPColor();

	}

	public Rectangle getCurrentHPBox() {
		return currentHPBox;
	}

	public void setCurrentHPBox(Rectangle currentHPBox) {
		this.currentHPBox = currentHPBox;
	}

	public ArrayList<Weapon> getInventory() {
		return inventory;
	}

	public Action getDieRight() {
		return dieRight;
	}

	public Action getDieLeft() {
		return dieLeft;
	}

	public boolean isDie() {
		return (dieRight.isAction() || dieLeft.isAction());
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
			weaponKey += inventory.size();
		}
		weaponKey = weaponKey % inventory.size();
	}

	public void changeWeaponRight() {
		weaponKey += 1;
		weaponKey = weaponKey % inventory.size();
	}

	public Weapon getWeapon() {
		return inventory.get(weaponKey);
	}

	public boolean isOnFloor() {
		return isOnFloor;
	}

	public void setOnFloor(boolean isOnFloor) {
		this.isOnFloor = isOnFloor;
	}

	public ArrayList<Consumable> getItems() {
		return items;
	}

}
