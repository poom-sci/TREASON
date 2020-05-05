package item.enemy;

import java.util.ArrayList;

import gui.SpriteAnimation;
import item.character.GameCharacter;
import item.weapon.Gun;
import item.weapon.Weapon;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import logic.Action;

public class ColliderEnemy extends GameCharacter {

	public ColliderEnemy(int initX, int initY) {
		super("main_character.png",initX, initY, 30, 60, 100);
		
		this.disX = 60 * 5;

		this.pictureWidth=96;
		this.pictureHeight=96;
		this.pictureOffsetX=30;
		this.pictureOffsetY=0;
		
		turnTime = Duration.millis(1000);
		walkTime = Duration.millis(1000);
		fireTime = Duration.millis(200);
		dieTime = Duration.millis(2000);
		jumpTime = Duration.millis(1000);

	}

}
