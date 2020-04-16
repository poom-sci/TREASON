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

	public Bullet fireBullet(MainCharacter character,boolean isRight) {

		bullets.get(0).setRight(isRight);
		bullets.get(0).setInitX(character.getX());
		bullets.get(0).setInitY(character.getY());
		if (isRight) {
			bullets.get(0).setX(character.getX() + 40);
			bullets.get(0).setY(character.getY() + 0);
		} else {
			bullets.get(0).setX(character.getX() -50);
			bullets.get(0).setY(character.getY() + 0);
		}
		if(!isRight) {
			bullets.get(0).getImageView().setRotate(bullets.get(0).getImageView().getRotate()+180);
		}
		createBullet(1);
		return bullets.remove(0);
	}
}
