package item.enemy;

import java.util.ArrayList;

import gui.SpriteAnimation;
import item.character.GameCharacter;
import item.weapon.BombGun;
import item.weapon.Gun;
import item.weapon.Weapon;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import logic.Action;

public class BossEnemy extends GameCharacter {
	

	public BossEnemy(int initX, int initY) {
		super("boss.png", initX, initY, 100, 160, 1000);
		Weapon gun = new BombGun(1);
		this.weaponsInventory.add(gun);
		velocityX=2;

		point = 5000;
		pictureWidth = 122;
		pictureHeight = 110;
		pictureOffsetX = pictureWidth * 0;
		pictureOffsetY = pictureHeight * 0;
		boundX = 53;
		boundY = 30;

		turnTime = Duration.millis(1000);
		walkTime = Duration.millis(1000);
		fireTime = Duration.millis(200);
		dieTime = Duration.millis(2000);
		jumpTime = Duration.millis(1000);

		setX(initX);
		setY(initY);

		sprite.stop();
		imageView.setViewport(new Rectangle2D(pictureOffsetX, pictureOffsetY, pictureWidth, pictureHeight));
		imageView.setFitWidth(200);
		imageView.setFitHeight(200);
		sprite = new SpriteAnimation(imageView, Duration.millis(2000), 8, 4, pictureOffsetX, pictureOffsetY,
				pictureWidth, pictureHeight);
		sprite.setCycleCount(Animation.INDEFINITE);
		sprite.play();
	}

}
