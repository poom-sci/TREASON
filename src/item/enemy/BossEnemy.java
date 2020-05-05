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

public class BossEnemy extends GameCharacter {
	protected Weapon weapon;

	public BossEnemy(int initX, int initY) {
		super("boss.png", initX, initY, 200, 200, 1000);
		this.weapon = new Gun(1);
		
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
