package item.box;

import gui.SpriteAnimation;
import item.Entity;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Box extends Entity {
	
	
	
	public Box(int initX,int initY, int width, int height) {
		super( initX, initY,width,height);
		this.image_Path="box.jpg";
		this.box.setFill(Color.ALICEBLUE);
		
		image=new Image(image_Path);
		imageView = new ImageView(image);
	    imageView.setFitHeight(width); 
	    imageView.setFitWidth(height); 
	}
	

}
