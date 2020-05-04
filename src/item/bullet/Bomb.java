package item.bullet;

import gui.SpriteAnimation;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import logic.Explodable;

public class Bomb extends Bullet implements Explodable {
	
	public Bomb(boolean isRight, int initX, int initY) {
		super(isRight,initX,initY,40,40);
		// TODO Auto-generated constructor stub
		this.disX=800;
		this.disY=800;

		this.isRight=isRight;
		this.name="rocket";
		
		this.image_Path="bomb.png";
		this.image=new Image(image_Path);
		this.imageView = new ImageView(image);
	    imageView.setFitHeight(height); 
	    imageView.setFitWidth(width); 
	    this.boundY=0;
	    
		this.sprite = new SpriteAnimation(this.imageView, Duration.millis(2000),15 , 2, 0, 0, 258, 250);
		this.sprite.setCycleCount(Animation.INDEFINITE);
		this.sprite.play();

		if(!this.isRight) {
			imageView.setRotate(imageView.getRotate()+180);
			
		}
	}

	@Override
	public void explode() {
		// TODO Auto-generated method stub
		
		this.imageView = new ImageView(new Image("bomb-sprite.png"));
//		this.imageView.setViewport(new Rectangle2D(0, 0, 128, 128));
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

		
		System.out.println(100000000);
		
		
	}
}
