package item.bullet;

import item.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bullet extends Entity {
	
	protected boolean isRight;
	protected int damage;
	
	
	public Bullet(boolean isRight,int initX,int initY,int width,int height) {
		super( initX, initY,width,height);
		this.width=width;
		this.height=height;
		this.x=initX;
		this.y=initY;
		this.isRight=isRight;
		
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
