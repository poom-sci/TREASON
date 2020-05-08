package item.bullet;

import gui.SpriteAnimation;
import implement.Explodable;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

public class RocketBullet extends Bullet implements Explodable {

	public RocketBullet(boolean isRight, int initX, int initY) {
		super("rocket.png",isRight,initX,initY,60,30);
		// TODO Auto-generated constructor stub
		this.disX=800;
		this.disY=800;
		this.velocityX=2;
		damage=30;
		
	}

	@Override
	public void explode() {
		// TODO Auto-generated method stub
		AudioClip granade_sound = new AudioClip(ClassLoader.getSystemResource("explosion_sound.wav").toString());
		granade_sound.setVolume(0.1);
		granade_sound.play();
		
		this.imageView = new ImageView(new Image("bomb-sprite.png"));
		this.imageView.setViewport(new Rectangle2D(0, 0, 128, 128));
	    imageView.setFitHeight(175); 
	    imageView.setFitWidth(175); 
		
		this.sprite = new SpriteAnimation(this.imageView, Duration.millis(1000), 12, 4, 0, 0, 128, 128);
		this.sprite.setCycleCount(1);
		this.sprite.play();

		
		this.width=128;
		this.box.setWidth(128);
		this.height=128;
		this.box.setHeight(128);
		this.imageView.setX(this.box.getTranslateX());
		this.imageView.setY(this.box.getTranslateY()-70);
		if(!this.isRight) {
			this.box.setTranslateX(this.box.getTranslateX()-this.width/2);
			this.imageView.setX(this.box.getTranslateX()-20);
		}

		
		this.box.setTranslateY(this.box.getTranslateY()-this.height/2+20);

		
		
		
	}
}
