package item.character;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.text.StyledEditorKit.BoldAction;

import gui.GameButton;
import gui.SpriteAnimation;
import item.Action;
import item.consumable.Ammo;
import item.consumable.ConsumableItem;
import item.consumable.Potion;
import item.weapon.Gun;
import item.weapon.RocketGun;
import item.weapon.Sword;
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

public class MainCharacter extends GameCharacter {

	private int point;
	private boolean isBlink = false;
	protected ArrayList<ConsumableItem> itemsInventory;

	public MainCharacter(int initX, int initY) {
		super("main_character.png", initX, initY, 30, 60, 200);

		
		itemsInventory=new ArrayList<ConsumableItem>();
		pictureWidth = 96;
		pictureHeight = 96;
		pictureOffsetX = 30;
		pictureOffsetY = 0;

		point = 0;
		turnTime = Duration.millis(1000);
		walkTime = Duration.millis(1000);
		fireTime = Duration.millis(200);
		dieTime = Duration.millis(2000);
		jumpTime = Duration.millis(1000);
		
		createPotion();
		createAmmo();
		createGun();
		createRocketGun();
		createSword();

	}
	
	private void createPotion() {
		Potion potion = new Potion(1);
		this.itemsInventory.add(potion);
	}

	private void createAmmo() {
		Ammo ammo = new Ammo(1);
		this.itemsInventory.add(ammo);
	}

	private void createGun() {
		Weapon gun = new Gun(20);
		this.weaponsInventory.add(gun);
	}

	private void createRocketGun() {
		Weapon rocketGun = new RocketGun(10);
		this.weaponsInventory.add(rocketGun);
	}

	private void createSword() {
		Weapon sword = new Sword(1);
		this.weaponsInventory.add(sword);
	}

	public void blink() {
		isBlink = true;
		if (imageView.getOpacity() == 1) {
			imageView.setOpacity(0.5);
		}else {
			imageView.setOpacity(1);
		}
	}
	
	public void setOpacityNormal() {
		imageView.setOpacity(1);
	}

	public boolean isBlink() {
		return isBlink;
	}

	public void setBlink(boolean isBlink) {
		this.isBlink = isBlink;
	}

	public void addPoint(int i) {
		this.point += i;
	}

	public int getPoint() {
		return point;
	}
	
	public ArrayList<ConsumableItem> getItemsInventory() {
		return itemsInventory;
	}

}
