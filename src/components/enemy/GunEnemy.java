package components.enemy;

import java.util.ArrayList;

import components.Entity;
import components.character.GameCharacter;
import components.weapon.Gun;
import components.weapon.Weapon;
import gui.SpriteAnimation;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class GunEnemy extends GameCharacter {

	public GunEnemy(int initX, int initY) {
		super("enemy_character.png", initX, initY, 30, 60, 20);
		Weapon gun = new Gun(1);
		this.weaponsInventory.add(gun);
		
		point=200;
		this.disX = 60 * 5;
		velocityX=1;
		
		boundX = 32;
		boundY = 18;
		
		pictureWidth=96;
		pictureHeight=96;
		pictureOffsetX=30;
		pictureOffsetY=0;

		turnTime = Duration.millis(1000);
		walkTime = Duration.millis(1000);
		fireTime = Duration.millis(200);
		dieTime = Duration.millis(2000);
		
		super.createAnimation();
	}

}
