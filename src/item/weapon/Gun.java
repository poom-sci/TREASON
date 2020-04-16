package item.weapon;

import item.bullet.Bullet;
import item.bullet.GunBullet;
import item.character.MainCharacter;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Fireable;

public class Gun extends Weapon implements Fireable {

	public Gun( int bulletLeft) {
		super("gun.jpg",60,60);
		this.name = "Gun";
		this.bulletLeft = bulletLeft;
		this.bulletFull = 15;
		createBullet(bulletLeft);
		
	}


	@Override
	public void createBullet(int count) {
		for (int i = 0; i < count; i++) {
			Bullet bullet = new GunBullet(true, 0, 0);
			this.bullets.add(bullet);

		}

	}

}
