package item.weapon;

import exception.FireBulletFailedException;
import implement.Fireable;
import item.bullet.Bullet;
import item.bullet.GunBullet;
import item.character.GameCharacter;
import item.character.MainCharacter;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Gun extends Weapon implements Fireable {

	public Gun( int bullet) {
		super("gun.jpg",60,60);
		this.maxBullet = 15;
		this.bulletType='G';
		addBullet(bullet);
	}
	
	
	public void addBullet(int count) {
		for (int i = 0; i < count; i++) {
			Bullet bullet = new GunBullet(true, 0, 0);
			this.bullets.add(bullet);
			currentBullet += 1;
			if (currentBullet >= maxBullet) {
				return;
			}
		}
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
		// TODO Auto-generated method stub
		Bullet bullet = new GunBullet(isRight, character.getX(), character.getY());
		bullet = setPositionBullet(character, isRight, bullet);
		return bullet;
		
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



}
