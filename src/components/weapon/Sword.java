package components.weapon;

import components.bullet.Bullet;
import components.bullet.GunBullet;
import components.bullet.SwordSlice;
import components.character.GameCharacter;
import components.character.MainCharacter;
import element.AudioLoader;
import exception.FireBulletFailedException;
import implement.Fireable;
import javafx.scene.media.AudioClip;

public class Sword extends Weapon implements Fireable {

	public Sword(int bullet) {
		super("sword.png", 60, 60);
		this.maxBullet = 1;
		bulletType = 'S';
		addBullet(bullet);

	}

	public Bullet fireBullet(components.character.GameCharacter character, boolean isRight)
			throws FireBulletFailedException {
		if (currentBullet == 0) {
			throw new FireBulletFailedException("There is no bullet left.");
		}
		currentBullet -= 1;
		Bullet bullet=bullets.get(0);
		bullet = setPositionBullet(character, isRight, bullet);
		addBullet(1);

		AudioClip Sword_Sound = AudioLoader.Sword_Sound;
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
		bullet.setInitX(character.getX());
		bullet.setInitY(character.getY());
		if (isRight) {
			int x = character.getX() + 40;
			int y = character.getY() + 7;
			bullet.setX(x);
			bullet.setY(y);
		} else {
			int x = character.getX() - 40;
			int y = character.getY() +7;
			bullet.setX(x);
			bullet.setY(y);
		}

		if (!isRight) {
			bullets.get(0).getImageView().setScaleX(-1);
		}

		return bullet;

	}

	@Override
	public Bullet fireBulletInfinite(GameCharacter character, boolean isRight) {
		// TODO Auto-generated method stub
		Bullet bullet = new SwordSlice(isRight, character.getX(), character.getY());
		bullet = setPositionBullet(character, isRight, bullet);

		AudioClip Sword_Sound = AudioLoader.Sword_Sound;
//		granade_sound.setVolume(0.1);
		Sword_Sound.play();

		return bullet;
	}

}
