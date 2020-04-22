package item.bullet;

import item.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bullet extends Entity {
	
	protected boolean isRight;
	protected int initX;
	protected int initY;
	protected int disX;
	protected int disY;
	
	
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


	public int getInitX() {
		return initX;
	}


	public int getInitY() {
		return initY;
	}
	
	public void setInitX(int initX) {
		this.initX = initX;
	}


	public void setInitY(int initY) {
		this.initY = initY;
	}


	public int getDisX() {
		return disX;
	}


	public int getDisY() {
		return disY;
	}
}
