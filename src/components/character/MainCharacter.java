package components.character;

import java.util.ArrayList;

import components.consumable.Ammo;
import components.consumable.ConsumableItem;
import components.consumable.Potion;
import components.weapon.Gun;
import components.weapon.RocketGun;
import components.weapon.Sword;
import components.weapon.Weapon;
import javafx.util.Duration;

public class MainCharacter extends GameCharacter {

	private int point;
	private boolean isBlink = false;
	private ArrayList<ConsumableItem> itemsInventory;

	public MainCharacter(int initX, int initY) {
		super("main_character.png", initX, initY, 30, 60, 200);
		
		this.boundY=50;
		this.boundX=50;

		
		itemsInventory=new ArrayList<ConsumableItem>();
		pictureWidth = 128;
		pictureHeight = 128;
		pictureOffsetX = 0;
		pictureOffsetY = 0;

		point = 0;
		turnTime = Duration.millis(1000);
		walkTime = Duration.millis(500);
		fireTime = Duration.millis(300);
		dieTime = Duration.millis(200);
		
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
