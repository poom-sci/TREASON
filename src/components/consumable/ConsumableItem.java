package components.consumable;

import components.Item;
import components.bullet.Bomb;
import components.bullet.Bullet;
import components.bullet.GunBullet;
import components.bullet.RocketBullet;
import components.bullet.SwordSlice;
import components.character.GameCharacter;
import components.character.MainCharacter;
import exception.ConsumeItemFailedException;
import exception.FireBulletFailedException;
import implement.Fireable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class ConsumableItem extends Item {

	protected int amount ;
	
	protected char hotKey;
	
	public ConsumableItem(String image_Path,int width, int height,int amount) {
		super(image_Path,width,height);
		this.amount = amount;
	}

	public void addAmount(int amount) {
		this.amount += amount;
	}

	public int getAmount() {
		return this.amount;
	}

	public char getHotKey() {
		return hotKey;
	}
	
	


	
}
