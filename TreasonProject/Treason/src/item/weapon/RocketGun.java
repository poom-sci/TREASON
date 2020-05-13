package item.weapon;

import item.bullet.Bullet;
import item.bullet.RocketBullet;
import item.character.MainCharacter;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Fireable;

public class RocketGun extends Weapon {

	public RocketGun( int bullet) {
		super("rocketGun.png",120,40);
		this.maxBullet = 10;
		bulletType='R';
		addBullet(bullet);
		
	}


}

