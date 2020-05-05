package item.enemy;

import java.util.ArrayList;

import gui.SpriteAnimation;
import item.Entity;
import item.character.GameCharacter;
import item.weapon.Gun;
import item.weapon.Weapon;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import logic.Action;

public class GunEnemy extends GameCharacter {

	public GunEnemy(int initX, int initY) {
		super("main_character.png", initX, initY, 30, 60, 50);
		Weapon gun = new Gun(1);
		this.inventory.add(gun);
		
		this.disX = 60 * 5;
		
		pictureWidth=96;
		pictureHeight=96;
		pictureOffsetX=30;
		pictureOffsetY=0;

		turnTime = Duration.millis(1000);
		walkTime = Duration.millis(1000);
		fireTime = Duration.millis(200);
		dieTime = Duration.millis(2000);
		jumpTime = Duration.millis(1000);
	}

}
