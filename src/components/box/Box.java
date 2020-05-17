package components.box;

import components.Entity;
import gui.SpriteAnimation;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public abstract class Box extends Entity {

	public Box(String image_path,int initX,int initY, int width, int height) {
		super( image_path,initX, initY,width,height);
	}
	

}
