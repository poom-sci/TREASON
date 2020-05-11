package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import item.Entity;
import item.box.AmmoBox;
import item.box.Box;
import item.box.Oak;
import item.box.Portal;
import item.box.PotionBox;
import item.character.GameCharacter;
import item.enemy.BossEnemy;
import item.enemy.ColliderEnemy;
import item.enemy.GunEnemy;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class LevelData {
	private static final String[] LEVEL1 = new String[] {
			"10000000000000000000000000000000000000000000000000000000000000000000000001",
			"1000000000000000000000000000000000000000000000000000000000000HC00000000001",
			"10000000000000000000000000000000000000000000000000000000000011100G00000001",
			"10000000000000000000000000000000000000000000000000000000000000000111100001",
			"100000000000000000000000000000000000000000000GH000000000000000000000000001",
			"10000000000000000000000000000000000000000000111000001000000000000000000P01",
			"1000AG0000000000000000000000000000000000000000000000100000G0000G0000000111",
			"100011100G000000000000000000000000G000111100000000001000011100111001110111",
			"100000000111000G0000001001000000111100000000000000011000000000000000000111",
			"1000000G0000001110000110011000000000000000000000001111000G00000G0000000111",
			"1000001110000000000011100111000C000G00C000000G0001111111111111111111111111",
			"11111111100111100011111001111111111111111111111111111111111111111111111111" };

	private static final String[] LEVEL2 = new String[] { "100000000000000000000000000000000000000000000000000001",
			"100000000000000000000000000000000000000000000000000001",
			"10000000000000C0000GC000HA0000000000000000000000000001",
			"100000000000011000111100110000000000000000000000000001",
			"100000000110000000000000000000000000000000000000000001",
			"100000000000000000000000000000000000000000000000000P01",
			"1000000G000G00000000000000000000000000000000000G000111",
			"1000001111111000001110001100000010000000G0000011100111",
			"100011111000000000000000010000011000000111100000000111",
			"1001111110000011100000000110G0111001000000000000000111",
			"101111111000000000000000011111111111111111111111111111",
			"111111111001111000111110011111111111111111111111111111" };

	private static final String[] LEVEL3 = new String[] { "1000000000000000000000000000000000001",
			"1000000000000000000000000000000000001", "1000000000000000000000000000000000001",
			"1000000000000010000000000000000000B01", "1000000000000010000000000000000000001",
			"1000000000000011000000000000000000001", "1000000000011111000000000000000000111",
			"1000000000100011000000000000000000111", "1000000001000011000111001110011100111",
			"1000000010000011000000000000000000111", "1000000100000011100000000000000000111",
			"1111111111111111111111111111111111111" };

	public static final String[][] ALLLEVELMAP = new String[][] { LEVEL1, LEVEL2, LEVEL3 };

	public static LevelData levelD;

	private int Level;
	private Pane gameRoot;
	private ArrayList<Entity> platforms;
	private ArrayList<Portal> portalList;
	private BossEnemy boss;
	private ArrayList<GameCharacter> enemieList;
	private ArrayList<Entity> playerInteractEntity ;
	private Rectangle bg;

	private int levelWidth;
	private int levelHeight;

	private LevelData() {
		Level = 1;

		platforms = new ArrayList<Entity>();
		portalList = new ArrayList<Portal>();
		enemieList = new ArrayList<GameCharacter>();
		playerInteractEntity = new ArrayList<Entity>();
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
		enemieList.clear();
		playerInteractEntity.clear();
		gameRoot.getChildren().clear();
		boss=null;

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
					gunEnemy.doWalkLeft();
					enemieList.add(gunEnemy);
					gameRoot.getChildren().add(gunEnemy.getImageView());

					break;
				case 'C':
					GameCharacter colliderEnemy = new ColliderEnemy(j * 60, i * 60);
					colliderEnemy.doWalkLeft();
					enemieList.add(colliderEnemy);
					gameRoot.getChildren().add(colliderEnemy.getImageView());

					break;
				case 'P':
					Portal portal = new Portal(j * 60, i * 60, 60, 60);
					portalList.add(portal);
					gameRoot.getChildren().add(portal.getImageView());
					if (level == 0) {
						portal.setLevel(2);
					} else if (level == 1) {
						portal.setLevel(3);
					}
					break;
				case 'B':
					boss = new BossEnemy(j * 60, i * 60);
//					gameRoot.getChildren().addAll(boss.getBox());
					gameRoot.getChildren().add(boss.getImageView());

					Rectangle box = new Rectangle(j * 60 - 1550, 180, 280, 70);
					box.setOpacity(0.4);
					box.setFill(Color.ANTIQUEWHITE);
					Label bossStage = new Label("boss Stage");

					try {
//						bulletLeft.setTextFill(Color.web("EA8F3C"));
						bossStage.setFont(Font.loadFont(new FileInputStream("res/PixelTakhisis-ZajJ.ttf"), 23));
						bossStage.setTranslateX(j * 60 - 1500);
						bossStage.setTranslateY(200);

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					gameRoot.getChildren().addAll(box, bossStage);

					break;

				case 'H':
					PotionBox potionBox = new PotionBox(j * 60, i * 60, 60, 60);
					playerInteractEntity.add(potionBox);
					gameRoot.getChildren().add(potionBox.getImageView());
					break;
				case 'A':
					AmmoBox ammoBox = new AmmoBox(j * 60, i * 60, 60, 60);
					playerInteractEntity.add(ammoBox);
					gameRoot.getChildren().add(ammoBox.getImageView());
					break;
				case 'T':
					Oak tree = new Oak(j * 60, i * 60, 310, 240);
					playerInteractEntity.add(tree);
					gameRoot.getChildren().add(tree.getImageView());
					break;
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

	public BossEnemy getBoss() {
		return boss;
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

	public ArrayList<Entity> getPlayerInteractEntity() {
		return playerInteractEntity;
	}
	
	

}