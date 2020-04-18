package item.character;

import java.util.ArrayList;

import item.weapon.Weapon;
import javafx.animation.Animation;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.Action;

public class Character {
	protected ImageView imageView;
	protected Rectangle box;
	protected Animation sprite;

	protected int width;
	protected int height;
	protected int x;
	protected int y;

	protected ArrayList<Action> actions;

	protected Action turnLeft;
	protected Action turnRight;
	protected Action walkRight;
	protected Action walkLeft;
	protected Action fireRight;
	protected Action fireLeft;
	protected Action dieRight;
	protected Action dieLeft;

	protected int boundX;
	protected int boundY;

	protected String image_Path;
	
	protected int health;
	protected Rectangle healthBox;
	
	protected ArrayList<Weapon> inventory;
	 

	public Character(int width,int height,int health) {
		box = new Rectangle(width, height);
		this.width = width;
		this.height = height;
		box.setFill(Color.TRANSPARENT);
		
		this.health=health;
		healthBox=new Rectangle(health,20);
		setHealthColor();
		
	}
	
	protected void setHealthColor() {
		if(this.health>150) {
			healthBox.setFill(Color.GREEN);
		}
		else if(this.health<150) {
			this.healthBox.setFill(Color.ORANGERED);
		}
		else if(this.health<50){
			this.healthBox.setFill(Color.RED);
		}
	}
	
	public ImageView getImageView() {
		return imageView;
	}

	public int getX() {
		return (int) box.getTranslateX();
	}

	public void setX(int x) {
		box.setTranslateX(x);
		this.x = x;
		imageView.setLayoutX(this.x - boundX);
	}

	public int getY() {
		return (int) box.getTranslateY();
	}

	public void setY(int y) {
		box.setTranslateY(y);
		this.y = y;
		imageView.setLayoutY(this.y - boundY);
	}

	public Rectangle getBox() {
		return box;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
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

	public ArrayList<Action> getActions() {
		return actions;
	}
	
	public void decreasedHealth(int damage) {
		setHealth(this.health-damage);
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
		return(dieRight.isAction()||dieLeft.isAction());
	}

	public Animation getSprite() {
		return sprite;
	}

	
	
}
