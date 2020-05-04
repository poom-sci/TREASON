package item.weapon;

import item.bullet.Bullet;
import item.bullet.Bomb;
import item.bullet.RocketBullet;
import logic.Fireable;

public class BombGun extends Weapon implements Fireable {
	
	public BombGun( int bulletLeft) {
		
		super("rocketGun.png",120,40);
		this.name = "Rocket";
		this.bulletLeft = bulletLeft;
		this.bulletFull = 10;
		createBullet(bulletLeft);
		
	}


	@Override
	public void createBullet(int count) {
		for (int i = 0; i < count; i++) {
			Bullet bullet = new Bomb(true, 0, 0);
			this.bullets.add(bullet);

		}

	}
}
