package item.weapon;

import item.bullet.Bullet;
import item.bullet.RocketBullet;
import item.character.MainCharacter;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Fireable;

public class RocketGun extends Weapon implements Fireable {

	public RocketGun( int bulletLeft) {
		super("rocketGun.png",120,40);
		this.name = "Rocket";
		this.bulletLeft = bulletLeft;
		this.bulletFull = 10;
		createBullet(bulletLeft);
		
	}


	@Override
	public void createBullet(int count) {
		for (int i = 0; i < count; i++) {
			Bullet bullet = new RocketBullet(true, 0, 0);
			this.bullets.add(bullet);
			

		}

	}

}

