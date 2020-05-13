package item.weapon;

import item.bullet.Bullet;
import exception.FireBulletFailedException;
import item.bullet.Bomb;
import item.bullet.RocketBullet;
import logic.Fireable;

public class BombGun extends Weapon {
	
	public BombGun( int bullet) {
		
		super("rocketGun.png",120,40);
		this.maxBullet = 10;
		bulletType='B';
		addBullet(bullet);

		
	}

}
