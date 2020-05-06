package item.weapon;

import item.bullet.Bullet;
import item.bullet.Bomb;
import item.bullet.RocketBullet;
import logic.FireBulletFailedException;
import logic.Fireable;

public class BombGun extends Weapon {
	
	public BombGun( int bullet) {
		
		super("rocketGun.png",120,40);
		this.name = "Rocket";
		this.maxBullet = 10;
		bulletType='B';
		addBullet(bullet);

		
	}

}
