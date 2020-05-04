package logic;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import javax.swing.text.StyledEditorKit.BoldAction;

import gui.SpriteAnimation;
import item.bullet.Bomb;
import item.bullet.Bullet;
import item.bullet.GunBullet;
import item.bullet.RocketBullet;
import item.bullet.SwordSlice;
import item.character.MainCharacter;
import item.enemy.BossEnemy;
import item.enemy.Enemy;
import item.weapon.BombGun;
import item.weapon.Gun;
import item.weapon.RocketGun;
import item.weapon.Sword;
import item.weapon.Weapon;
import item.DelayItem;
import item.Entity;
import item.box.Box;
import item.box.Portal;
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

	private ArrayList<Entity> platforms = new ArrayList<Entity>();
	private ArrayList<Portal> portals = new ArrayList<Portal>();
	private Rectangle bg;
	private ArrayList<Bullet> playerBullets = new ArrayList<Bullet>();
	private ArrayList<DelayItem> delaylist = new ArrayList<DelayItem>();

	private int weaponKey = 0;

	private ArrayList<Bomb> bossBombList = new ArrayList<Bomb>();
	private ArrayList<BossEnemy> bossList = new ArrayList<BossEnemy>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
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

	public GameController(Pane gameRoot) {
		this.gameRoot = gameRoot;

		initContentLevel1();
		createPlayer();

		createRocketGun();
		createGun();
		createSword();

	}

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

	public void getControl() {

		if (player.getVelocityY() < 10) {
			player.addVelocityY(1);
		}
		if (player.getY() <= levelHeight) {
			movePlayerY(player.getVelocityY());
		}

		moveBossBomb();

		if (player.getHealth() <= 0 || player.getY() >= levelHeight) {
			if (!isDie) {
				if (player.getIsWalkLeft() || player.getIsTurnLeft()) {
					player.doDieLeft();
				} else if (player.getIsWalkRight() || player.getIsTurnRight()) {
					player.doDieRight();
				}
				isDie = true;
			} else {
				System.out.println("gameOVER");
			}
		} else {
			try {
				System.out.println(Math.round(time) % 5);
//
				if ((Math.round(time) % 3) == 0) {
					if (!isEnemyFire) {
						enemyFireBullet();
						isEnemyFire = true;
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

				if (isPressed(KeyCode.P)) {
					this.player.decreasedHealth(40);
				}

				if (!isKeyboardPressed()) {
					if (player.getIsWalkLeft() || player.getIsFireLeft()) {
						player.doTurnLeft();
					}
					if (player.getIsWalkRight() || player.getIsFireRight()) {
						player.doTurnRight();
					}
				}

//				if (player.getIsWalkLeft() && alreadyPressedSPACE) {
//					player.doTurnLeft();
//				}
//				if((player.getIsWalkRight() && alreadyPressedSPACE)) {
//					player.doTurnRight();
//				}

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
				if (offsetX > levelWidth - 1280) {
					gameRoot.setLayoutX(-(levelWidth - 1280));
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
		}
	}

	private void check() {
		moveEnemies();
		moveBullets();
		checkPlayerCollision();
		checkDelay();
	}

	private void createPlayer() {

		player = new MainCharacter(100, 0);
		player.setX(100);
		gameRoot.getChildren().addAll(player.getBox(), player.getImageView());

	}

	private void initContentLevel1() {
		bg = new Rectangle(1280, 720);
		bg.setFill(new ImagePattern(new Image("game_background.jpg", 1280, 720, false, true)));
		this.levelWidth = LevelData.LEVEL1[0].length() * 60;
		this.levelHeight = LevelData.LEVEL1.length * 60;

		for (int i = 0; i < LevelData.LEVEL1.length; i++) {
			String line = LevelData.LEVEL1[i];
			for (int j = 0; j < line.length(); j++) {
				switch (line.charAt(j)) {
				case '0':
					break;
				case '1':
					Box platform = new Box(0, 0, 60, 60);
					platform.setX(j * 60);
					platform.setY(i * 60);
					platforms.add(platform);
					gameRoot.getChildren().add(platform.getImageView());
					break;
				case '2':
					Enemy enemy = new Enemy(j * 60, i * 60);
					enemy.setX(j * 60);
					enemy.setY(i * 60);
					enemies.add(enemy);
					gameRoot.getChildren().addAll(enemy.getBox());
					gameRoot.getChildren().add(enemy.getImageView());

					break;
				case '3':
//					System.out.println("33333333333");
					Portal door = new Portal(j * 60, i * 60, 60, 60);
					door.setX(j * 60);
					door.setY(i * 60);
					portals.add(door);
					gameRoot.getChildren().add(door.getImageView());
					break;
				case 'B':
					BossEnemy boss = new BossEnemy(j * 60, i * 60);
					boss.setX(j * 60);
					boss.setY(i * 60);
					bossList.add(boss);
					gameRoot.getChildren().addAll(boss.getBox());
					gameRoot.getChildren().add(boss.getImageView());

					break;

				}
			}
		}
	}

	private void initContentLevel2() {
		if (platforms != null) {
			platforms.clear();
			portals.clear();
		}
		bg = new Rectangle(1280, 720);
		bg.setFill(new ImagePattern(new Image("game_background.jpg", 1280, 720, false, true)));
		this.levelWidth = LevelData.LEVEL1[0].length() * 60;
		this.levelHeight = LevelData.LEVEL1.length * 60;

		for (int i = 0; i < LevelData.LEVEL2.length; i++) {
			String line = LevelData.LEVEL2[i];
			for (int j = 0; j < line.length(); j++) {
				switch (line.charAt(j)) {
				case '0':
					break;
				case '1':
					Box platform = new Box(0, 0, 60, 60);
					platform.setX(j * 60);
					platform.setY(i * 60);
					platforms.add(platform);
					gameRoot.getChildren().add(platform.getImageView());
					break;
				case '2':
					Enemy enemy = new Enemy(j * 60, i * 60);
					enemy.setX(j * 60);
					enemy.setY(i * 60);
					enemies.add(enemy);
					gameRoot.getChildren().add(enemy.getImageView());
					break;
				case '3':
					Portal door = new Portal(j * 60, i * 60, 60, 60);
					door.setX(j * 60);
					door.setY(i * 60);
					portals.add(door);
					gameRoot.getChildren().add(door.getImageView());
					break;
				}
			}
		}

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
				System.out.println(granade_sound.getVolume());
				granade_sound.play();
			} else if (bullet instanceof SwordSlice) {
				AudioClip granade_sound = new AudioClip(ClassLoader.getSystemResource("sword_sound.wav").toString());
				granade_sound.setVolume(0.2);
				System.out.println(granade_sound.getVolume());
				granade_sound.play();
			}

		} catch (FireBulletFailedException e) {
			System.out.println("Fire bullet failed, " + e.message);
		}

	}

	private void enemyFireBullet() {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			int distance = enemy.getX() - player.getX();
			if (Math.abs(distance) < 300) {
				boolean isBeforeRight = enemy.isRight();
				boolean isRight = distance < 0;

				if (distance < 0) {
					enemy.doFireRight();
				} else {
					enemy.doFireLeft();
				}

				Bullet bullet = enemy.getWeapon().fireBulletInfinite(enemy, isRight);
				enemyBullets.add(bullet);
				gameRoot.getChildren().addAll(bullet.getBox(), bullet.getImageView());

				if (isBeforeRight) {
					enemy.doWalkRight();
				} else {
					enemy.doWalkLeft();
				}
			}

		}
	}

	private void checkPortal() {

		int size = portals.size();
		for (int i = 0; i < size;) {
			Portal portal = portals.get(i);
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
		initContentLevel2();
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
		if (!enemies.isEmpty()) {
			for (int i = 0; i < enemies.size(); i++) {
				Enemy enemy = enemies.get(i);
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

	private boolean isReleased(KeyCode key) {
		return keys.getOrDefault(key, true);
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

	private void delayImage(Entity item, Double duration) {
		DelayItem delayBullet = new DelayItem(time, duration, item);
		delaylist.add(delayBullet);
	}

	private void checkDelay() {
		if (delaylist.size() == 0) {
			return;
		}
		int size = delaylist.size();
		for (int i = 0; i < size;) {
			if (delaylist.get(i).getFinalTime() <= time) {

				Entity item = delaylist.get(i).getItem();

				gameRoot.getChildren().remove(item.getImageView());
				gameRoot.getChildren().remove(item.getBox());
//				System.out.println("remove+++++++++++++++" + delaylist.get(i).getFinalTime());
				size--;
				delaylist.remove(i);
				continue;
			}
			i++;
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

					if (item instanceof RocketBullet) {
						gameRoot.getChildren().remove(item.getImageView());
						gameRoot.getChildren().remove(item.getBox());
						((RocketBullet) item).explode();
						checkEnemyCollision(item);
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
						gameRoot.getChildren().remove(item.getImageView());
						gameRoot.getChildren().remove(item.getBox());
						((RocketBullet) item).explode();
						delayImage(item, 1.0);
						playerBullets.remove(item);
						gameRoot.getChildren().addAll(item.getImageView(), item.getBox());
						return;
					}
					if (item instanceof SwordSlice) {
						delayImage(item, 0.2);
						playerBullets.remove(item);
						gameRoot.getChildren().addAll(item.getImageView(), item.getBox());
						return;
					}

					gameRoot.getChildren().remove(item.getImageView());
					gameRoot.getChildren().remove(item.getBox());

					playerBullets.remove(item);
					enemyBullets.remove(item);
					return;
				}
			}

		}
		item.setX(item.getX() + (movingRight ? 10 : -10));
	}

	private void moveEnemyX(Enemy enemy, int value) {
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

	public void setTime(Double time) {
		this.time = time;
	}

	private boolean checkEnemyCollision(Entity item) {
		int size = enemies.size();
		boolean isHit = false;
		for (int i = 0; i < size;) {
			Enemy enemy = enemies.get(i);
			if (item.getBox().getBoundsInParent().intersects(enemies.get(i).getBox().getBoundsInParent())) {
				gameRoot.getChildren().remove(enemy.getImageView());
				gameRoot.getChildren().remove(enemy.getHighBox());
				gameRoot.getChildren().remove(enemy.getLowBox());
				enemies.remove(enemy);
				player.addPoint(100);

				size -= 1;
				isHit = true;
				continue;
			}
			i += 1;
		}
		return isHit;
	}

	private void checkPlayerCollision() {
		if (checkEnemyCollision(player)) {
			this.player.decreasedHealth(50);
		}

		int size = enemyBullets.size();
		for (int i = 0; i < size;) {

			Bullet bullet = enemyBullets.get(i);
			if (player.getBox().getBoundsInParent().intersects(bullet.getBox().getBoundsInParent())) {
				gameRoot.getChildren().remove(bullet.getImageView());
				gameRoot.getChildren().remove(bullet.getBox());
				enemyBullets.remove(bullet);
				size -= 1;
				this.player.decreasedHealth(20);
				continue;
			}
			i++;

		}

	}

	private void jumpPlayer() {
		if (canJump) {
			player.setVelocityY(-20);
			canJump = false;
		}
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

	public Rectangle getPlayerHealthBox() {
		return player.getHealthBox();
	}

	public Weapon getPlayerWeapon() {
		return player.getWeapon();
	}
	
	public int getPlayerPoint() {
		return player.getPoint();
	}

}
