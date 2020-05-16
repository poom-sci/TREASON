package components.bullet;

import element.AudioLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

public class GunBullet extends Bullet {
	
	
	public GunBullet(boolean isRight, int initX,int initY) {
		
		super("bullet.png",isRight,initX,initY,20,20);
		this.imageView.setScaleX(12);
		this.imageView.setScaleY(12);
		this.boundY=-20;
		this.disX=300;
		this.disY=300;
		damage=10;
		this.velocityX=2;
//	    this.boundY=15;
	}
	
	
}
