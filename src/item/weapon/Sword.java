package item.weapon;

import item.bullet.Bullet;
import item.bullet.SwordSlice;
import item.character.MainCharacter;
import logic.Fireable;

public class Sword extends Weapon implements Fireable  {
	
	public Sword( int bulletLeft) {
		super("sword.png",60,60);
		this.name = "Sword";
		this.bulletLeft = bulletLeft;
		this.bulletFull = 1;
		createBullet(bulletLeft);
	}


	@Override
	public void createBullet(int count) {
		for (int i = 0; i < count; i++) {
			Bullet bullet = new SwordSlice(true, 0, 0);
			this.bullets.add(bullet);

		}

	}

	public Bullet fireBullet(item.character.Character character,boolean isRight) {
		Bullet bullet= new SwordSlice(isRight, character.getX(), character.getY());
		
		bullet.setRight(isRight);
		bullet.setInitX(character.getX());
		bullet.setInitY(character.getY());
		if (isRight) {
			bullet.setBoundX(-10);
			bullet.setX(character.getX() + 40);
			bullet.setY(character.getY() + 0);
		} else {
			bullet.setBoundX(10);
			bullet.setX(character.getX() -60);
			bullet.setY(character.getY() + 0);
			bullet.getImageView().setRotate(imageView.getRotate() + 180);
		}
		createBullet(1);
		
		return bullet;
	}
}
