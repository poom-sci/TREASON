package implement;

import components.Entity;
import components.bullet.Bullet;
import components.character.GameCharacter;
import components.character.MainCharacter;
import exception.FireBulletFailedException;

public interface Fireable {

	public abstract void addBullet(int count);

	public abstract Bullet fireBullet(GameCharacter character, boolean isRight) throws FireBulletFailedException;

	public abstract Bullet fireBulletInfinite(GameCharacter character, boolean isRight);

	public abstract Bullet setPositionBullet(GameCharacter character, boolean isRight, Bullet bullet);

}
