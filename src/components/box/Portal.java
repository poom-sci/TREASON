package components.box;

import components.Entity;
import gui.SpriteAnimation;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Portal extends Entity {
	
	private int level;
	
	public Portal( int initX,int initY,int width, int height) {
		super("portal-white.png", initX, initY,width,height);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	

	
}
