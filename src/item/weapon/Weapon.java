package item.weapon;

import java.util.ArrayList;

import exception.FireBulletFailedException;
import implement.Fireable;
import item.Item;
import item.bullet.Bomb;
import item.bullet.Bullet;
import item.bullet.GunBullet;
import item.bullet.RocketBullet;
import item.bullet.SwordSlice;
import item.character.GameCharacter;
import item.character.MainCharacter;
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
