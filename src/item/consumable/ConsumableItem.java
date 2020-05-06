package item.consumable;

import exception.ConsumeItemFailedException;
import exception.FireBulletFailedException;
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

	protected char itemType;
	protected int amount ;
	protected String image_Path;
	protected Image image;
	protected ImageView imageView ;
	protected int width;
	protected int height;
	
	public ConsumableItem(String image_Path,int width, int height,int amount) {
		super(image_Path,width,height);
		this.amount = amount;
	}

	public void addAmount(int amount) {
		this.amount += amount;
	}

	public void consumed(MainCharacter player) throws ConsumeItemFailedException {

		if(this.amount==0) {
			throw new ConsumeItemFailedException("item is not enough.");
		}
		
		
		switch (itemType) {
		case 'P': {
			if(player.getMaxHP()== player.getCurrentHP()) {
				throw new ConsumeItemFailedException("Hp is full.");
			}
			
			this.amount -= 1;
			player.increaseCurrentHP(40);
			break;
		}
		case 'R': {
			this.amount -= 1;
			break;
		}
		case 'S': {

			this.amount -= 1;
			break;
		}
		case 'B': {
			this.amount -= 1;
			break;
		}
		}

		
	}

	public int getAmount() {
		return this.amount;
	}


	
}
