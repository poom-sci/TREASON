package item.character;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.text.StyledEditorKit.BoldAction;

import gui.GameButton;
import gui.SpriteAnimation;
import item.weapon.Weapon;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import logic.Action;

public class MainCharacter extends GameCharacter {

	private int point;
	private boolean isBlink = false;

	public MainCharacter(int initX, int initY) {
		super("main_character.png", initX, initY, 30, 60, 200);

		pictureWidth = 96;
		pictureHeight = 96;
		pictureOffsetX = 30;
		pictureOffsetY = 0;

		point = 0;
		turnTime = Duration.millis(1000);
		walkTime = Duration.millis(1000);
		fireTime = Duration.millis(200);
		dieTime = Duration.millis(2000);
		jumpTime = Duration.millis(1000);

	}

	public void blink() {
		isBlink = true;
//		if(image_Path=="main_character.png") {
//			image_Path="main_character_red.png";
//
//		}
//		else {
//			image_Path="main_character.png";
//		}
		if (imageView.getOpacity() == 1) {
			imageView.setOpacity(0.5);
		}else {
			imageView.setOpacity(1);
		}
//		createAnimation();
	}

	public boolean isBlink() {
		return isBlink;
	}

	public void setBlink(boolean isBlink) {
		this.isBlink = isBlink;
	}

	public void addPoint(int i) {
		this.point += i;
	}

	public int getPoint() {
		return point;
	}

}
