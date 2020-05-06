package logic;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import javax.swing.text.StyledEditorKit.BoldAction;

import com.sun.javafx.geom.Shape;

import gui.SpriteAnimation;
import item.bullet.Bomb;
import item.bullet.Bullet;
import item.bullet.GunBullet;
import item.bullet.RocketBullet;
import item.bullet.SwordSlice;
import item.character.GameCharacter;
import item.character.MainCharacter;
import item.consumable.ConPotion;
import item.enemy.BossEnemy;
import item.enemy.ColliderEnemy;
import item.enemy.GunEnemy;
import item.weapon.BombGun;
import item.weapon.Gun;
import item.weapon.RocketGun;
import item.weapon.Sword;
import item.weapon.Weapon;
import item.Barrier;
import item.DelayItem;
import item.Entity;
import item.box.Ammo;
import item.box.Box;
import item.box.Oak;
import item.box.Portal;
import item.box.Potion;
import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GameController {

	private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
	private MainCharacter player;
	private Pane gameRoot;
	private Double time;
	LevelData levelD;

	private ArrayList<Entity> platforms = new ArrayList<Entity>();
	private ArrayList<Portal> portalList = new ArrayList<Portal>();
	private ArrayList<Bullet> playerBullets = new ArrayList<Bullet>();
	private ArrayList<DelayItem> delaylist = new ArrayList<DelayItem>();
	private Rectangle bg;

//	private ArrayList<Potion> potions = new ArrayList<Potion>();
//	private ArrayList<Ammo> ammoBoxes = new ArrayList<Ammo>();
//	private ArrayList<Oak> trees = new ArrayList<Oak>();

	private int counter = 0;

	private ArrayList<Bomb> bossBombList = new ArrayList<Bomb>();
	private ArrayList<BossEnemy> bossList = new ArrayList<BossEnemy>();
	private ArrayList<GameCharacter> enemieList = new ArrayList<GameCharacter>();
	private ArrayList<Bullet> enemyBullets = new ArrayList<Bullet>();

	private int levelWidth;
	private int levelHeight;

	private boolean canJump;

	private boolean alreadyPressedQ = false;
	private boolean alreadyPressedE = false;
	private boolean alreadyPressedW = false;
	private boolean alreadyPressedSPACE = false;

	private boolean isEnemyFire = false;

	private int offsetX;
	private int offsetY;

	private boolean isDie = false;
	private boolean isBarrierOpen = false;
	private Barrier barrier;

	public GameController() {
		createLevel(1);

//		createPotion();
	}

	private void createLevel(int level) {

		if (gameRoot != null) {
			platforms.clear();
			portalList.clear();
			bossList.clear();
			enemieList.clear();
			gameRoot.getChildren().clear();
		}

		levelD = LevelData.getInstance();
		levelD.loadLevel(level);

		platforms.addAll(levelD.getPlatforms());
		portalList.addAll(levelD.getPortalList());
		bossList = levelD.getBossList();
		enemieList.addAll(levelD.getEnemieList());

		gameRoot = levelD.getGameRoot();
		bg = levelD.getBg();
		this.levelHeight = levelD.getLevelHeight();
		this.levelWidth = levelD.getLevelWidth();
		if (player == null) {
			createPlayer();
		} else {
			player.setX(200);
			gameRoot.getChildren().addAll(player.getBox(), player.getImageView());
		}

	}

	private void createPlayer() {

		player = new MainCharacter(200, 0);
//		player.doTurnLeft();
		gameRoot.getChildren().addAll(player.getBox(), player.getImageView());

		createRocketGun();
		createGun();
		createSword();

	}

//	private void createPotion() {
//		ConPotion potion = new ConPotion(1);
//		player.getItems().add(potion);
//	}

	private void createGun() {
		Weapon gun = new Gun(20);
		player.getInventory().add(gun);
	}

	private void createRocketGun() {
		Weapon rocketGun = new RocketGun(10);
		player.getInventory().add(rocketGun);
	}

	private void createSword() {
		Weapon sword = new Sword(1);
		player.getInventory().add(sword);
	}

	private void gravity() {
		for (int i = 0; i < enemieList.size(); i++) {
			GameCharacter enemy = enemieList.get(i);
			if (enemy.getVelocityY() < 10) {
				enemy.addVelocityY(1);
			}
		}
	}

	public void getControl() {
		try {
			counter += 1;

			gravity();

			if (player.getVelocityY() < 10) {
				player.addVelocityY(1);
			}
			if (player.getY() <= levelHeight) {
				movePlayerY(player.getVelocityY());
			}

			if (player.getCurrentHP() <= 0 || player.getY() >= levelHeight) {

				if (!isDie) {
					if (player.getIsWalkLeft() || player.getIsTurnLeft()) {
						player.doDieLeft();
					} else if (player.getIsWalkRight() || player.getIsTurnRight()) {
						player.doDieRight();
					}
					isDie = true;
				}
			} else {
				if (isBarrierOpen) {
					barrier.setX(offsetX);
					barrier.setY(offsetY);
					if (counter % 5 == 0) {
						player.blink();

					}
				}

				if ((Math.round(time) % 3) == 0) {
					if (!isEnemyFire) {
						enemyFireBullet();
						isEnemyFire = true;
						randomGunEnemyInRange(300, 200, 0, 0);
					}
				} else {
					isEnemyFire = false;
				}

				if (isPressed(KeyCode.ENTER)) {
					checkPortal();
				}

				offsetX = player.getX();
				offsetY = player.getY();

				check();

				if (!isKeyboardPressed()) {
					if (player.getIsWalkLeft() || player.getIsFireLeft()) {
						player.doTurnLeft();
					}
					if (player.getIsWalkRight() || player.getIsFireRight()) {
						player.doTurnRight();
					}
				}

				if (isPressed(KeyCode.W) && player.getX() >= 5) {
					if (!alreadyPressedW) {
						jumpPlayer();
						alreadyPressedW = true;
					}
				} else {
					alreadyPressedW = false;
				}

				if (isPressed(KeyCode.A) && player.getX() >= 5) {
					movePlayerX(-4);
					if (!player.getIsWalkLeft()) {
						player.doWalkLeft();
					}
				}

				if (isPressed(KeyCode.D) && player.getX() + player.getWidth() <= levelWidth - 5) {
					movePlayerX(4);
					if (!player.getIsWalkRight()) {
						player.doWalkRight();
					}
				}

				if (isPressed(KeyCode.Q)) {
					if (!alreadyPressedQ) {
						player.changeWeaponLeft();
						alreadyPressedQ = true;
					}
				} else {
					alreadyPressedQ = false;
				}

				if (isPressed(KeyCode.E)) {
					if (!alreadyPressedE) {
						player.changeWeaponRight();
						alreadyPressedE = true;
					}
				} else {
					alreadyPressedE = false;
				}

				if (isPressed(KeyCode.SPACE)) {
					if (!alreadyPressedSPACE) {
						if (player.getIsWalkRight() || player.getIsTurnRight()) {
							if (!player.getIsFireRight()) {
								player.doFireRight();
								fireBullet(true);
							}
						}
						if (player.getIsWalkLeft() || player.getIsTurnLeft()) {
							if (!player.getIsFireLeft()) {
								player.doFireLeft();
								fireBullet(false);

							}

						}
						alreadyPressedSPACE = true;
					}
				} else {
					alreadyPressedSPACE = false;
				}

				// follow player x
				
				if (offsetX > 640 && offsetX < levelWidth - 640) {
					gameRoot.setLayoutX(-(offsetX - 640));
				}
				else if (offsetX > levelWidth - 1280) {
					gameRoot.setLayoutX(-(levelWidth - 1280));
				}
				
				if (isPressed(KeyCode.P)) {
					this.gameRoot.setLayoutX(0);
					createLevel(2);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		System.gc();

	}

	private void check() {
		moveEnemies();

		moveBullets();

		checkPlayerCollision();
		checkDelay();
	}

	private void fireBullet(boolean isRight) {
		try {
			Bullet bullet = player.getWeapon().fireBullet(player, isRight);
			playerBullets.add(bullet);
			gameRoot.getChildren().addAll(bullet.getBox(), bullet.getImageView());

			if (bullet instanceof RocketBullet) {
				AudioClip granade_sound = new AudioClip(ClassLoader.getSystemResource("granade_sound.wav").toString());
				granade_sound.setVolume(0.2);
				granade_sound.play();
			} else if (bullet instanceof GunBullet) {
				AudioClip granade_sound = new AudioClip(ClassLoader.getSystemResource("gun_sound.wav").toString());
				granade_sound.setVolume(0.2);
				granade_sound.play();
			} else if (bullet instanceof SwordSlice) {
				AudioClip granade_sound = new AudioClip(ClassLoader.getSystemResource("sword_sound.wav").toString());
				granade_sound.setVolume(0.2);
				granade_sound.play();
			}
		} catch (FireBulletFailedException e) {
			System.out.println("Fire bullet failed, " + e.message);
		}
	}

	private void enemyFireBullet() {
		for (int i = 0; i < enemieList.size(); i++) {

			GameCharacter enemyCharacter = enemieList.get(i);
			int distance = enemyCharacter.getX() - player.getX();
			if (Math.abs(distance) < 300) {
				boolean isBeforeRight = enemyCharacter.isRight();
				boolean isRight = distance < 0;

				if (distance < 0) {

					enemyCharacter.doFireRight();

				} else {
					enemyCharacter.doFireLeft();

				}

				try {
					Bullet bullet = enemyCharacter.getWeapon().fireBulletInfinite(enemyCharacter, isRight);
					enemyBullets.add(bullet);
					gameRoot.getChildren().addAll(bullet.getImageView());
				} catch (Exception e) {
					// TODO: handle exception
//					System.out.println(e);
				}

				if (isBeforeRight) {

					enemyCharacter.doWalkRight();

				} else {

					enemyCharacter.doWalkLeft();
				}
			}
		}
	}

	private void checkPortal() {
		int size = portalList.size();
		for (int i = 0; i < size;) {
			Portal portal = portalList.get(i);
			if (player.getBox().getBoundsInParent().intersects(portal.getBox().getBoundsInParent())) {

				changeMap();
				size -= 1;
				continue;
			}
			i += 1;
		}
	}

	private void changeMap() {
		gameRoot.getChildren().clear();
		gameRoot.setLayoutX(0);

		gameRoot.getChildren().addAll(player.getBox(), player.getImageView());
		player.setX(100);
		player.setY(0);
	}

	private void moveBossBomb() {
		for (int i = 0; i < bossBombList.size(); i++) {
			Bomb bomb = bossBombList.get(i);
			bomb.addVelocityY(1);

			moveItemY(bomb, bomb.getVelocityY());
		}
	}

	private void moveBullets() {
		for (int i = 0; i < playerBullets.size(); i++) {
			if (playerBullets.get(i).isRight()) {
				moveItemX(playerBullets.get(i), 2);
			} else {
				moveItemX(playerBullets.get(i), -2);
			}
		}
		for (int i = 0; i < enemyBullets.size(); i++) {

			if (enemyBullets.get(i).isRight()) {
				moveEnemybulletX(enemyBullets.get(i), 2);
			} else {
				moveEnemybulletX(enemyBullets.get(i), -2);
			}
		}

	}

	private void moveEnemies() {
		if (!enemieList.isEmpty()) {
			for (int i = 0; i < enemieList.size(); i++) {
				GameCharacter enemy = enemieList.get(i);
				if (enemy.getY() <= levelHeight) {
					moveEnemyY(enemy, enemy.getVelocityY());
				}

				if (enemy.isRight()) {
					moveEnemyX(enemy, 2);
				} else {
					moveEnemyX(enemy, -2);
				}
			}
		}
	}

	private boolean isPressed(KeyCode key) {
		return keys.getOrDefault(key, false);
	}

	private boolean isKeyboardPressed() {

		for (Boolean e : keys.values()) {
			if (e.booleanValue()) {
				return true;
			}
		}
		return false;
	}

	private void movePlayerX(int value) {
		boolean movingRight = value > 0;

		for (int i = 0; i < Math.abs(value); i++) {
			for (Entity platform : platforms) {
				if (player.getBox().getBoundsInParent().intersects(platform.getBox().getBoundsInParent())) {
					if (movingRight) {
						if (player.getX() + player.getWidth() == platform.getX()) {
							player.setX(player.getX() - 1);
							return;
						}
					} else {
						if (player.getX() == platform.getX() + 60) {
							player.setX(player.getX() + 1);
							return;
						}
					}
				}
			}
			player.setX(player.getX() + (movingRight ? 1 : -1));
		}
	}

	private void moveItemX(Entity item, int value) {
		boolean movingRight = value > 0;

		for (int i = 0; i < Math.abs(value); i++) {

			for (Entity platform : platforms) {
				boolean isOutOfRange = Math.abs(item.getX() - item.getInitX()) > item.getDisX();
				boolean isCollisionPlatform = item.getBox().getBoundsInParent()
						.intersects(platform.getBox().getBoundsInParent());

				if (isOutOfRange || isCollisionPlatform || checkEnemyCollision(item)) {
					gameRoot.getChildren().remove(item.getImageView());
					gameRoot.getChildren().remove(item.getBox());

					if (item instanceof RocketBullet) {
						((RocketBullet) item).explode();
						checkEnemyCollision(item);
						delayImage(item, 1.0);
						playerBullets.remove(item);
						gameRoot.getChildren().addAll(item.getImageView(), item.getBox());
						return;
					}
					if (item instanceof SwordSlice) {
						playerBullets.remove(item);
						checkEnemyCollision(item);
						delayImage(item, 0.2);
						checkEnemyCollision(item);
						gameRoot.getChildren().addAll(item.getImageView(), item.getBox());
						return;
					}

					playerBullets.remove(item);
					enemyBullets.remove(item);
					return;
				}
			}

		}
		item.setX(item.getX() + (movingRight ? 10 : -10));
	}

	private void moveEnemybulletX(Entity item, int value) {
		boolean movingRight = value > 0;

		for (int i = 0; i < Math.abs(value); i++) {
			boolean isOutOfRange = Math.abs(item.getX() - item.getInitX()) > item.getDisX();
			for (Entity platform : platforms) {

				boolean isCollisionPlatform = item.getBox().getBoundsInParent()
						.intersects(platform.getBox().getBoundsInParent());

				if (isOutOfRange || isCollisionPlatform) {

					if (item instanceof RocketBullet) {
						checkEnemyCollision(item);
						gameRoot.getChildren().remove(item.getImageView());
						gameRoot.getChildren().remove(item.getBox());
						((RocketBullet) item).explode();
						delayImage(item, 1.0);
						playerBullets.remove(item);
						gameRoot.getChildren().addAll(item.getImageView(), item.getBox());
						return;
					}
					if (item instanceof SwordSlice) {
						checkEnemyCollision(item);
						delayImage(item, 0.2);
						playerBullets.remove(item);
						gameRoot.getChildren().addAll(item.getImageView(), item.getBox());
						return;
					}

					gameRoot.getChildren().remove(item.getImageView());
					gameRoot.getChildren().remove(item.getBox());
					playerBullets.remove(item);
					enemyBullets.remove(item);
					item = null;
					return;
				}
			}

		}
		item.setX(item.getX() + (movingRight ? 10 : -10));
	}

	private void moveEnemyX(GameCharacter enemy, int value) {
		boolean movingRight = value > 0;

		for (int i = 0; i < Math.abs(value); i++) {

			if (enemy.checkTurn(platforms)) {
				if (enemy.isRight()) {
					enemy.doWalkLeft();
				} else {
					enemy.doWalkRight();
				}
			}
		}
		enemy.setX(enemy.getX() + (movingRight ? 1 : -1));
	}

	private void movePlayerY(int value) {
		boolean movingDown = value > 0;

		for (int i = 0; i < Math.abs(value); i++) {
			for (Entity platform : platforms) {
				if (player.getBox().getBoundsInParent().intersects(platform.getBox().getBoundsInParent())) {
					if (movingDown) {
						if (player.getY() + player.getHeight() == platform.getY()) {
							player.setY(player.getY() - 1);
							canJump = true;
							return;
						}
					} else {
						if (player.getY() == platform.getY() + 60) {
							player.setY(player.getY() + 1);
							return;
						}
					}
				}
			}
			player.setY(player.getY() + (movingDown ? 1 : -1));
		}
	}

	private void jumpPlayer() {
		if (canJump) {
			player.setVelocityY(-20);
			canJump = false;
		}
	}

	private void moveEnemyY(GameCharacter enemy, int value) {
		boolean movingDown = value > 0;

		for (int i = 0; i < Math.abs(value); i++) {
			for (Entity platform : platforms) {
				if (enemy.getBox().getBoundsInParent().intersects(platform.getBox().getBoundsInParent())) {
					if (movingDown) {
						if (enemy.getY() + player.getHeight() == platform.getY()) {
							enemy.setY(enemy.getY() - 1);
							enemy.setOnFloor(true);
							return;
						}
					} else {
						if (enemy.getY() == platform.getY() + 60) {
							enemy.setY(enemy.getY() + 1);
							return;
						}
					}
				}
			}
			enemy.setY(enemy.getY() + (movingDown ? 1 : -1));
		}
	}

	private void moveItemY(Entity item, int value) {
		boolean movingDown = value > 0;

		for (int i = 0; i < Math.abs(value); i++) {
			for (Entity platform : platforms) {
				if (player.getBox().getBoundsInParent().intersects(platform.getBox().getBoundsInParent())) {
					if (movingDown) {
						if (item.getY() + player.getHeight() == platform.getY()) {
							item.setY(item.getY() - 1);
							return;
						}
					} else {
						if (item.getY() == platform.getY() + 60) {
							item.setY(item.getY() + 1);
							return;
						}
					}
				}
			}
			item.setY(item.getY() + (movingDown ? 1 : -1));
		}
	}

	private boolean checkEnemyCollision(Entity item) {
		int size = enemieList.size();
		boolean isHit = false;
		for (int i = 0; i < size;) {
			GameCharacter enemy = enemieList.get(i);
			if (item.getBox().getBoundsInParent().intersects(enemieList.get(i).getBox().getBoundsInParent())) {
//				if (item instanceof MainCharacter) {
//					enemy.decreasedHealth(50);
//				}
				if (item instanceof Bullet) {
					enemy.decreasedCurrentHP(((Bullet) item).getDamage());
				}
				if (isEnemyDie(enemy)) {
					size -= 1;
				}

				isHit = true;
			}
			i += 1;
		}
		return isHit;
	}

	private void checkPlayerCollision() {
		if (isBarrierOpen) {
			return;
		}
		if (checkEnemyCollision(player)) {
			player.decreasedCurrentHP(50);
			createBarrier();
		}

		int size = enemyBullets.size();
		for (int i = 0; i < size;) {
			Bullet bullet = enemyBullets.get(i);
			if (player.getBox().getBoundsInParent().intersects(bullet.getBox().getBoundsInParent())) {
				gameRoot.getChildren().remove(bullet.getImageView());
				gameRoot.getChildren().remove(bullet.getBox());
				enemyBullets.remove(bullet);
				bullet = null;
				size -= 1;
				player.decreasedCurrentHP(20);
				continue;
			}
			i++;

		}

	}

	private boolean isEnemyDie(GameCharacter enemyCharacter) {
		boolean isDie = false;
		if (enemyCharacter.getCurrentHP() <= 0) {
			delayImage(enemyCharacter, 1.5);
//			gameRoot.getChildren().remove(enemyCharacter.getImageView());
			enemieList.remove(enemyCharacter);
			player.addPoint(100);
			if (enemyCharacter.isRight()) {
				enemyCharacter.doDieRight();
			} else {
				enemyCharacter.doDieLeft();
			}
			isDie = true;
		}
		return isDie;
	}

	private void delayImage(Entity item, Double duration) {
		DelayItem delayBullet = new DelayItem(time, duration, item);
		delaylist.add(delayBullet);
	}

	private void checkDelay() {
		int size = delaylist.size();
		for (int i = 0; i < size;) {

			if (delaylist.get(i).getFinalTime() <= time) {

				Entity item = delaylist.get(i).getItem();
				if (item instanceof Barrier) {
					isBarrierOpen = false;
					player.setOpacityNormal();
				}
				size--;
				delaylist.remove(delaylist.get(i));
				removeEntity(item);
				continue;
			}
			i++;
		}
	}

	private void randomGunEnemyInRange(int x1, int x2, int y1, int y2) {
		int randomNumberX = (int) Math.round(Math.random() * (x2 - x1));
		int randomNumberY = (int) Math.round(Math.random() * (y2 - y1));
		int positionX = x1 + randomNumberX;
		int positionY = y1 + randomNumberY;
		GunEnemy enemy = new GunEnemy(positionX, positionY);
		enemy.doWalkLeft();
		enemieList.add(enemy);
		
		gameRoot.getChildren().add(enemy.getImageView());

	}

	private void removeEntity(Entity item) {
		enemieList.remove(item);
		enemyBullets.remove(item);
		playerBullets.remove(item);
		gameRoot.getChildren().remove(item.getImageView());

		item = null;
	}

	private void createBarrier() {
		barrier = new Barrier(offsetX, offsetY);
		delayImage(barrier, 1.5);
//		gameRoot.getChildren().addAll(barrier.getImageView(), barrier.getBox());
//		System.out.println("OPEN Barrier");
		isBarrierOpen = true;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	public MainCharacter getPlayer() {
		return player;
	}

	public ArrayList<Entity> getPlatforms() {
		return platforms;
	}

	public Pane getGameRoot() {
		return gameRoot;
	}

	public Rectangle getBg() {
		return bg;
	}

	public void setKeys(HashMap<KeyCode, Boolean> keys) {
		this.keys = keys;
	}

	public Weapon getPlayerWeapon() {
		return player.getWeapon();
	}

	public int getPlayerPoint() {
		return player.getPoint();
	}

}
