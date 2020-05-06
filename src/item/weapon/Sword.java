package item.weapon;

import exception.FireBulletFailedException;
import item.bullet.Bullet;
import item.bullet.GunBullet;
import item.bullet.SwordSlice;
import item.character.MainCharacter;
import logic.Fireable;

public class Sword extends Weapon {

	public Sword(int bullet) {
		super("sword.png", 60, 60);
		this.maxBullet = 1;
		bulletType = 'S';
		addBullet(bullet);

	}

	public Bullet fireBullet(item.character.GameCharacter character, boolean isRight) throws FireBulletFailedException {
		if (currentBullet == 0) {
			throw new FireBulletFailedException("There is no bullet left.");
		}
		currentBullet -= 1;
		bullets.get(0).setRight(isRight);

		bullets.get(0).setInitX(character.getX());
		bullets.get(0).setInitY(character.getY());
		if (isRight) {
			bullets.get(0).setX(character.getX() + 40);
			bullets.get(0).setY(character.getY() + 20);
		} else {
			bullets.get(0).setX(character.getX() - 60);
			bullets.get(0).setY(character.getY() + 20);
		}
		if (!isRight) {
			bullets.get(0).getImageView().setRotate(bullets.get(0).getImageView().getRotate() + 180);
		}
		addBullet(1);
		return bullets.remove(0);
	}

}
