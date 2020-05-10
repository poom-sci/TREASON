package item.weapon;

import item.bullet.Bullet;
import exception.FireBulletFailedException;
import implement.Fireable;
import item.bullet.Bomb;
import item.bullet.RocketBullet;
import item.bullet.SwordSlice;
import item.character.GameCharacter;

public class BombGun extends Weapon implements Fireable {
	
	public BombGun( int bullet) {
		
		super("rocketGun.png",120,40);
		this.maxBullet = 10;
		bulletType='B';
		addBullet(bullet);

		
	}

	@Override
	public void addBullet(int count) {
		// TODO Auto-generated method stub.
		for (int i = 0; i < count; i++) {
		Bullet bullet = new Bomb(true, 0, 0);
		this.bullets.add(bullet);
		currentBullet += 1;
		if (currentBullet >= maxBullet) {
			return;
		}
	}
		
	}

	@Override
	public Bullet setPositionBullet(GameCharacter character, boolean isRight, Bullet bullet) {
		// TODO Auto-generated method stub
		bullet.setRight(isRight);
		if (isRight) {
			int x = character.getX() + 40;
			int y = character.getY() + 20;
			bullet.setInitX(x);
			bullet.setInitY(y);
			bullet.setX(x);
			bullet.setY(y);
		} else {
			int x = character.getX() - 40;
			int y = character.getY() + 20;
			bullet.setInitX(x);
			bullet.setInitY(y);
			bullet.setX(x);
			bullet.setY(y);
		}

			if (!isRight) {
				bullet.getImageView().setRotate(bullet.getImageView().getRotate() + 180);
			}
		
		
		return bullet;
	}

	@Override
	public Bullet fireBullet(GameCharacter character, boolean isRight) throws FireBulletFailedException {
		// TODO Auto-generated method stub
		if (currentBullet == 0) {
			throw new FireBulletFailedException("There is no bullet left.");
		}
		currentBullet -= 1;
		Bullet bullet = bullets.get(0);

		bullet = setPositionBullet(character, isRight, bullet);

		return bullets.remove(0);

	}

	@Override
	public Bullet fireBulletInfinite(GameCharacter character, boolean isRight) {
		Bullet bullet = new Bomb(isRight, character.getX(), character.getY());
		bullet = setPositionBullet(character, isRight, bullet);
		return bullet;
	}

}
