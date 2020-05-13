package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.text.StyledEditorKit.BoldAction;

import exception.AddLeaderboardScoresFailedException;
import gui.GameButton;
import gui.GameEndingScene;
import gui.GameSubScene;
import gui.PauseMenu;
import gui.PlayerInfoBox;
import gui.PlayerInventoryBox;
import gui.SpriteAnimation;
import item.box.Box;
import item.bullet.GunBullet;
import item.character.MainCharacter;
import item.consumable.ConsumableItem;
import item.weapon.Weapon;
import item.Entity;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.DoubleExpression;
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
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.GameController;
import logic.Leaderboards;
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

	boolean alreadyPressedESCAPE = false;
	private boolean isPause;

	private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();

	private Boolean isGameover;
	private AudioClip gameThemeSong;

	private Pane gameRoot;

	private GameController player1Controller;
	private PauseMenu pauseMenu;
	private PlayerInfoBox playerInfoBox;
	private PlayerInventoryBox playerInventory;
	private GameEndingScene gameEndingScene;

	public GameViewManager() {
		inititializeStage();
	}

	public void inititializeStage() {

		gamePane = new AnchorPane();
		player1Controller = new GameController();
		gameRoot = player1Controller.getGameRoot();
		gamePane.getChildren().addAll(player1Controller.getBg(), gameRoot);
		gameScene = new Scene(gamePane, WIDTH, HEIGHT);

		gameThemeSong = new AudioClip(ClassLoader.getSystemResource("Off_limits.wav").toString());
		gameThemeSong.setCycleCount(AudioClip.INDEFINITE);
		gameThemeSong.setVolume(0.5);
		gameThemeSong.play();

		gameScene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
		gameScene.setOnKeyReleased(event -> keys.put(event.getCode(), false));

		keyboardListener();

		player1Controller.setKeys(keys);

		createPlayerInfo();

		gameStage = new Stage();
		gameStage.setScene(gameScene);
		gameStage.setTitle("Game Scene2");
		gameStage.setResizable(false);

		isGameover = false;

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
				update();
				player1Controller.getControl();

			}
		};
		gameTimer.start();
	}

	private void update() {
		if (player1Controller.isGameEnd()) {
			gameTimer.stop();
			AudioClip gameover_sound = new AudioClip(ClassLoader.getSystemResource("gameover_sound.wav").toString());
			gameover_sound.play();
			gameThemeSong.stop();
			createEndSubScene();
		}

		player1Controller.setTime(time.doubleValue());

		playerInfoBox.setPlayerHP(player1Controller.getPlayer().getCurrentHPBox());
		playerInfoBox.getNumber().setText("" + player1Controller.getPlayer().getWeapon().getCurrentBullet());
		if (playerInfoBox.getWeaponImage() != player1Controller.getPlayer().getWeapon().getImageView()) {
			playerInfoBox.changeWeaponImage(player1Controller.getPlayer().getWeapon().getImageView());
		}

		playerInventory.getNumberItem1()
				.setText("" + player1Controller.getPlayer().getItemsInventory().get(0).getAmount());
		playerInventory.getNumberItem2()
				.setText("" + player1Controller.getPlayer().getItemsInventory().get(1).getAmount());

		if (isPressed(KeyCode.ESCAPE)) {
			if (!alreadyPressedESCAPE) {
				pauseMenu.setVisible(true);
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
						pauseMenu.setVisible(false);
						gameTimer.start();
						isPause = false;
					} else {
						pauseMenu.setVisible(true);
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

		playerInfoBox = new PlayerInfoBox();
		playerInfoBox.setTranslateX(50);
		playerInfoBox.setTranslateY(50);
		this.gamePane.getChildren().add(playerInfoBox);

		playerInventory = new PlayerInventoryBox(player1Controller.getPlayer());
		playerInventory.setTranslateX(750);
		playerInventory.setTranslateY(625);
		this.gamePane.getChildren().add(playerInventory);

	}

	private void createMenu() {

		pauseMenu = new PauseMenu();

		GameButton resume = pauseMenu.getResume();
		resume.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				gameTimer.start();
				pauseMenu.setVisible(false);

			}
		});

		GameButton restart = pauseMenu.getRestart();
		restart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				restart();

			}

		});

		GameButton exit = pauseMenu.getExit();
		exit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				gameStage.hide();
				menuStage.show();
				pauseMenu.setVisible(false);
				gameThemeSong.stop();

			}

		});

		ImageView soundImage = pauseMenu.getSound();

		soundImage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				pauseMenu.changeSound();
				if (gameThemeSong.isPlaying()) {
					gameThemeSong.stop();
				} else {
					gameThemeSong.play();
				}
			}
		});

		pauseMenu.setVisible(false);
		gamePane.getChildren().addAll(pauseMenu);
	}

	private void restart() {

		gameRoot.getChildren().clear();
		this.gamePane.getChildren().remove(pauseMenu);
		
		player1Controller = new GameController();
		this.gameRoot = player1Controller.getGameRoot();

		createPlayerInfo();
		createMenu();
		gameTimer.start();

	}

	private void createEndSubScene() {
		gameEndingScene = new GameEndingScene(player1Controller.isWin(), player1Controller.getPlayerPoint(),
				time.doubleValue());
		gamePane.getChildren().add(gameEndingScene);

		GameButton submitButton = gameEndingScene.getSubmitButton();
		submitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					isGameover = true;
					Leaderboards scoreBoard = Leaderboards.getInstance();
					scoreBoard.loadScore();
					scoreBoard.addPlayerScore(gameEndingScene.getTextField().getText(),
							player1Controller.getPlayerPoint(), time.doubleValue());
					scoreBoard.saveScores();
					gameStage.close();
					menuStage.show();
					removeAll();
				} catch (AddLeaderboardScoresFailedException e) {
					Alert alert = new Alert(AlertType.WARNING, "Add leaderboard failed, " + e.message);
					alert.setTitle("Error");
					alert.show();
				}

			}

		});

		GameButton ExitButton = gameEndingScene.getExitButton();
		ExitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				isGameover = true;
				gameStage.close();
				menuStage.show();
				removeAll();

			}

		});
//
		gamePane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent key) {

				if (key.getCode() == KeyCode.ENTER) {
					try {
						isGameover = true;
						Leaderboards scoreBoard = Leaderboards.getInstance();
						scoreBoard.loadScore();
						scoreBoard.addPlayerScore(gameEndingScene.getTextField().getText(),
								player1Controller.getPlayerPoint(), time.doubleValue());
						scoreBoard.saveScores();
						menuStage.show();
						gameStage.close();
						removeAll();
					} catch (AddLeaderboardScoresFailedException e) {
						System.out.println("Add leaderboard failed, " + e.message);
					}
				}

			}
		});
	}

	public void removeAll() {
		gameRoot = null;
		player1Controller = null;
		pauseMenu = null;
		playerInfoBox = null;
		playerInventory = null;
		gameEndingScene = null;
		gameViewManager = null;
		gameRoot = null;
		player1Controller = null;
		gamePane = null;
		gameScene = null;
		gameStage = null;
		gameTimer = null;
		pauseMenu = null;

		System.gc();
	}

	public Stage getGameStage() {
		return gameStage;
	}

	public AnimationTimer getGameTimer() {
		return gameTimer;
	}

	public Boolean getIsGameover() {
		return isGameover;
	}

	public AudioClip getGameThemeSong() {
		return gameThemeSong;
	}

	public void continueThemeSond() {
		if (pauseMenu.isSoundOn()) {
			gameThemeSong.play();
		} else {
			gameThemeSong.stop();
		}
	}

}
