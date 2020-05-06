package item.weapon;

import item.bullet.Bullet;
import item.bullet.GunBullet;
import item.character.MainCharacter;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Fireable;

public class Gun extends Weapon {

	public Gun( int bullet) {
		super("gun.jpg",60,60);
		this.name = "Gun";
		this.maxBullet = 15;
		this.bulletType='G';
		addBullet(bullet);
	}



}
