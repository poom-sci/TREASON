package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import item.Entity;
import item.box.AmmoBox;
import item.box.Grass0;
import item.box.Grass1;
import item.box.Grass2;
import item.box.Grass3;
import item.box.Grass4;
import item.box.Grass5;
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
			"20000000000000000000000000000000000000000000000000000000000000000000000003",
			"2000000000000000000000000000000000000000000000000000000000000HC00000000003",
			"200000000000000000000000000000000000000000000000000000000000a8b00G00000003",
			"20000000000000000000000000000000000000000000000000000000000000000a88b00003",
			"200000000000000000000000000000000000000000000GH000000000000000000000000003",
			"20000000000000000000000000000000000000000000a8b00000c000000000000000000P03",
			"2000AG0000000000000000000000000000000000000000000000900000G0000G0000000416",
			"2000a8b00G000000000000000000000000G000a88b000000000090000a8b00a8b00a8b03xx",
			"200000000a8b000G000000c00c000000a88b000000000000000420000000000000000003xx",
			"2000000G000000a8b0000420035000000000000000000000004675000G00000G00000003xx",
			"2000004150000000000046200375000C000G00C000000G00046xx7111111111111111116xx",
			"7111116x2004115000416x2003x71111111111111111111116xxxxxxxxxxxxxxxxxxxxxxxx" };

	private static final String[] LEVEL2 = new String[] { "100000000000000000000000000000000000000000000000000001",
			"200000000000000000000000000000000000000000000000000001",
			"20000000000000C0000GC000HA0000000000000000000000000001",
			"200000000000011000111100110000000000000000000000000001",
			"200000000110000000000000000000000000000000000000000001",
			"200000000000000000000000000000000000000000000000000P01",
			"2000000G000G00000000000000000000000000000000000G000111",
			"2000001111111000001110001100000010000000G0000011100111",
			"200011111000000000000000010000011000000111100000000111",
			"2001111110000011100000000110G0111001000000000000000111",
			"201111111000000000000000011111111111111111111111111111",
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
	private ArrayList<PotionBox> potionList;
	private ArrayList<AmmoBox> ammoList;
	private ArrayList<Oak> treeList;
	private Rectangle bg;

	private int levelWidth;
	private int levelHeight;

	private LevelData() {
		Level = 1;

		platforms = new ArrayList<Entity>();
		portalList = new ArrayList<Portal>();
		enemieList = new ArrayList<GameCharacter>();
		potionList = new ArrayList<PotionBox>();
		ammoList = new ArrayList<AmmoBox>();
		treeList = new ArrayList<Oak>();
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
		potionList.clear();
		ammoList.clear();
		treeList.clear();
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
				case 'x':
					Grass0 grass0 = new Grass0(j * 60, i * 60, 60, 60);
					platforms.add(grass0);
					gameRoot.getChildren().add(grass0.getImageView());
					break;
				case '1':
					Grass1 grass1 = new Grass1(j * 60, i * 60, 60, 60);
					platforms.add(grass1);
					gameRoot.getChildren().add(grass1.getImageView());
					break;
				case '2':
					Grass1 grass12 = new Grass1(j * 60, i * 60, 60, 60);
					grass12.getImageView().setRotate(grass12.getImageView().getRotate()+90);
					platforms.add(grass12);
					gameRoot.getChildren().add(grass12.getImageView());
					break;
				case '3':
					Grass1 grass13 = new Grass1(j * 60, i * 60, 60, 60);
					grass13.getImageView().setRotate(grass13.getImageView().getRotate()+270);
					platforms.add(grass13);
					gameRoot.getChildren().add(grass13.getImageView());
					break;
				case '4':
					Grass2 grass2 = new Grass2(j * 60, i * 60, 60, 60);
					platforms.add(grass2);
					gameRoot.getChildren().add(grass2.getImageView());
					break;
				case '5':
					Grass2 grass21 = new Grass2(j * 60, i * 60, 60, 60);
					grass21.getImageView().setScaleX(-1);
					platforms.add(grass21);
					gameRoot.getChildren().add(grass21.getImageView());
					break;
				case '6':
					Grass3 grass3 = new Grass3(j * 60, i * 60, 60, 60);
					platforms.add(grass3);
					gameRoot.getChildren().add(grass3.getImageView());
					break;
				case '7':
					Grass3 grass31 = new Grass3(j * 60, i * 60, 60, 60);
					grass31.getImageView().setScaleX(-1);
					platforms.add(grass31);
					gameRoot.getChildren().add(grass31.getImageView());
					break;
				case '8':
					Grass4 grass4 = new Grass4(j * 60, i * 60, 60, 60);
					platforms.add(grass4);
					gameRoot.getChildren().add(grass4.getImageView());
					break;
				case '9':
					Grass4 grass41 = new Grass4(j * 60, i * 60, 60, 60);
					grass41.getImageView().setRotate(grass41.getImageView().getRotate()+90);
					platforms.add(grass41);
					gameRoot.getChildren().add(grass41.getImageView());
					break;
				case 'b':
					Grass5 grass5 = new Grass5(j * 60, i * 60, 60, 60);
					platforms.add(grass5);
					gameRoot.getChildren().add(grass5.getImageView());
					break;
				case 'a':
					Grass5 grass51 = new Grass5(j * 60, i * 60, 60, 60);
					grass51.getImageView().setScaleX(-1);
					platforms.add(grass51);
					gameRoot.getChildren().add(grass51.getImageView());
					break;
				case 'c':
					Grass5 grass52 = new Grass5(j * 60, i * 60, 60, 60);
					grass52.getImageView().setRotate(grass52.getImageView().getRotate()+270);
					platforms.add(grass52);
					gameRoot.getChildren().add(grass52.getImageView());
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
					potionList.add(potionBox);
					gameRoot.getChildren().add(potionBox.getImageView());
					break;
				case 'A':
					AmmoBox ammoBox = new AmmoBox(j * 60, i * 60, 60, 60);
					ammoList.add(ammoBox);
					gameRoot.getChildren().add(ammoBox.getImageView());
					break;
				case 'T':
					Oak tree = new Oak(j * 60, i * 60, 310, 240);
					treeList.add(tree);
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

	public ArrayList<PotionBox> getPotionList() {
		return potionList;
	}

	public ArrayList<AmmoBox> getAmmoList() {
		return ammoList;
	}

	public ArrayList<Oak> getTreeList() {
		return treeList;
	}

}