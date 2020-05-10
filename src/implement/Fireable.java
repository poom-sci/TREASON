package implement;

import exception.FireBulletFailedException;
import item.Entity;
import item.bullet.Bullet;
import item.character.GameCharacter;
import item.character.MainCharacter;

public interface Fireable {

	public abstract void addBullet(int count);

	public abstract Bullet fireBullet(GameCharacter character, boolean isRight) throws FireBulletFailedException;

	public abstract Bullet fireBulletInfinite(GameCharacter character, boolean isRight);

	public abstract Bullet setPositionBullet(GameCharacter character, boolean isRight, Bullet bullet);

}
