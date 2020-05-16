package components.bullet;

import components.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bullet extends Entity {
	
	protected boolean isRight;
	protected int damage;
	
	
	public Bullet(String image_Path,boolean isRight,int initX,int initY,int width,int height) {
		super(image_Path, initX, initY,width,height);
		this.isRight=isRight;
		this.box.setFill(Color.AZURE);

		
	}


	public boolean isRight() {
		return isRight;
	}

	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}

	public int getDamage() {
		return damage;
	}
	
	
}
