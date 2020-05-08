package implement;

import item.Entity;
import item.bullet.Bullet;
import item.character.GameCharacter;
import item.character.MainCharacter;

public  interface  Fireable {
	
	public abstract void addBullet(int count);
	public abstract void fireBullet(GameCharacter character, boolean isRight);
	public abstract void fireBulletInfinite(GameCharacter character, boolean isRight);
	public abstract void setPositionBullet(GameCharacter character, boolean isRight, Bullet bullet);

}
