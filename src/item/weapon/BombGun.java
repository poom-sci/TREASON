package item.weapon;

import item.bullet.Bullet;
import exception.FireBulletFailedException;
import implement.Fireable;
import item.bullet.Bomb;
import item.bullet.RocketBullet;
import item.character.GameCharacter;

public class BombGun extends Weapon {
	
	public BombGun( int bullet) {
		
		super("rocketGun.png",120,40);
		this.maxBullet = 10;
		bulletType='B';
		addBullet(bullet);

		
	}

//	@Override
//	public void fireBullet(GameCharacter character, boolean isRight) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void fireBulletInfinite(GameCharacter character, boolean isRight) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setPositionBullet(GameCharacter character, boolean isRight, Bullet bullet) {
//		// TODO Auto-generated method stub
//		
//	}

}
