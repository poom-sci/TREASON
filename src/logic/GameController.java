package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import javax.swing.text.StyledEditorKit.BoldAction;

import item.bullet.Bullet;
import item.bullet.GunBullet;
import item.bullet.RocketBullet;
import item.bullet.SwordSlice;
import item.character.MainCharacter;
import item.enemy.Enemy;
import item.weapon.Gun;
import item.weapon.RocketGun;
import item.weapon.Sword;
import item.weapon.Weapon;
import item.DelayItem;
import item.Entity;
import item.box.Box;
import item.box.Portal;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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

	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Bullet> enemyBullets = new ArrayList<Bullet>();

	private int levelWidth;
	private int levelHeight;
	private Point2D playerVelocity = new Point2D(0, 0);
	private boolean canJump;

	private boolean alreadyPressedQ = false;
	private boolean alreadyPressedE = false;
	private boolean alreadyPressedW = false;
	private boolean alreadyPressedSPACE = false;

	private boolean isEnemyFire = false;

	private int width;
	private int height;
	private int offsetX;
	private int offsetY;

	private boolean isDie = false;

	public GameController(Pane gameRoot) {
		this.gameRoot = gameRoot;

		initContentLevel1();
		createPlayer();

		width = player.getWidth();
		height = player.getHeight();

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

		if (playerVelocity.getY() < 10) {
			playerVelocity = playerVelocity.add(0, 1);
		}
		if (player.getY() <= levelHeight) {
			movePlayerY((int) playerVelocity.getY());
		}

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

				checkDelay();

				if (isPressed(KeyCode.ENTER)) {
					checkPortal();
				}

				offsetX = player.getX();
				offsetY = player.getY();

				moveEnemies();
				moveBullets();
				checkPlayerCollision();

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

				if (isPressed(KeyCode.W) && player.getX() >= 5) {
					if (!alreadyPressedW) {
						jumpPlayer();
						alreadyPressedW = true;
					}
				} else {
					alreadyPressedW = false;
				}

				if (isPressed(KeyCode.A) && player.getX() >= 5) {
					if (!player.getIsWalkLeft()) {
						player.doWalkLeft();
					}
					movePlayerX(-4);
				}

				if (isPressed(KeyCode.Q)) {
					if (!alreadyPressedQ) {
						changeWeaponQ();
					}
				} else {
					alreadyPressedQ = false;
				}

				if (isPressed(KeyCode.E)) {
					if (!alreadyPressedE) {
						changeWeaponE();
					}
				} else {
					alreadyPressedE = false;
				}

				if (isPressed(KeyCode.D) && player.getX() + width <= levelWidth - 5) {
					movePlayerX(4);
					if (!player.getIsWalkRight()) {
						player.doWalkRight();
					}
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

//		 follow player y
//		int offsetY= player.getY();
//		if (offsetY > 360 && offsetY < 720 - 360) {
//			gameRoot.setLayoutY(-(offsetY - 360));
//		}
		}
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
//					System.out.println("111111111");
//					System.out.println(j+"------"+i);
					Box platform = new Box(0, 0, 60, 60);
					platform.setX(j * 60);
					platform.setY(i * 60);
					platforms.add(platform);
					gameRoot.getChildren().add(platform.getImageView());
					break;
				case '2':
//					System.out.println("2222222222222");
//					System.out.println(j+"++++++"+i);
					Enemy enemy = new Enemy(j * 60, i * 60);
					enemy.setX(j * 60);
					enemy.setY(i * 60);
					enemies.add(enemy);
					gameRoot.getChildren().add(enemy.getImageView());
					gameRoot.getChildren().addAll(enemy.getHighBox(),enemy.getLowBox());
					break;
				case '3':
//					System.out.println("33333333333");
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

	private void changeWeaponQ() {

		weaponKey -= 1;
		if (weaponKey < 0) {
			weaponKey += player.getInventory().size();
		}
		weaponKey = weaponKey % player.getInventory().size();
		alreadyPressedQ = true;
	}

	private void changeWeaponE() {
		weaponKey += 1;
		weaponKey = weaponKey % player.getInventory().size();
		alreadyPressedE = true;
	}

	private void fireBullet(boolean isRight) {
		if (!player.getInventory().get(weaponKey).isEmpty()) {
			Bullet bullet = player.getInventory().get(weaponKey).fireBullet(player, isRight);
			playerBullets.add(bullet);
			gameRoot.getChildren().addAll(bullet.getBox(), bullet.getImageView());
		}
	}

	private void enemyFireBullet() {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			boolean isRight = enemy.isRight();
			enemy.getSprite().stop();
			if (isRight) {
				enemy.doFireRight();
			} else {
				enemy.doFireLeft();
			}
			Bullet bullet = enemy.getWeapon().fireBulletInfinite(enemy, isRight);
			enemyBullets.add(bullet);
			gameRoot.getChildren().addAll(bullet.getBox(), bullet.getImageView());
			enemy.getSprite().pause();
			if (isRight) {
				enemy.doWalkRight();
			} else {
				enemy.doWalkLeft();
			}

		}
	}

	private void checkPortal() {
		int index = 0;
		int size = portals.size();
		for (int i = 0; index < size; i++) {
			if (player.getBox().getBoundsInParent().intersects(portals.get(index).getBox().getBoundsInParent())) {

				changeMap();
				size -= 1;
				continue;
			}
			index += 1;

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

	private void moveBullets() {
		if (!playerBullets.isEmpty()) {
			for (int i = 0; i < playerBullets.size(); i++) {

				if (playerBullets.get(i).isRight()) {
					moveItemX(playerBullets.get(i), 2);
				} else {
					moveItemX(playerBullets.get(i), -2);
				}
			}
		}

		if (!enemyBullets.isEmpty()) {
			for (int i = 0; i < enemyBullets.size(); i++) {

				if (enemyBullets.get(i).isRight()) {
					moveItemX(enemyBullets.get(i), 2);
				} else {
					moveItemX(enemyBullets.get(i), -2);
				}
			}
		}

	}

	private void moveEnemies() {
		if (!enemies.isEmpty()) {
			for (int i = 0; i < enemies.size(); i++) {
				Enemy enemy=enemies.get(i);
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
						if (player.getX() + width == platform.getX()) {
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
						if (player.getY() + height == platform.getY()) {
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
				System.out.println("remove+++++++++++++++" + delaylist.get(i).getFinalTime());
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

	private void moveEnemyX(Enemy enemy, int value) {
		boolean movingRight = value > 0;

		for (int i = 0; i < Math.abs(value); i++) {
			
			if (enemy.checkTurn(platforms)) {
				if(enemy.isRight()) {
					enemy.doWalkLeft();
				}
				else {
					enemy.doWalkRight();
				}
			}
//			boolean isTurn=false;
//			for (Entity platform : platforms) {
//				boolean isCollisionPlatform = item.getBox().getBoundsInParent()
//						.intersects(platform.getBox().getBoundsInParent());
//
//				if (isCollisionPlatform) {
//
//					isTurn=true;
//
//				}
//			}
//			if(isTurn) {
//				if(enemy.isRight()) {
//					enemy.doTurnLeft();
//				}
//				else {
//					enemy.doTurnRight();
//				}
//			}
		}
		enemy.setX(enemy.getX() + (movingRight ? 1 : -1));
	}

	private void moveItemY(Bullet item, int value) {
		boolean movingDown = value > 0;

		for (int i = 0; i < Math.abs(value); i++) {
			for (Entity platform : platforms) {
				if (player.getBox().getBoundsInParent().intersects(platform.getBox().getBoundsInParent())) {
					if (movingDown) {
						if (player.getY() + height == platform.getY()) {
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

//	private void clearBullet(Bullet item) {
//		gameRoot.getChildren().remove(item.getImageView());
//		gameRoot.getChildren().remove(item.getBox());
//	}

	public void setTime(Double time) {
		this.time = time;
	}

	private boolean checkEnemyCollision(Entity item) {
		int size = enemies.size();
		boolean isHit = false;
		for (int i = 0; i < size;) {
			if (item.getBox().getBoundsInParent().intersects(enemies.get(i).getBox().getBoundsInParent())) {
				gameRoot.getChildren().remove(enemies.get(i).getImageView());
				enemies.remove(enemies.get(i));
				size -= 1;
				isHit = true;
				continue;
			}
			i += 1;
		}
		return isHit;
	}

	private void checkPlayerCollision() {
		int sizeEnemy = enemies.size();
		for (int i = 0; i < sizeEnemy;) {
			Enemy enemy = enemies.get(i);
			if (player.getBox().getBoundsInParent().intersects(enemy.getBox().getBoundsInParent())) {
				gameRoot.getChildren().remove(enemy.getImageView());
				gameRoot.getChildren().remove(enemy.getBox());
				enemies.remove(enemy);

				sizeEnemy -= 1;
				this.player.decreasedHealth(40);
				continue;
			}
			System.out.println("next");
			i++;

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

			playerVelocity = playerVelocity.ZERO;
			playerVelocity = playerVelocity.add(0, -20);
			canJump = false;
//	            System.out.println("jump");
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
		return player.getInventory().get(weaponKey);
	}

}
