package item.weapon;

import element.AudioLoader;
import exception.FireBulletFailedException;
import implement.Fireable;
import item.bullet.Bullet;
import item.bullet.GunBullet;
import item.bullet.SwordSlice;
import item.character.GameCharacter;
import item.character.MainCharacter;
import javafx.scene.media.AudioClip;

public class Sword extends Weapon implements Fireable {

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
		
		AudioClip Sword_Sound =AudioLoader.Sword_Sound;
//		granade_sound.setVolume(0.1);
		Sword_Sound.play();
		
		return bullets.remove(0);
	}

	@Override
	public void addBullet(int count) {
		// TODO Auto-generated method stub
		for (int i = 0; i < count; i++) {
			Bullet bullet = new SwordSlice(true, 0, 0);
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
	public Bullet fireBulletInfinite(GameCharacter character, boolean isRight) {
		// TODO Auto-generated method stub
		Bullet bullet = new SwordSlice(isRight, character.getX(), character.getY());
		bullet = setPositionBullet(character, isRight, bullet);
		
		AudioClip Sword_Sound =AudioLoader.Sword_Sound;
//		granade_sound.setVolume(0.1);
		Sword_Sound.play();
		
		return bullet;
	}

}
