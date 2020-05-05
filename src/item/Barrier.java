package item;

import gui.SpriteAnimation;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Barrier extends Entity {


	public Barrier(int initX, int initY) {

		super(initX, initY, 80, 80);
		this.image_Path = "lightBarrier.png";
		this.box.setFill(Color.TRANSPARENT);

		imageView = new ImageView(new Image(image_Path));
		imageView.setViewport(new Rectangle2D(0, 0, 192, 192));
		imageView.setFitHeight(80);
		imageView.setFitWidth(80);
		imageView.setOpacity(0.5);
		this.boundX=15;

		sprite = new SpriteAnimation(imageView, Duration.millis(3000), 30, 5, 0, 0, 192, 192);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();

	}

}
