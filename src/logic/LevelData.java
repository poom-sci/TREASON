package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import item.Entity;
import item.box.Box;
import item.box.Portal;
import item.character.GameCharacter;
import item.enemy.BossEnemy;
import item.enemy.ColliderEnemy;
import item.enemy.GunEnemy;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class LevelData {
	private static final String[] LEVEL1 = new String[] {
			"10000000000000000000000000000000000000000000000000000000000000000000000001",
			"10000000000000000000000000000000000000000000000000000000000000000000000001",
			"10000000000000000000000000000000000000000000000000000000000000000000000001",
			"10000000000000000000000000000000000000000000000000000000000000000000000001",
			"1000000000B000000000000000000000000000000000000000000000000000000000000001",
			"10000000000000000000000000000000000000000000111000001000000000000000000001",
			"10000G00000000000000000000000000000000000000000000001000000000000000000111",
			"100011100G0000000000000000000000000000111100000000001000011100111001110111",
			"10000000011100020000001001000000111100000000000000011000000000000000000111",
			"1000000G000000111000011001100000000000000000000000111100000000000000000111",
			"10000011100002000000111001110000000000000000000001111111111111111111111111",
			"11111111100111100011111001111111111111111111111111111111111111111111111111" };

	private static final String[] LEVEL2 = new String[] { 
			"100000000000000000000000000000000000000000000000000001",
			"100000000000000000000000000000000000000000000000000001",
			"100000000000000000000000000000000000000000000000000001",
			"100000000000000000000000000000000000000000000000000001",
			"100000000000000000000000000000000000000000000000000001",
			"100000000000000000000000000000000000000000000000000001",
			"100000000000000000000000000000000000000000000000000111",
			"100000111111111000111000111000001000000000000000000111",
			"100011111000000000000000010000011000000000000000000111",
			"100111111000001110000000011000111001000000000000000111",
			"101111111000000000000000011101111111111111111111111111",
			"111111111001111000111110011111111111111111111111111111" };

	public static final String[][] ALLLEVELMAP = new String[][] { LEVEL1, LEVEL2 };

	public static LevelData levelD;

	private int Level;
	private Pane gameRoot;
	private ArrayList<Entity> platforms;
	private ArrayList<Portal> portalList;
	private ArrayList<BossEnemy> bossList;
	private ArrayList<GameCharacter> enemieList;
	private Rectangle bg;

	private int levelWidth;
	private int levelHeight;

	private LevelData() {
		Level = 1;

		platforms = new ArrayList<Entity>();
		portalList = new ArrayList<Portal>();
		bossList = new ArrayList<BossEnemy>();
		enemieList = new ArrayList<GameCharacter>();
		gameRoot = new Pane();
	}

	public static LevelData getInstance() {
		if (levelD == null) {
			levelD = new LevelData();
		}
		return levelD;
	}

	public void loadLevel(int level) {

		level -= 1;
		Level = level;

		platforms.clear();
		portalList.clear();
		bossList.clear();
		enemieList.clear();
		gameRoot.getChildren().clear();

		bg = new Rectangle(1280, 720);
		bg.setFill(new ImagePattern(new Image("game_background.jpg", 1280, 720, false, true)));

		this.levelWidth = LevelData.ALLLEVELMAP[level][0].length() * 60;
		this.levelHeight = LevelData.ALLLEVELMAP[level].length * 60;

		for (int i = 0; i < LevelData.ALLLEVELMAP[level].length; i++) {
			String line = LevelData.ALLLEVELMAP[level][i];
			for (int j = 0; j < line.length(); j++) {
				switch (line.charAt(j)) {
				case '0':
					break;
				case '1':
					Box platform = new Box(j * 60, i * 60, 60, 60);
					platforms.add(platform);
					gameRoot.getChildren().add(platform.getImageView());
					break;
				case 'G':
					GameCharacter gunEnemy = new GunEnemy(j * 60, i * 60);
					gunEnemy.doTurnLeft();
					enemieList.add(gunEnemy);
					gameRoot.getChildren().add(gunEnemy.getImageView());

					break;
				case 'C':
					GameCharacter colliderEnemy = new ColliderEnemy(j * 60, i * 60);
					colliderEnemy.doTurnLeft();
					enemieList.add(colliderEnemy);
					gameRoot.getChildren().add(colliderEnemy.getImageView());

					break;
				case '3':
					Portal door = new Portal(j * 60, i * 60, 60, 60);
					portalList.add(door);
					gameRoot.getChildren().add(door.getImageView());
					break;
//				case 'B':
//					BossEnemy boss = new BossEnemy(j * 60, i * 60);
//					boss.setX(j * 60);
//					boss.setY(i * 60);
//					bossList.add(boss);
//					gameRoot.getChildren().addAll(boss.getBox());
//					gameRoot.getChildren().add(boss.getImageView());
//
//					break;

				}
			}
		}
	}

	public Rectangle getBg() {
		return bg;
	}

	public int getLevel() {
		return Level;
	}

	public Pane getGameRoot() {
		return gameRoot;
	}

	public ArrayList<Entity> getPlatforms() {
		return platforms;
	}

	public ArrayList<Portal> getPortalList() {
		return portalList;
	}

	public ArrayList<BossEnemy> getBossList() {
		return bossList;
	}

	public ArrayList<GameCharacter> getEnemieList() {
		return enemieList;
	}

	public int getLevelWidth() {
		return levelWidth;
	}

	public int getLevelHeight() {
		return levelHeight;
	}

}