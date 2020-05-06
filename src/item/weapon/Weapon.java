package item.weapon;

import java.util.ArrayList;

import item.bullet.Bomb;
import item.bullet.Bullet;
import item.bullet.GunBullet;
import item.bullet.RocketBullet;
import item.bullet.SwordSlice;
import item.character.MainCharacter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.FireBulletFailedException;
import logic.Fireable;

public abstract class Weapon {

	protected int maxBullet;
	protected String name;
	protected int currentBullet;
	protected String image_Path;
	protected Image image;
	protected ImageView imageView;
	protected char bulletType;

	protected int width;
	protected int height;

	protected ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	protected int boundX;
	protected int boundY;

	public Weapon(String image_Path, int width, int height) {

		image = new Image(image_Path);
		imageView = new ImageView(image);
		imageView.setFitHeight(height);
		imageView.setFitWidth(width);
		this.width = width;
		this.height = height;
//		bulletType = 'G';

	}

	public void addBullet(int count) {

		switch (bulletType) {
		case 'G': {
			for (int i = 0; i < count; i++) {
				Bullet bullet = new GunBullet(true, 0, 0);
				this.bullets.add(bullet);
				currentBullet += 1;
				System.out.println("addG");
				if (currentBullet == maxBullet) {
					return;
				}
			}
			break;
		}
		case 'R': {
			for (int i = 0; i < count; i++) {
				Bullet bullet = new RocketBullet(true, 0, 0);
				this.bullets.add(bullet);
				currentBullet += 1;
				if (currentBullet == maxBullet) {
					return;
				}
			}
			break;
		}
		case 'S': {
			for (int i = 0; i < count; i++) {
				Bullet bullet = new SwordSlice(true, 0, 0);
				this.bullets.add(bullet);
				currentBullet += 1;
				if (currentBullet == maxBullet) {
					return;
				}
			}
			break;
		}
		case 'B': {
			for (int i = 0; i < count; i++) {
				Bullet bullet = new Bomb(true, 0, 0);
				this.bullets.add(bullet);
				currentBullet += 1;
				if (currentBullet == maxBullet) {
					return;
				}
			}
			break;
		}
		}

	}

	public int getCurrentBullet() {
		return currentBullet;
	}

	public void setCurrentBullet(int currentBullet) {
		this.currentBullet = currentBullet;
	}

	public String getName() {
		return name;
	}

	public Bullet fireBullet(item.character.GameCharacter character, boolean isRight) throws FireBulletFailedException {
		if (currentBullet == 0) {
			throw new FireBulletFailedException("There is no bullet left.");
		}
		currentBullet -= 1;
		Bullet bullet =bullets.get(0);
		bullet.setRight(isRight);


		if (isRight) {
			int x=character.getX() + 40;
			int y=character.getY() + 20;
			bullet.setInitX(x);
			bullet.setInitY(y);
			bullet.setX(x);
			bullet.setY(y);
		} else {
			int x=character.getX() - 40;
			int y=character.getY() + 20;
			bullets.get(0).setInitX(x);
			bullet.setInitY(y);
			bullet.setX(x);
			bullet.setY(y);
		}
		if (!isRight) {
			bullet.getImageView().setRotate(bullet.getImageView().getRotate() + 180);
		}

		return bullets.remove(0);
	}

	public Bullet fireBulletInfinite(item.character.GameCharacter character, boolean isRight) {
		Bullet bullet = new GunBullet(isRight, character.getX(), character.getY());
		bullet.setRight(isRight);
		bullet.setInitX(character.getX());
		bullet.setInitY(character.getY());
		if (isRight) {
			bullet.setX(character.getX() + 40);
			bullet.setY(character.getY() + 20);
		} else {
			bullet.setX(character.getX() - 30);
			bullet.setY(character.getY() + 20);
		}
		if (!isRight) {
			bullet.getImageView().setRotate(bullet.getImageView().getRotate() + 180);
		}
		return bullet;
	}

	public boolean isEmpty() {
		return currentBullet == 0;
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public Image getImage() {
		return image;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void addBullets(int ammo) {
		this.currentBullet = this.currentBullet + ammo;
	}

}
