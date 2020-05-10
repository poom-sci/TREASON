package item.consumable;

import exception.ConsumeItemFailedException;
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
