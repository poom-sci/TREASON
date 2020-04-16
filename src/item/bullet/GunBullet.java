package item.bullet;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class GunBullet extends Bullet {
	
	
	public GunBullet(boolean isRight, int initX,int initY) {
		
		super(isRight,20,10,initX,initY);
		this.disX=300;
		this.disY=300;
		this.isRight=isRight;
		this.name="bullet";
		this.height=10;
		this.width=20;
		
		this.image_Path="bullet.png";
		this.image=new Image(image_Path);
		this.imageView = new ImageView(image);
	    imageView.setFitHeight(width*2); 
	    imageView.setFitWidth(height*2); 
		if(!this.isRight) {
			imageView.setRotate(imageView.getRotate()+180);
		}
	    this.boundY=15;
	}
	
	
}
