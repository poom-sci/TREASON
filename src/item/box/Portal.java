package item.box;

import gui.SpriteAnimation;
import item.Entity;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Portal extends Entity {
	
	public Portal( int initX,int initY,int width, int height) {
		super("portal-white.png", initX, initY,width,height);
//		imageView = new ImageView(new Image(image_Path));
////		imageView.setViewport(new Rectangle2D(0, 0, 182, 206));
//		imageView.setFitHeight(60);
//		imageView.setFitWidth(60);
//		
//		sprite = new SpriteAnimation(imageView, Duration.millis(1000), 8, 4, 0, 206, 182, 206);
//		sprite.setCycleCount(Animation.INDEFINITE);
//		sprite.play();
	}
	
	public void Teleport(int level) {
		
	}
}
