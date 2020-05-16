package components.weapon;

import components.bullet.Bullet;
import components.bullet.GunBullet;
import components.character.GameCharacter;
import components.character.MainCharacter;
import element.AudioLoader;
import exception.FireBulletFailedException;
import implement.Fireable;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

public class Gun extends Weapon implements Fireable {

	public Gun( int bullet) {
		super("gun.png",60,60);
		this.imageView.setScaleX(2);
		this.imageView.setScaleY(2);
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
		
		AudioClip gunAudioClip =AudioLoader.Gun_Sound;
//		granade_sound.setVolume(0.1);
		gunAudioClip.play();
		
		return bullets.remove(0);
		
	}

	@Override
	public Bullet fireBulletInfinite(GameCharacter character, boolean isRight) {
		// TODO Auto-generated method stub
		Bullet bullet = new GunBullet(isRight, character.getX(), character.getY());
		bullet = setPositionBullet(character, isRight, bullet);
		
		AudioClip gunAudioClip =AudioLoader.Gun_Sound;
//		granade_sound.setVolume(0.1);
		gunAudioClip.play();
		
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
				bullet.getImageView().setScaleX(-1*bullet.getImageView().getScaleX());
			}
		
		
		return bullet;
		
	}



}
