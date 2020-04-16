package item.weapon;

import java.util.ArrayList;

import item.bullet.Bullet;
import item.character.MainCharacter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Fireable;

public abstract class Weapon {
	
	protected int bulletFull;
	protected String name;
	protected int bulletLeft;
	protected String image_Path;
	protected Image image;
	protected ImageView imageView;
	
	protected int width;
	protected int height;
	
	protected ArrayList<Bullet> bullets=new ArrayList<Bullet>();
	
	protected int boundX;
	protected int boundY;

	public Weapon(String image_Path,int width,int height) {
		bulletLeft=0;
		
		image=new Image(image_Path);
		imageView = new ImageView(image);
		imageView.setFitHeight(height);
		imageView.setFitWidth(width);
		this.width=width;
		this.height=height;

		
	}

	public int getBulletLeft() {
		return bulletLeft;
	}

	public void setBulletLeft(int bulletLeft) {
		this.bulletLeft = bulletLeft;
	}

	public String getName() {
		return name;
	}

	public Bullet fireBullet(MainCharacter character,boolean isRight) {
		bulletLeft-=1;
		bullets.get(0).setRight(isRight);
		bullets.get(0).setInitX(character.getX());
		bullets.get(0).setInitY(character.getY());
		if (isRight) {
			bullets.get(0).setX(character.getX() + 40);
			bullets.get(0).setY(character.getY() + 20);
		} else {
			bullets.get(0).setX(character.getX() -20);
			bullets.get(0).setY(character.getY() + 20);
		}
		if(!isRight) {
			bullets.get(0).getImageView().setRotate(bullets.get(0).getImageView().getRotate()+180);
		}
		
		return bullets.remove(0);
	}
	
	public boolean isEmpty() {
		return bulletLeft==0;
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
	
	
	
	


}
