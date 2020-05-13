package item.bullet;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import gui.SpriteAnimation;

public class SwordSlice extends Bullet {

	public SwordSlice(boolean isRight, int initX, int initY) {
		super("sword_slice.png",isRight,initX,initY, 50, 50);
		// TODO Auto-generated constructor stub
		this.disX = 30;
		this.disY = 20;
		
		damage=20;
		this.velocityX=2;

		this.addOnY=-15;
		this.boundY=-20;
		creatAnimation();

	}
	
	private void creatAnimation() {
		this.imageView.setViewport(new Rectangle2D(0, 0, 110, 129));

		this.sprite = new SpriteAnimation(imageView, Duration.millis(500),4, 4, 0, 0, 110, 129);
		this.sprite.setCycleCount(Animation.INDEFINITE);
		this.sprite.play();
	}
}
