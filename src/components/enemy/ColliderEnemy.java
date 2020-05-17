package components.enemy;

import java.util.ArrayList;

import components.character.GameCharacter;
import components.weapon.Gun;
import components.weapon.Weapon;
import gui.SpriteAnimation;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ColliderEnemy extends GameCharacter {

	public ColliderEnemy(int initX, int initY) {
		super("enemy_character.png",initX, initY, 30, 60, 30);
		
		point=100;
		this.disX = 60 * 5;
		velocityX=1;

		this.pictureWidth=96;
		this.pictureHeight=96;
		this.pictureOffsetX=30;
		this.pictureOffsetY=0;
		
		turnTime = Duration.millis(1000);
		walkTime = Duration.millis(1000);
		fireTime = Duration.millis(200);
		dieTime = Duration.millis(2000);

	}

}
