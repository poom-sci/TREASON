package components.weapon;

import java.util.ArrayList;

import components.Item;
import components.bullet.Bomb;
import components.bullet.Bullet;
import components.bullet.GunBullet;
import components.bullet.RocketBullet;
import components.bullet.SwordSlice;
import components.character.GameCharacter;
import components.character.MainCharacter;
import exception.FireBulletFailedException;
import implement.Fireable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Weapon extends Item {

	protected int maxBullet;
	protected int currentBullet;
	protected char bulletType;

	protected ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	public Weapon(String image_Path, int width, int height) {
		super(image_Path, width, height);

	}


	public int getCurrentBullet() {
		return currentBullet;
	}

	public void setCurrentBullet(int currentBullet) {
		this.currentBullet = currentBullet;
	}

	public boolean isEmpty() {
		return currentBullet == 0;
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public int getMaxBullet() {
		return maxBullet;
	}
	
	

}
