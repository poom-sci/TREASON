package item.box;

import item.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Door extends Entity {
	
	public Door( int width, int height) {
		super(width,height);
		this.image_Path="portal-white.png";
		this.box.setFill(Color.BROWN);
		
		image=new Image(image_Path);
		imageView = new ImageView(image);
	    imageView.setFitHeight(width); 
	    imageView.setFitWidth(height); 
	}
	
//	public Teleport(pla) {
//		
//	}
}
