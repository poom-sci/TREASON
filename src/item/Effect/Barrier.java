package item.Effect;

import gui.SpriteAnimation;
import item.Entity;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Barrier extends Effect {


	public Barrier(int initX, int initY) {

		super("white.png",initX, initY, 80, 80);

		imageView.setOpacity(0);
		this.boundX=15;

//		sprite = new SpriteAnimation(imageView, Duration.millis(3000), 30, 5, 0, 0, 192, 192);
//		sprite.setCycleCount(Animation.INDEFINITE);
//		sprite.play();

	}

}
