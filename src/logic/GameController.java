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

import exception.ConsumeItemFailedException;
import exception.FireBulletFailedException;
import gui.SpriteAnimation;
import implement.Explodable;
import item.bullet.Bomb;
import item.bullet.Bullet;
import item.bullet.GunBullet;
import item.bullet.RocketBullet;
import item.bullet.SwordSlice;
import item.character.GameCharacter;
import item.character.MainCharacter;
import item.consumable.Potion;
import item.consumable.Ammo;
import item.consumable.ConsumableItem;
import item.enemy.BossEnemy;
import item.enemy.ColliderEnemy;
import item.enemy.GunEnemy;
import item.weapon.BombGun;
import item.weapon.Gun;
import item.weapon.RocketGun;
import item.weapon.Sword;
import item.weapon.Weapon;
import item.Entity;
import item.Effect.Barrier;
import item.Effect.Light;
import item.Effect.Warning;
import item.Effect.recoveryLight;
import item.box.AmmoBox;
import item.box.Box;
import item.box.Oak;
import item.box.Portal;
import item.box.PotionBox;
import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	private ArrayList<Bullet> bossBombList = new ArrayList<Bullet>();
	private ArrayList<Entity> timedEntityList = new ArrayList<Entity>();
	private ArrayList<Bullet> enemyBullets = new ArrayList<Bullet>();
	private Rectangle bg;

	private ArrayList<PotionBox> potionList = new ArrayList<PotionBox>();
	private ArrayList<AmmoBox> ammoList = new ArrayList<AmmoBox>();
	private ArrayList<Oak> treeList = new ArrayList<Oak>();

	private int counter = 0;

	private BossEnemy boss;
	private boolean hasBoss = false;
	private boolean isWin = false;
	private ArrayList<GameCharacter> enemieList = new ArrayList<GameCharacter>();

	private int levelWidth;
	private int levelHeight;

	private boolean canJump;

	private boolean alreadyPressedQ = false;
	private boolean alreadyPressedE = false;
	private boolean alreadyPressedW = false;
	private boolean alreadyPressedSPACE = false;
	private boolean alreadyPressedH = false;
	private boolean alreadyPressedJ = false;

	private boolean isEnemyFire = false;

	private int offsetX;
	private int offsetY;

	private boolean isBarrierOpen = false;
	private boolean islight = false;
	private boolean isRecoverylightOpen = false;
	private Light light;
	private recoveryLight recoverylight;
	private Barrier barrier;

	private boolean isBossStart = false;
	private boolean isBossMove = false;
	private int pensioner = 0;
	private boolean isGameEnd = false;

	public GameController() {
		createLevel(1);
		gameRoot.setLayoutX(0);

	}

	private void createLevel(int level) {

		if (gameRoot != null) {
			platforms.clear();
			portalList.clear();
			enemieList.clear();
			potionList.clear();
			ammoList.clear();
			treeList.clear();
			gameRoot.getChildren().clear();
			hasBoss = false;
			isBossStart = false;
		}

		levelD = LevelData.getInstance();
		levelD.loadLevel(level);

		platforms.addAll(levelD.getPlatforms());
		portalList.addAll(levelD.getPortalList());
		enemieList.addAll(levelD.getEnemieList());
		potionList.addAll(levelD.getPotionList());
		ammoList.addAll(levelD.getAmmoList());
		treeList.clear();
		if (levelD.getBoss() != null) {
			boss = levelD.getBoss();
			hasBoss = true;
		} else {
			hasBoss = false;
		}

		gameRoot = levelD.getGameRoot();
		bg = levelD.getBg();
		this.levelHeight = levelD.getLevelHeight();
		this.levelWidth = levelD.getLevelWidth();
		gameRoot.setLayoutX(0);

		createPlayer();

	}

	private void createPlayer() {
		if (player == null) {
			player = new MainCharacter(100, 540);
		}
		player.setX(100);

		gameRoot.getChildren().addAll(player.getBox(), player.getImageView());

		createRocketGun();
		createGun();
		createSword();
		createPotion();
		createAmmo();

	}

	private void createPotion() {
		Potion potion = new Potion(1);
		player.getItemsInventory().add(potion);
	}

	private void createAmmo() {
		Ammo ammo = new Ammo(1);
		player.getItemsInventory().add(ammo);
	}

	private void createGun() {
		Weapon gun = new Gun(20);
		player.getWeaponsInventory().add(gun);
	}

	private void createRocketGun() {
		Weapon rocketGun = new RocketGun(10);
		player.getWeaponsInventory().add(rocketGun);
	}

	private void createSword() {
		Weapon sword = new Sword(1);
		player.getWeaponsInventory().add(sword);
	}

	private void gravity() {
		if (player.getVelocityY() < 10) {
			player.addVelocityY(1);
		}
		if (player.getY() <= levelHeight) {
			movePlayerY(player.getVelocityY());
		}
		for (int i = 0; i < enemieList.size(); i++) {
			GameCharacter enemy = enemieList.get(i);
			if (enemy.getVelocityY() < 10) {
				enemy.addVelocityY(1);
			}
			if (enemy.getY() <= levelHeight) {
				moveEnemyY(enemy, enemy.getVelocityY());
			}
		}

		if (hasBoss) {
			if (boss.getVelocityY() < 10) {
				boss.addVelocityY(1);
			}
			if (boss.getY() <= levelHeight) {
				moveEnemyY(boss, boss.getVelocityY());
			}
			for (int i = 0; i < bossBombList.size(); i++) {
				Bullet bomb = bossBombList.get(i);
				moveEnemyBulletY(bomb, bomb.getVelocityY());
				if (bomb.getVelocityY() < 10) {
					bomb.addVelocityY(1);
				}
			}
		}
	}

	public void getControl() {
		try {
			if (isGameEnd) {
				removeAll();
				return;
			}
//			System.out.println(offsetX);
//			System.out.println(gameRoot.getLayoutX());

			counter += 1;

			gravity();

			if (player.getCurrentHP() <= 0 || player.getY() >= levelHeight) {

//				if (!isDie) {
				if (player.isRight()) {
					player.doDieLeft();
				} else {
					player.doDieRight();
				}
				isGameEnd = true;
				return;
//				}
			} else {

				if ((Math.round(time) % 3) == 0) {
					if (!isEnemyFire) {
						allEnemyFireBullet();
						isEnemyFire = true;
//						randomGunEnemyInRange(300, 200, 0, 0);
					}
				} else {
					isEnemyFire = false;
				}

				if (hasBoss) {
					if (isBossStart) {

						if (isBossMove) {
							moveBossX();
						} else {
							if (counter % 100 == 0) {

								bossFireBullet();

							}
						}

						if (counter % 200 == 0) {
							if (pensioner <= 3) {
								randomGunEnemyInRange(levelWidth - 1100, levelWidth - 240, 540, 540);
								pensioner += 1;
							}
						}
						if ((Math.round(time) % 30) == 0) {
							int randomNumber = (int) Math.round(Math.random() * (3) + 1);
							if (randomNumber == 1) {
								moveBossTo(levelWidth - 1080, 0);
							} else if (randomNumber == 2) {
								moveBossTo(levelWidth - 780, 0);

							} else if (randomNumber == 3) {
								moveBossTo(levelWidth - 480, 0);

							}
						}
					}

				}

				if (isPressed(KeyCode.ENTER)) {
					if (enemieList.size() == 0) {
						checkPortal();
					}

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

				if (isPressed(KeyCode.H)) {
					if (!alreadyPressedH) {
						if (player.getMaxHP() != player.getCurrentHP()) {
							try {
								ConsumableItem item = player.getItemsInventory().get(0);
								item.consumed(player);
								recoverylight = new recoveryLight(offsetX, offsetY);
								timedEntity(recoverylight, 1.5);
								isRecoverylightOpen = true;
							} catch (ConsumeItemFailedException e) {
								System.out.println("Use potion failed, " + e.message);
							}
							alreadyPressedH = true;
						}
					}
				} else {
					alreadyPressedH = false;
				}

				if (isPressed(KeyCode.H)) {
					if (!alreadyPressedH) {
						if (player.getMaxHP() != player.getCurrentHP()) {
							try {
								ConsumableItem item = player.getItemsInventory().get(0);
								item.consumed(player);
								recoverylight = new recoveryLight(offsetX, offsetY);
								timedEntity(recoverylight, 1.5);
								isRecoverylightOpen = true;
							} catch (ConsumeItemFailedException e) {
								System.out.println("Use potion failed, " + e.message);
							}
							alreadyPressedH = true;
						}
					}
				} else {
					alreadyPressedH = false;
				}

				if (isPressed(KeyCode.J)) {
					if (!alreadyPressedJ) {

						try {
							ConsumableItem item = player.getItemsInventory().get(1);
							item.consumed(player);
							light = new Light(offsetX, offsetY);
							timedEntity(light, 1.5);
							islight = true;
						} catch (ConsumeItemFailedException e) {
							System.out.println("Use ammo failed, " + e.message);
						}
						alreadyPressedJ = true;

					}
				} else {
					alreadyPressedJ = false;
				}

				// follow player x

				if (offsetX > 640 && offsetX < levelWidth - 640) {
					gameRoot.setLayoutX(-(offsetX - 640));
				}
				if (offsetX > levelWidth - 1280) {
					if (hasBoss) {
						gameRoot.setLayoutX(-(levelWidth - 1280));
						isBossStart = true;
					}

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
		checkTime();
		if (isBarrierOpen) {
			barrier.setX(offsetX);
			barrier.setY(offsetY);
			if (counter % 5 == 0) {
				player.blink();

			}
		}
		if (isRecoverylightOpen) {
			recoverylight.setX(offsetX);
			recoverylight.setY(offsetY);
		}
		if (islight) {
			light.setX(offsetX);
			light.setY(offsetY);
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

	private void allEnemyFireBullet() {
		for (int i = 0; i < enemieList.size(); i++) {
			GameCharacter enemy = enemieList.get(i);
			int distance = enemy.getX() - player.getX();
			if (enemy instanceof ColliderEnemy) {
				continue;
			}
			if (Math.abs(distance) < 450) {
				enemyFireMethod(enemy);
			}

		}
	}

	private void bossFireBullet() {

		int velocityX = (int) Math.round(Math.random() * (2)) + 1;
		enemyFireMethod(boss).setVelocityX(velocityX);

	}

	private Bullet enemyFireMethod(GameCharacter enemyCharacter) {

		int distance = enemyCharacter.getX() - player.getX();
		boolean isRight = distance < 0;
		boolean isBeforeRight = enemyCharacter.isRight();

		Bullet bullet = enemyCharacter.getWeapon().fireBulletInfinite(enemyCharacter, isRight);

		if (enemyCharacter instanceof GunEnemy) {
			enemyBullets.add(bullet);
			gameRoot.getChildren().addAll(bullet.getImageView());
		}
		if (enemyCharacter instanceof BossEnemy) {
			bossBombList.add(bullet);
			gameRoot.getChildren().addAll(bullet.getImageView());
			return bullet;
		}

		if (distance < 0) {
			enemyCharacter.doFireRight();
		} else {
			enemyCharacter.doFireLeft();
		}

		if (isBeforeRight) {
			enemyCharacter.doWalkRight();
		} else {
			enemyCharacter.doWalkLeft();
		}
		return bullet;

	}

	private void checkPortal() {
		int size = portalList.size();
		for (int i = 0; i < size;) {
			Portal portal = portalList.get(i);
			if (player.getBox().getBoundsInParent().intersects(portal.getBox().getBoundsInParent())) {
				createLevel(portal.getLevel());
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

	private void moveBullets() {
		for (int i = 0; i < playerBullets.size(); i++) {
			Bullet bullet = playerBullets.get(i);
			if (playerBullets.get(i).isRight()) {
				movePlayerBulletX(playerBullets.get(i), bullet.getVelocityX());
			} else {
				movePlayerBulletX(playerBullets.get(i), -bullet.getVelocityX());
			}
		}
		for (int i = 0; i < enemyBullets.size(); i++) {
			Bullet bullet = enemyBullets.get(i);
			if (bullet.isRight()) {
				moveEnemybulletX(bullet, bullet.getVelocityX());
			} else {
				moveEnemybulletX(bullet, -bullet.getVelocityX());
			}
		}

		if (hasBoss) {
			for (int i = 0; i < bossBombList.size(); i++) {
				Bullet bossBomb = bossBombList.get(i);
				if (bossBomb.isRight()) {
					moveEnemybulletX(bossBomb, bossBomb.getVelocityX());
				} else {
					moveEnemybulletX(bossBomb, -bossBomb.getVelocityX());
				}
			}
		}

	}

	private void moveEnemies() {
		for (int i = 0; i < enemieList.size(); i++) {
			GameCharacter enemy = enemieList.get(i);
			if (enemy.isRight()) {
				moveEnemyX(enemy, enemy.getVelocityX());
			} else {
				moveEnemyX(enemy, -enemy.getVelocityX());
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

	private void movePlayerBulletX(Entity item, int value) {
		boolean movingRight = value > 0;
		value = Math.abs(value);

		for (Entity platform : platforms) {
			boolean isOutOfRange = Math.abs(item.getX() - item.getInitX()) > item.getDisX();
			boolean isCollisionPlatform = item.getBox().getBoundsInParent()
					.intersects(platform.getBox().getBoundsInParent());

			if (checkBossCollision(item) || checkEnemyCollision(item) || isOutOfRange || isCollisionPlatform) {
				gameRoot.getChildren().remove(item.getImageView());
				gameRoot.getChildren().remove(item.getBox());
				playerBullets.remove(item);

				if (item instanceof Explodable) {
					((Explodable) item).explode();
					checkEnemyCollision(item);
					timedEntity(item, 1.0);
					return;
				}
				if (item instanceof SwordSlice) {
					checkEnemyCollision(item);

					timedEntity(item, 0.2);
					return;
				}

				item = null;
				return;
			}
		}

		item.setX(item.getX() + (movingRight ? 5 * value : -5 * value));
	}

	private void moveEnemybulletX(Entity item, int value) {
		boolean movingRight = value > 0;
		value = Math.abs(value);

		for (int i = 0; i < Math.abs(value); i++) {
			boolean isOutOfRange = Math.abs(item.getX() - item.getInitX()) > item.getDisX();
			for (Entity platform : platforms) {

				boolean isCollisionPlatform = item.getBox().getBoundsInParent()
						.intersects(platform.getBox().getBoundsInParent());

				if (isOutOfRange || isCollisionPlatform) {
					gameRoot.getChildren().remove(item.getImageView());
					gameRoot.getChildren().remove(item.getBox());
					playerBullets.remove(item);
					enemyBullets.remove(item);

					if (item instanceof RocketBullet) {
						((RocketBullet) item).explode();
						checkEnemyCollision(item);
						timedEntity(item, 1.0);
						playerBullets.remove(item);
						return;
					}
					if (item instanceof SwordSlice) {
						checkEnemyCollision(item);
						timedEntity(item, 0.2);
						playerBullets.remove(item);
						return;
					}
					if (item instanceof Bomb && bossBombList.contains(item)) {
						System.out.println("x");
						((Bomb) item).explode();
						timedEntity(item, 1.0);
						bossBombList.remove(item);

						return;
					}

					item = null;
					return;
				}
			}

		}
		item.setX(item.getX() + (movingRight ? 5 * value : -5 * value));
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

	private void moveBossX() {

		boolean movingRight = boss.getFinalPositionX() - boss.getX() > 0;
		int value = boss.getVelocityX();
//		boss.setVelocityY(-40);

		for (int i = 0; i < Math.abs(value); i++) {

//			if (enemy.checkTurn(platforms)) {
//				if (enemy.isRight()) {
//					enemy.doWalkLeft();
//				} else {
//					enemy.doWalkRight();
//				}
//			}
			boss.setX(boss.getX() + (movingRight ? value : -value));
		}
		if (boss.isOnFloor() && boss.getFinalPositionX() == boss.getX()) {
			isBossMove = false;
			return;
		}

	}

	private void moveBossTo(int positionX, int positionY) {
		isBossMove = true;
		boolean movingRight = positionX - boss.getX() > 0;
		int value = boss.getVelocityX();
		boss.setFinalPositionX(positionX);
		boss.setVelocityY(-30);

		for (int i = 0; i < Math.abs(value); i++) {

//			if (enemy.checkTurn(platforms)) {
//				if (enemy.isRight()) {
//					enemy.doWalkLeft();
//				} else {
//					enemy.doWalkRight();
//				}
//			}
			boss.setX(boss.getX() + (movingRight ? value : -value));
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

	private void jumpPlayer() {
		if (canJump) {
			player.setVelocityY(-20);
			canJump = false;
		}
	}

	private void moveEnemyY(GameCharacter item, int value) {
		boolean movingDown = value > 0;

		for (int i = 0; i < Math.abs(value); i++) {
			for (Entity platform : platforms) {
				if (item.getBox().getBoundsInParent().intersects(platform.getBox().getBoundsInParent())) {
					if (movingDown) {
						if (item.getY() + item.getHeight() == platform.getY()) {
							item.setY(item.getY() - 1);
							item.setOnFloor(true);

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

	private void moveEnemyBulletY(Entity item, int value) {
		boolean movingDown = value > 0;

		for (int i = 0; i < Math.abs(value); i++) {
			for (Entity platform : platforms) {
				if (item.getBox().getBoundsInParent().intersects(platform.getBox().getBoundsInParent())) {
					gameRoot.getChildren().remove(item.getImageView());
					if (item instanceof Bomb) {
						System.out.println("Y");
						((Bomb) item).explode();
						checkPlayerCollision();
						timedEntity(item, 1.0);
						bossBombList.remove(item);
						return;
					}
					if (movingDown) {
						if (item.getY() + item.getHeight() == platform.getY()) {
//							item.setY(item.getY() - 1);
							return;
						}
					} else {
						if (item.getY() == platform.getY() + 60) {
//							item.setY(item.getY() + 1);
							return;
						}
					}
				}
			}
			item.setY(item.getY() + (movingDown ? 1 : -1));
		}
	}

	private boolean checkEnemyCollision(Entity item) {
		boolean isHit = false;
		for (int i = 0; i < enemieList.size();) {
			GameCharacter enemy = enemieList.get(i);
			if (item.getBox().getBoundsInParent().intersects(enemy.getBox().getBoundsInParent())) {

				if (item instanceof Bullet) {
					enemy.decreasedCurrentHP(((Bullet) item).getDamage());
				}
				if (enemy.isDie()) {
					characterDie(enemy);
					i -= 1;
				}
				isHit = true;
			}
			i += 1;
		}
		return isHit;
	}

	private boolean checkBossCollision(Entity item) {
		boolean isHit = false;
		if (!hasBoss) {
			return false;
		}

		if (item.getBox().getBoundsInParent().intersects(boss.getBox().getBoundsInParent())) {

			if (item instanceof Bullet) {
				boss.decreasedCurrentHP(((Bullet) item).getDamage());
			}
			if (boss.isDie()) {
				characterDie(boss);
			}

			isHit = true;
		}

		return isHit;
	}

	private void checkPlayerCollision() {
		if (isBarrierOpen) {
			return;
		}
		if (checkEnemyCollision(player)) {
			player.decreasedCurrentHP(30);
			createBarrier();
		}
		if (checkBossCollision(player)) {
			player.decreasedCurrentHP(50);
			createBarrier();
		}

		for (int i = 0; i < enemyBullets.size();) {
			Bullet bullet = enemyBullets.get(i);
			if (player.getBox().getBoundsInParent().intersects(bullet.getBox().getBoundsInParent())) {
				player.decreasedCurrentHP(bullet.getDamage());
				enemyBullets.remove(bullet);
				removeEntity(bullet);
				continue;
			}
			i++;

		}

		for (int i = 0; i < bossBombList.size();) {
			Bullet bullet = bossBombList.get(i);
			if (player.getBox().getBoundsInParent().intersects(bullet.getBox().getBoundsInParent())) {
				player.decreasedCurrentHP(bullet.getDamage());
				gameRoot.getChildren().remove(bullet.getImageView());
				((Bomb) bullet).explode();
				timedEntity(bullet, 1.0);
				bossBombList.remove(bullet);

				continue;
			}
			i++;
		}
		for (int i = 0; i < ammoList.size();) {
			AmmoBox ammo = ammoList.get(i);
			if (player.getBox().getBoundsInParent().intersects(ammo.getBox().getBoundsInParent())) {
				gameRoot.getChildren().remove(ammo.getImageView());
				player.getItemsInventory().get(1).addAmount(1);
				ammoList.remove(ammo);

				continue;
			}
			i++;
		}

		for (int i = 0; i < potionList.size();) {
			PotionBox potionBox = potionList.get(i);
			if (player.getBox().getBoundsInParent().intersects(potionBox.getBox().getBoundsInParent())) {
				gameRoot.getChildren().remove(potionBox.getImageView());
				player.getItemsInventory().get(0).addAmount(1);
				potionList.remove(potionBox);

				continue;
			}
			i++;
		}

	}

	private void characterDie(GameCharacter Character) {
		GameCharacter enemy = (GameCharacter) Character;
		gameRoot.getChildren().remove(enemy.getImageView());
		timedEntity(enemy, 1.5);

		enemieList.remove(enemy);
		if (Character instanceof BossEnemy) {
			isWin = true;
			hasBoss = false;
			isBossStart = false;
			isGameEnd = true;
		}

		player.addPoint(enemy.getPoint());

		if (enemy.isRight()) {
			enemy.doDieRight();
		} else {
			enemy.doDieLeft();
		}
		if (enemy.isPensioner()) {
			pensioner -= 1;
		}

	}

	private void timedEntity(Entity entity, Double duration) {
		timedEntityList.add(entity);
		entity.setFinalTime(time + duration);
		gameRoot.getChildren().add(entity.getImageView());
//		gameRoot.getChildren().add(entity.getBox());
	}

	private void checkTime() {
		for (int i = 0; i < timedEntityList.size();) {

			if (timedEntityList.get(i).getFinalTime() <= time) {
				Entity item = timedEntityList.get(i);

				if (item instanceof Barrier) {
					isBarrierOpen = false;
					player.setOpacityNormal();
				}
				if (item instanceof recoveryLight) {
					isRecoverylightOpen = false;
				}

				if (item instanceof Warning) {
					GunEnemy enemy = new GunEnemy(item.getX(), item.getY());
					enemy.setPensioner(true);
					enemy.doWalkLeft();
					enemieList.add(enemy);
					gameRoot.getChildren().add(enemy.getImageView());
				}
				timedEntityList.remove(item);
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
		Warning warning = new Warning(positionX, positionY);
		timedEntity(warning, 1.0);

	}

	private void removeEntity(Entity item) {
		gameRoot.getChildren().remove(item.getImageView());
		item = null;
	}

	private void removeAll() {
		platforms.clear();
		portalList.clear();
		enemieList.clear();
		potionList.clear();
		ammoList.clear();
		treeList.clear();
		gameRoot.getChildren().clear();
		platforms = null;
		portalList = null;
		enemieList = null;
		potionList = null;
		ammoList = null;
		treeList = null;
		hasBoss = false;
		isBossStart = false;
		boss = null;
		light = null;
		recoverylight = null;
		barrier = null;

		levelWidth = 0;
		levelHeight = 0;
		System.gc();
	}

	private void createBarrier() {
		barrier = new Barrier(offsetX, offsetY);
		timedEntity(barrier, 1.5);
		isBarrierOpen = true;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	public MainCharacter getPlayer() {
		return player;
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

	public int getPlayerPoint() {
		return player.getPoint();
	}

	public ArrayList<ConsumableItem> getPlayerInventory() {
		return player.getItemsInventory();
	}

	public boolean isGameEnd() {
		return isGameEnd;
	}

	public boolean isWin() {
		return isWin;
	}

	public void setWin(boolean isWin) {
		this.isWin = isWin;
	}

	public void setGameEnd(boolean isGameEnd) {
		this.isGameEnd = isGameEnd;
	}

}
