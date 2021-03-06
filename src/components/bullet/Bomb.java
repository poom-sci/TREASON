package components.bullet;

import element.AudioLoader;
import gui.SpriteAnimation;
import implement.Explodable;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Bomb extends Bullet implements Explodable {

	public Bomb(boolean isRight, int initX, int initY) {
		super("bomb.png", isRight, initX, initY, 40, 40);
		// TODO Auto-generated constructor stub
		this.disX = 800;
		this.disY = 800;
		damage=50;
		
//		this.box.setFill(Color.AZURE);

		this.sprite = new SpriteAnimation(this.imageView, Duration.millis(2000), 15, 2, 0, 0, 258, 250);
		this.sprite.setCycleCount(Animation.INDEFINITE);
		this.sprite.play();
		this.velocityX=1;
	}

	@Override
	public void explode() {
		AudioClip Explosion_Sound =AudioLoader.Explosion_Sound;
//		Explosion_Sound.setVolume(0.5);
		Explosion_Sound.play();

		this.imageView = new ImageView(new Image("bomb-sprite.png"));
		this.imageView.setViewport(new Rectangle2D(0, 0, 128, 128));
		imageView.setFitHeight(175);
		imageView.setFitWidth(175);

		this.sprite = new SpriteAnimation(this.imageView, Duration.millis(1000), 12, 4, 0, 0, 128, 128);
		this.sprite.setCycleCount(1);
		this.sprite.play();

		this.width = 128;
		this.box.setWidth(128);
		this.height = 128;
		this.box.setHeight(128);
		this.imageView.setX(this.box.getTranslateX()-this.width / 2 );
		this.imageView.setY(this.box.getTranslateY() - this.height / 2 );
		this.box.setTranslateX(this.box.getTranslateX() - this.width / 2 + 20);
		this.box.setTranslateY(this.box.getTranslateY() - this.height / 2 + 20);

	}
}
