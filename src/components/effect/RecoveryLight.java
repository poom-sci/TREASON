package components.effect;

import components.Entity;
import gui.SpriteAnimation;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

public class RecoveryLight extends Effect {
	public RecoveryLight(int initX, int initY) {

		super("recovery.png",initX, initY, 80, 80);

		this.boundX=26;

		imageView.setViewport(new Rectangle2D(0, 0, 192, 192));
		sprite = new SpriteAnimation(imageView, Duration.millis(3000), 30, 5, 0, 0, 192, 192);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();

	}
}
