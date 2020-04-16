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
	
	protected int boundX;
	protected int boundY;

	
    public Entity(int width,int height) {
    	box=new Rectangle(width,height);
		this.width = width;
		this.height = height;
		this.boundX=0;
		this.boundY=0;
		this.x=0;
		this.y=0;
		this.box.setFill(Color.ALICEBLUE);
    	
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


	public void setBox(Rectangle box) {
		this.box = box;
	}


	public Animation getSprite() {
		return sprite;
	}


	public void setSprite(Animation sprite) {
		this.sprite = sprite;
	}


	public String getPic_path() {
		return image_Path;
	}


	public void setPic_path(String pic_path) {
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
		this.imageView.setTranslateX(x-boundX);
	}


	public int getY() {
		return (int) this.box.getTranslateY();
	}


	public void setY(int y) {
		this.y = y;
		this.box.setTranslateY(y);
		this.imageView.setTranslateY(y-boundY);
	}


	public ImageView getImageView() {
		return imageView;
	}
    
    
    
}
