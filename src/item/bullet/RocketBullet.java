package item.bullet;

import gui.SpriteAnimation;
import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import logic.Explodable;

public class RocketBullet extends Bullet implements Explodable {

	public RocketBullet(boolean isRight, int initX, int initY) {
		super(isRight,60,30, initX, initY);
		// TODO Auto-generated constructor stub
		this.disX=800;
		this.disY=800;

		this.isRight=isRight;
		this.name="rocket";
		
		this.image_Path="rocket.png";
		this.image=new Image(image_Path);
		this.imageView = new ImageView(image);
	    imageView.setFitHeight(height); 
	    imageView.setFitWidth(60); 

		if(!this.isRight) {
			imageView.setRotate(imageView.getRotate()+180);
			
		}
	}

	@Override
	public void explode() {
		// TODO Auto-generated method stub
		this.width=150;
		this.box.setWidth(150);
		this.height=150;
		this.box.setHeight(150);
		
		if(!this.isRight) {
			this.box.setTranslateX(this.box.getTranslateX()-this.width/2);
		}
		
		this.box.setTranslateY(this.box.getTranslateY()-this.height/2);
		
		sprite = new SpriteAnimation(imageView, Duration.millis(200), 1, 1, 0, 0, 96, 96);
		sprite.setCycleCount(1);
		sprite.play();
		
		System.out.println(100000000);
		
		
	}
}
