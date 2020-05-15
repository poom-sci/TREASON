package components.effect;

import gui.SpriteAnimation;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

public class Light extends Effect {
	
	public Light(int initX, int initY)  {

		super("light1.png",initX, initY, 120, 120);

		imageView.setOpacity(0.4);
		this.boundX=50;
		this.boundY=40;
		

		imageView.setViewport(new Rectangle2D(0, 0, 192, 192));
		sprite = new SpriteAnimation(imageView, Duration.millis(3000), 30, 5, 0, 0, 192, 192);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();

	}
}
