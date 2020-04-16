package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import javax.swing.text.StyledEditorKit.BoldAction;

import item.bullet.Bullet;
import item.bullet.DelayBullet;
import item.bullet.GunBullet;
import item.bullet.RocketBullet;
import item.character.MainCharacter;
import item.enemy.Enemy;
import item.weapon.Gun;
import item.weapon.RocketGun;
import item.weapon.Sword;
import item.weapon.Weapon;
import item.Entity;
import item.box.Box;
import item.box.Door;
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
	private ArrayList<Door> portals = new ArrayList<Door>();
	private Rectangle bg;
	private ArrayList<Bullet> playerBullets = new ArrayList<Bullet>();
	private ArrayList<DelayBullet> delaylist = new ArrayList<DelayBullet>();

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
				
				checkDelay();
				
				if (isPressed(KeyCode.ENTER)) {
					checkPortal();
				}

				offsetX = player.getX();
				offsetY = player.getY();

				moveplayerBullets();
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
				if (offsetX > levelWidth - 1000) {
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

		player = new MainCharacter();
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
					Box platform = new Box(60, 60);
					platform.setX(j * platform.getWidth());
					platform.setY(i * platform.getHeight());
					platforms.add(platform);
					gameRoot.getChildren().add(platform.getImageView());
					break;
				case '2':
					Enemy enemy = new Enemy();
					enemies.add(enemy);
					gameRoot.getChildren().add(enemy.getImageView());
					enemy.setX(j * 60);
					enemy.setY(i * 60);
					break;
				case '3':
					Door door = new Door(60, 60);
					portals.add(door);
					gameRoot.getChildren().add(door.getImageView());
					door.setX(j * 60);
					door.setY(i * 60);
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
					Box platform = new Box(60, 60);
					platform.setX(j * platform.getWidth());
					platform.setY(i * platform.getHeight());
					platforms.add(platform);
					gameRoot.getChildren().add(platform.getImageView());
					break;
				case '2':
					Enemy enemy = new Enemy();
					enemies.add(enemy);
					gameRoot.getChildren().add(enemy.getImageView());
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

	private void moveplayerBullets() {
		if (!playerBullets.isEmpty()) {
			for (int i = 0; i < playerBullets.size(); i++) {

				if (playerBullets.get(i).isRight()) {
					moveItemX(playerBullets.get(i), 2);
				} else {
					moveItemX(playerBullets.get(i), -2);
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
	
	private void delayImage(Bullet item,int duration) {
		double finalTime=time+duration;
		DelayBullet bullet=new DelayBullet(finalTime, duration,item);
		System.out.println(finalTime);
		delaylist.add(bullet);
	}

	private void checkDelay() {
		if (delaylist.size() == 0) {
			return;
		}
		int size = delaylist.size();
		for (int i = 0; i < size;i++) {
			if (delaylist.get(i).getFinalTime()>=time) {
				Bullet item=delaylist.get(i).getBullet();
				gameRoot.getChildren().remove(item.getImageView());
				gameRoot.getChildren().remove(item.getBox());
				playerBullets.remove(item);
				delaylist.remove(i);
				System.out.println("remove");
			}


		}
	}
	
	private void moveItemX(Bullet item, int value) {
		boolean movingRight = value > 0;

		for (int i = 0; i < Math.abs(value); i++) {
			if (!playerBullets.contains(item)) {
				break;
			}

			for (Enemy enemy : enemies) {
				if (item.getBox().getBoundsInParent().intersects(enemy.getBox().getBoundsInParent())) {

					if (item instanceof RocketBullet) {
						((RocketBullet) item).explode();
						checkEnemyCollision(item);
						delayImage(item,2);
						return;
					}
					checkEnemyCollision(item);

					gameRoot.getChildren().remove(item.getImageView());
					gameRoot.getChildren().remove(item.getBox());
					playerBullets.remove(item);

					return;
				}
			}

			for (Entity platform : platforms) {
				if (Math.abs(item.getX() - item.getInitX()) > item.getDisX()) {

					if (item instanceof RocketBullet) {

						((RocketBullet) item).explode();
						checkEnemyCollision(item);
					}

					gameRoot.getChildren().remove(item.getImageView());
					gameRoot.getChildren().remove(item.getBox());
					playerBullets.remove(item);
					return;

				}
				if (item.getBox().getBoundsInParent().intersects(platform.getBox().getBoundsInParent())) {
					System.out.println("crash");
					if (item instanceof RocketBullet) {

						((RocketBullet) item).explode();
						checkEnemyCollision(item);
					}

					gameRoot.getChildren().remove(item.getImageView());
					gameRoot.getChildren().remove(item.getBox());
					playerBullets.remove(item);
					return;
				}
			}

		}
		item.setX(item.getX() + (movingRight ? 10 : -10));
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

	private void checkEnemyCollision(Bullet item) {
		if (enemies.size() == 0) {
			return;
		}
		int size = enemies.size();

		int index = 0;
		for (int i = 0; index < size; i++) {

			if (item.getBox().getBoundsInParent().intersects(enemies.get(index).getBox().getBoundsInParent())) {

				gameRoot.getChildren().remove(enemies.get(index).getImageView());
				enemies.remove(enemies.get(index));
				size -= 1;
				continue;
			}

			index += 1;

		}

	}

	private void checkPlayerCollision() {
		if (enemies.size() == 0) {
			return;
		}
		int sizeEnemy = enemies.size();
		System.out.println(sizeEnemy + "size");
		int index1 = 0;
		for (int i = 0; index1 < sizeEnemy; i++) {
			System.out.println(index1);
			if (player.getBox().getBoundsInParent().intersects(enemies.get(index1).getBox().getBoundsInParent())) {
				System.out.println(index1 + "die");
				gameRoot.getChildren().remove(enemies.get(index1).getImageView());
				enemies.remove(enemies.get(index1));
				sizeEnemy -= 1;
				this.player.decreasedHealth(40);
				continue;
			}
			System.out.println("next");
			index1 += 1;

		}
		try {
			if (enemyBullets.size() < 1)
				return;
			int size = enemyBullets.size();
			System.out.println(size + "size");
			int index2 = 0;
			for (int i = 0; index2 < size; i++) {
				System.out.println(index2);
				if (player.getBox().getBoundsInParent()
						.intersects(enemyBullets.get(index2).getBox().getBoundsInParent())) {
					System.out.println("----");
					gameRoot.getChildren().remove(enemyBullets.get(index2).getImageView());
					enemyBullets.remove(enemyBullets.get(index2));
					size -= 1;
					this.player.decreasedHealth(40);
					continue;
				}
				System.out.println("next");
				index2 += 1;

			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
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
