package item.bullet;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import gui.SpriteAnimation;

public class SwordSlice extends Bullet {

	public SwordSlice(boolean isRight, int initX, int initY) {
		super(isRight,initX,initY, 60, 80);
		// TODO Auto-generated constructor stub
		this.disX = 20;
		this.disY = 20;

		this.isRight = isRight;
		this.name = "slice";

		this.image_Path = "sword_slice.png";
		this.image = new Image(image_Path);
		this.imageView = new ImageView(image);
		this.imageView.setFitHeight(height+20);
		this.boundY=10;
		this.imageView.setFitWidth(width);
		this.imageView.setViewport(new Rectangle2D(0, 0, 110, 129));

		this.sprite = new SpriteAnimation(imageView, Duration.millis(200),4, 4, 0, 0, 110, 129);
		this.sprite.setCycleCount(1);
		this.sprite.play();

//		if (!this.isRight) {
//			imageView.setRotate(imageView.getRotate() + 180);
//		}
	}
}
