package components.weapon;

import components.bullet.Bomb;
import components.bullet.Bullet;
import components.bullet.RocketBullet;
import components.bullet.SwordSlice;
import components.character.GameCharacter;
import components.character.MainCharacter;
import element.AudioLoader;
import exception.FireBulletFailedException;
import implement.Fireable;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

public class RocketGun extends Weapon implements Fireable {

	public RocketGun(int bullet) {
		super("rocketGun.png", 120, 40);
		this.imageView.setScaleY(3);
		this.maxBullet = 10;
		bulletType = 'R';
		addBullet(bullet);

	}

	@Override
	public void addBullet(int count) {
		// TODO Auto-generated method stub
		for (int i = 0; i < count; i++) {
			if (currentBullet >= maxBullet) {
				return;
			}
			Bullet bullet = new RocketBullet(true, 0, 0);
			this.bullets.add(bullet);
			currentBullet += 1;

		}

	}

	@Override
	public Bullet setPositionBullet(GameCharacter character, boolean isRight, Bullet bullet) {
		// TODO Auto-generated method stub

		bullet.setRight(isRight);
		if (isRight) {
			int x = character.getX() + 0;
			int y = character.getY()+25;
			bullet.setBoundX(30);
			bullet.setInitX(x);
			bullet.setInitY(y);
			bullet.setX(x);
			bullet.setY(y);
		} else {
			int x = character.getX() +0;
			int y = character.getY()+25;
			bullet.setBoundX(-30);
			bullet.setInitX(x);
			bullet.setInitY(y);
			bullet.setX(x);
			bullet.setY(y);
		}

		if (isRight) {
			bullet.getImageView().setScaleX(-1*bullet.getImageView().getScaleX());
		}

		return bullet;
	}

	@Override
	public Bullet fireBullet(GameCharacter character, boolean isRight) throws FireBulletFailedException {

		if (currentBullet == 0) {
			throw new FireBulletFailedException("There is no bullet left.");
		}
		currentBullet -= 1;
		Bullet bullet = bullets.get(0);
		bullet = setPositionBullet(character, isRight, bullet);
		
		AudioClip granade_sound = AudioLoader.Granade_Sound;
		granade_sound.play();

		return bullets.remove(0);

	}

	@Override
	public Bullet fireBulletInfinite(GameCharacter character, boolean isRight) {
		Bullet bullet = new RocketBullet(isRight, character.getX(), character.getY());
		bullet = setPositionBullet(character, isRight, bullet);
		
		AudioClip granade_sound = new AudioClip(ClassLoader.getSystemResource("granade_sound.wav").toString());
//		granade_sound.setVolume(0.2);
		granade_sound.play();
		
		return bullet;
	}

}
