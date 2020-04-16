package view;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.text.StyledEditorKit.BoldAction;

import gui.GameButton;
import gui.SpriteAnimation;
import item.box.Box;
import item.bullet.GunBullet;
import item.character.MainCharacter;
import item.weapon.Weapon;
import item.Entity;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.GameController;
import logic.LevelData;

public class GameViewManager {

	private GameViewManager gameViewManager;

	private long startTime;
	DoubleProperty time = new SimpleDoubleProperty(0.0);
	
	private static final int HEIGHT = 720;
	private static final int WIDTH = 1280;
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	private Stage menuStage;
	private AnimationTimer gameTimer;

	private static ArrayList<GameButton> menuButtons;
	boolean alreadyPressedESCAPE = false;
	private Pane menuPane;
	private Rectangle menuBox;
	private boolean isPause;

	private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();

	private Boolean isGamePause;

	private Pane gameRoot;

	private GameController player1Controller;
	private Rectangle playerInfoBox;
	private Rectangle heathBox;
	private Weapon weapon;
	private ImageView weaponImage;
	private Text bulletLeft;
	
	private boolean isPlayerDie;

	public GameViewManager() {
		inititializeStage();
	}

	public void inititializeStage() {
		gameRoot = new Pane();
		player1Controller = new GameController(gameRoot);
		this.gamePane = new AnchorPane();
		gamePane.getChildren().addAll(player1Controller.getBg(), gameRoot);
		gameScene = new Scene(gamePane, WIDTH, HEIGHT);
		
		gameScene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
		gameScene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
		
		keyboardListener();

		player1Controller.setKeys(keys);

		createPlayerInfo();

		gameStage = new Stage();
		gameStage.setScene(gameScene);
		gameStage.setTitle("Game Scene2");
		gameStage.setResizable(false);

		menuButtons = new ArrayList<GameButton>();
		isGamePause = false;

		createMenu();
		createGameLoop();

	}

	public DoubleProperty getTime() {
		return time;
	}

	public void setTime(DoubleProperty time) {
		this.time = time;
	}

	public void createNewGame(Stage menuStage) {
		this.menuStage = menuStage;
		this.menuStage.hide();
		gameStage.show();
	}

	public void createGameLoop() {
		gameTimer = new AnimationTimer() {

			
			@Override
			public void start() {
				startTime = System.currentTimeMillis();
				super.start();
			}

			@Override
			public void handle(long timestamp) {
		        long now = System.currentTimeMillis();
		        time.set((now - startTime) / 1000.0);
		        System.out.println(time);
				player1Controller.getControl();
				update();

			}
		};
		gameTimer.start();
	}

	private void update() {
		player1Controller.setKeys(keys);
		player1Controller.setTime(time.doubleValue());
		this.gameRoot = player1Controller.getGameRoot();
		heathBox = player1Controller.getPlayerHealthBox();
		weapon = player1Controller.getPlayerWeapon();
		isPlayerDie=player1Controller.getPlayer().isDie();
//		if(isPlayerDie) {
//			gameTimer.stop();
//		}

		if (weaponImage != player1Controller.getPlayerWeapon().getImageView()) {
			createWeaponImage();
		}

		bulletLeft.setText("Bullet Left : " + weapon.getBulletLeft());

		if (isPressed(KeyCode.ESCAPE)) {
			if (!alreadyPressedESCAPE) {
				menuPane.setVisible(true);
				System.out.println("show");
				gameTimer.stop();
				alreadyPressedESCAPE = true;
				isPause = true;
			}
		} else {
			alreadyPressedESCAPE = false;
		}
	}

	private void keyboardListener() {
		gamePane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent key) {
				
				if (key.getCode() == KeyCode.ESCAPE) {
					if (isPause) {
						menuPane.setVisible(false);
						System.out.println("hide");
						gameTimer.start();
						isPause = false;
					}
					else {
						menuPane.setVisible(true);
						System.out.println("show");
						gameTimer.stop();
						alreadyPressedESCAPE = true;
						isPause = true;
					}
				} 

			}
		});
	}

	private boolean isPressed(KeyCode key) {
		return keys.getOrDefault(key, false);
	}

	private void createPlayerInfo() {

		createPlayerInfoBox();

		createHealthBox();

		createWeaponImage();

		createBulletLeft();
	}
	
	private void createBulletLeft() {
		if (bulletLeft != null) {
			gamePane.getChildren().remove(bulletLeft);
		}

		bulletLeft = new Text("Bullet Left : " + weapon.getBulletLeft());
		bulletLeft.setX(180);
		bulletLeft.setY(100);
		gamePane.getChildren().add(bulletLeft);
	}
	
	private void createPlayerInfoBox() {
		if (playerInfoBox != null) {
			gamePane.getChildren().remove(playerInfoBox);
		}

		Rectangle playerInfoBox = new Rectangle(250, 120);
		playerInfoBox.setTranslateX(30);
		playerInfoBox.setTranslateY(30);
		playerInfoBox.setFill(Color.ANTIQUEWHITE);
		playerInfoBox.setOpacity(0.5);
		gamePane.getChildren().add(playerInfoBox);
	}

	private void createWeaponImage() {
		if (weapon != null) {
			gamePane.getChildren().remove(weaponImage);
		}

		weapon = player1Controller.getPlayerWeapon();
		weaponImage = player1Controller.getPlayerWeapon().getImageView();
		weaponImage.setTranslateX(50);
		weaponImage.setTranslateY(80);
		gamePane.getChildren().add(weaponImage);
	}

	private void createHealthBox() {
		if (heathBox != null) {
			gamePane.getChildren().remove(heathBox);
		}

		heathBox = player1Controller.getPlayerHealthBox();
		gamePane.getChildren().add(heathBox);
		heathBox.setTranslateX(50);
		heathBox.setTranslateY(50);
	}

	private void createMenu() {
		menuPane = new Pane();

		menuBox = new Rectangle(400, 1000);
		menuBox.setFill(Color.GREY);
		menuBox.setOpacity(0.5);
		menuPane.getChildren().add(menuBox);

		GameButton resume = new GameButton("Resume");
		addMenuButton(resume);
		resume.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				gameTimer.start();
				menuPane.setVisible(false);

			}
		});

		GameButton restart = new GameButton("Restart");
		addMenuButton(restart);

		restart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				restart();

			}

		});

		GameButton option = new GameButton("Option");
		addMenuButton(option);

		GameButton menu = new GameButton("Menu");
		addMenuButton(menu);

		menu.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				gameStage.hide();
				menuStage.show();
				menuPane.setVisible(false);
				isGamePause = true;
			}

		});

		menuPane.setVisible(false);
		menuPane.toFront();
		gamePane.getChildren().addAll(menuPane);
	}

	private void addMenuButton(GameButton button) {
		button.setLayoutX(120);
		menuPane.getChildren().add(button);
		button.setLayoutY(100 + (menuButtons.size() * 100));
		menuButtons.add(button);

	}

	private void restart() {

		menuPane.getChildren().remove(menuBox);
		this.gamePane.getChildren().remove(menuPane);
		for (int i = 0; i < 4; i++) {
			this.gamePane.getChildren().remove(menuButtons.get(0));
			menuButtons.remove(menuButtons.get(0));
		}
		this.gamePane.getChildren().remove(gameRoot);
		gameRoot = new Pane();
		player1Controller = new GameController(gameRoot);
		this.gamePane.getChildren().add(gameRoot);

		createPlayerInfo();

		createMenu();

		gameTimer.start();

	}

	public Stage getGameStage() {
		return gameStage;
	}

	public AnimationTimer getGameTimer() {
		return gameTimer;
	}

}
