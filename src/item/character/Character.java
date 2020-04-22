package item.character;

import java.util.ArrayList;

import item.Entity;
import item.bullet.RocketBullet;
import item.bullet.SwordSlice;
import item.weapon.Weapon;
import javafx.animation.Animation;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.Action;

public class Character extends Entity {
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

//	protected int boundX;
//	protected int boundY;
//
//	protected String image_Path;
//	
	protected int health;
	protected Rectangle healthBox;

	protected ArrayList<Weapon> inventory;

	public Character(int initX, int initY, int width, int height, int health) {
		super(initX, initY, width, height);

		box.setFill(Color.YELLOW);

		this.lowBox = new Rectangle(10, 10);
		this.lowBox.setFill(Color.ALICEBLUE);

		this.highBox = new Rectangle(10, 10);
		this.highBox.setFill(Color.RED);

		this.health = health;
		healthBox = new Rectangle(health, 20);
		setHealthColor();

	}

	protected void setHealthColor() {
		if (this.health > 150) {
			healthBox.setFill(Color.GREEN);
		} else if (this.health < 150) {
			this.healthBox.setFill(Color.ORANGERED);
		} else if (this.health < 50) {
			this.healthBox.setFill(Color.RED);
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

	public void decreasedHealth(int damage) {
		setHealth(this.health - damage);
		setHealthColor();

	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
		this.healthBox.setWidth(health);
		setHealthColor();

	}

	public Rectangle getHealthBox() {
		return healthBox;
	}

	public void setHealthBox(Rectangle healthBox) {
		this.healthBox = healthBox;
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

		if (isRight()) {
			this.lowBox.setTranslateX(getX() + 30);
			this.lowBox.setTranslateY(getY() + 60);

			this.highBox.setTranslateX(getX() + 30);
			this.highBox.setTranslateY(getY());
		} else {
			this.lowBox.setTranslateX(getX() - 30);
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

}
