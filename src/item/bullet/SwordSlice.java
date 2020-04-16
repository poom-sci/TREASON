package item.bullet;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SwordSlice extends Bullet {
	
	public SwordSlice(boolean isRight, int initX, int initY) {
		super(isRight,60,80, initX, initY);
		// TODO Auto-generated constructor stub
		this.disX=20;
		this.disY=20;
	
		this.isRight=isRight;
		this.name="slice";
		
		this.image_Path="rocket.png";
		this.image=new Image(image_Path);
		this.imageView = new ImageView(image);
	    imageView.setFitHeight(height); 
	    imageView.setFitWidth(width); 
	    
		if(!this.isRight) {
			imageView.setRotate(imageView.getRotate()+180);
		}
	}
}
