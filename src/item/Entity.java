package item;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public abstract class Entity {
	
	protected int x;
	protected int y;

	protected int width;
	protected int height;
	
	
	protected String name;
	protected Rectangle box;
	protected Image image;
	protected ImageView imageView;
	protected Animation sprite;
	protected String image_Path; 
	
	protected int initX;
	protected int initY;
	protected int disX;
	protected int disY;
	protected int velocityX;
	protected int velocityY;
	
	protected int boundX;
	protected int boundY;

	
    public Entity(int initX,int initY,int width,int height) {
    	box=new Rectangle(width,height);
		this.width = width;
		this.height = height;
		this.box.setFill(Color.TRANSPARENT);
		
		this.initX=initX;
		this.initY=initY;

		
//		setX(initX);
//		setY(initY);
    	
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public Rectangle getBox() {
		return box;
	}


	public Animation getSprite() {
		return sprite;
	}
	

	public String getImage_Path() {
		return image_Path;
	}


	public void setImage_Path(String image_Path) {
		this.image_Path = image_Path;
	}


	public int getBoundX() {
		return boundX;
	}


	public void setBoundX(int boundX) {
		this.boundX = boundX;
	}


	public int getBoundY() {
		return boundY;
	}


	public void setBoundY(int boundY) {
		this.boundY = boundY;
	}


	public int getX() {
		return (int) this.box.getTranslateX();
	}


	public void setX(int x) {
		this.x = x;
		this.box.setTranslateX(x);
		this.imageView.setTranslateX(this.x-boundX);
	}


	public int getY() {
		return (int) this.box.getTranslateY();
	}


	public void setY(int y) {
		this.y = y;
		this.box.setTranslateY(y);
		this.imageView.setTranslateY(this.y-boundY);
		
	}


	public ImageView getImageView() {
		return imageView;
	}


	public int getInitX() {
		return initX;
	}


	public void setInitX(int initX) {
		this.initX = initX;
	}


	public int getInitY() {
		return initY;
	}


	public void setInitY(int initY) {
		this.initY = initY;
	}


	public int getDisX() {
		return disX;
	}


	public void setDisX(int disX) {
		this.disX = disX;
	}


	public int getDisY() {
		return disY;
	}


	public void setDisY(int disY) {
		this.disY = disY;
	}


	public void setSprite(Animation sprite) {
		this.sprite = sprite;
	}


	public int getVelocityX() {
		return velocityX;
	}


	public void setVelocityX(int velocityX) {
		this.velocityX = velocityX;
	}


	public int getVelocityY() {
		return velocityY;
	}


	public void setVelocityY(int velocityY) {
		this.velocityY = velocityY;
	}
	
	public void addVelocityX(int velocityX) {
		this.velocityX +=velocityX;
	}
    
	public void addVelocityY(int velocityY) {
		this.velocityY +=velocityY;
	}
    
    
}
