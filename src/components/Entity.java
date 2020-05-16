package components;

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
//	protected int addOnX;
//	protected int addOnY;
	
	protected double finalTime;

	
    public Entity(String image_Path,int initX,int initY,int width,int height) {
    	box=new Rectangle(width,height);
		this.width = width;
		this.height = height;
		this.box.setFill(Color.TRANSPARENT);
		
		this.initX=initX;
		this.initY=initY;
		
		image=new Image(image_Path);
		imageView = new ImageView(image);
	    imageView.setFitHeight(height); 
	    imageView.setFitWidth(width); 

		
		setX(initX);
		setY(initY);
    	
	}


	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}

	public Rectangle getBox() {
		return box;
	}


	public String getImage_Path() {
		return image_Path;
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


	public int getDisY() {
		return disY;
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


	public double getFinalTime() {
		return finalTime;
	}


	public void setFinalTime(double finalTime) {
		this.finalTime = finalTime;
	}
	
	
    
    
}
