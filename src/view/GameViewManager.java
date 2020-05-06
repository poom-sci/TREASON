package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.text.StyledEditorKit.BoldAction;

import exception.AddLeaderboardScoresFailedException;
import gui.GameButton;
import gui.GameSubScene;
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

	private static ArrayList<GameButton> menuButtons;
	boolean alreadyPressedESCAPE = false;
	private Pane menuPane;
	private Rectangle menuBox;
	private boolean isPause;

	private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();

	private Boolean isGameover;

	private Pane gameRoot;

	private GameController player1Controller;
	private Rectangle playerInfoBox;
	private Rectangle heathBox;
	private ImageView inventory;
	private Weapon weapon;
	private ImageView weaponImage;
	private ImageView potionImage;
	private Text bulletLeft;
	private Text potionLeft;
	private Text potionHotkey;

	private boolean isPlayerDie;

	public GameViewManager() {
		inititializeStage();
	}

	public void inititializeStage() {
		this.gamePane = new AnchorPane();
		player1Controller = new GameController();
		gameRoot = player1Controller.getGameRoot();
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
//		gameStage.setResizable(false);

		menuButtons = new ArrayList<GameButton>();
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
		player1Controller.setKeys(keys);
		player1Controller.setTime(time.doubleValue());
		gameRoot = player1Controller.getGameRoot();
		heathBox = player1Controller.getPlayer().getCurrentHPBox();
		weapon = player1Controller.getPlayer().getWeapon();
		isPlayerDie = player1Controller.getPlayer().isDie();

		if (isPlayerDie) {
			gameTimer.stop();
			AudioClip mouse_pressed_sound = new AudioClip(
					ClassLoader.getSystemResource("gameover_sound.wav").toString());
			mouse_pressed_sound.play();
			createGameoverSubScene();
		}

		if (weaponImage != player1Controller.getPlayer().getWeapon().getImageView()) {
			createWeaponImage();
		}

		bulletLeft.setText("Bullet Left : " + weapon.getCurrentBullet());
		potionLeft.setText("" + player1Controller.getPlayerInventory().get(0).getAmount());

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
					} else {
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

		createInventoryBox();

		createPotionImage();

		createPotionLeft();

		createPotionHotkey();
	}

	private void createBulletLeft() {
		if (bulletLeft != null) {
			gamePane.getChildren().remove(bulletLeft);
		}

		bulletLeft = new Text("Bullet Left : " + weapon.getCurrentBullet());
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

	private void createInventoryBox() {
		if (inventory != null) {
			gamePane.getChildren().remove(inventory);
		}

		inventory = new ImageView(new Image("inventory2.png"));
		inventory.setFitHeight(70); 
		inventory.setFitWidth(300); 
		inventory.setOpacity(0.6);
		inventory.setTranslateX(750);
		inventory.setTranslateY(625);
		
		gamePane.getChildren().add(inventory);
	}

	private void createPotionImage() {
		ConsumableItem item = player1Controller.getPlayerInventory().get(0);
		ImageView imageView = item.getImageView();
		imageView.setTranslateX(760);
		imageView.setTranslateY(640);
		gamePane.getChildren().add(imageView);
	}

	private void createPotionLeft() {

		potionLeft = new Text("" + player1Controller.getPlayerInventory().get(0).getAmount());
		potionLeft.setX(760);
		potionLeft.setY(645);
		gamePane.getChildren().add(potionLeft);
	}

	private void createPotionHotkey() {
		potionHotkey = new Text("H");
		potionHotkey.setX(805);
		potionHotkey.setY(645);
		gamePane.getChildren().add(potionHotkey);
	}

	private void createWeaponImage() {
		if (weapon != null) {
			gamePane.getChildren().remove(weaponImage);
		}

		weapon = player1Controller.getPlayer().getWeapon();
		weaponImage = weapon.getImageView();
		weaponImage.setTranslateX(50);
		weaponImage.setTranslateY(80);
		gamePane.getChildren().add(weaponImage);
	}

	private void createHealthBox() {
		if (heathBox != null) {
			gamePane.getChildren().remove(heathBox);
		}

		heathBox = player1Controller.getPlayer().getCurrentHPBox();
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
		gameRoot.getChildren().clear();
		this.gamePane.getChildren().remove(menuPane);
		for (int i = 0; i < 4; i++) {
			this.gamePane.getChildren().remove(menuButtons.get(0));
			menuButtons.remove(menuButtons.get(0));
		}

		player1Controller = new GameController();
		this.gameRoot = player1Controller.getGameRoot();

		createPlayerInfo();

		createMenu();

		gameTimer.start();

	}

	private void createGameoverSubScene() {
		createBlackDrop();

		createLogoGameover();

		Label nameScores = new Label("Name :");
		Label scoreScores = new Label("Score : " + player1Controller.getPlayerPoint());
		Label timeScores = new Label("Time : " + time.doubleValue());
		TextField textField = new TextField();
		try {
			nameScores.setTextFill(Color.web("EA8F3C"));
			nameScores.setFont(Font.loadFont(new FileInputStream("res/PixelTakhisis-ZajJ.ttf"), 23));

			scoreScores.setTextFill(Color.web("EA8F3C"));
			scoreScores.setFont(Font.loadFont(new FileInputStream("res/PixelTakhisis-ZajJ.ttf"), 23));

			timeScores.setTextFill(Color.web("EA8F3C"));
			timeScores.setFont(Font.loadFont(new FileInputStream("res/PixelTakhisis-ZajJ.ttf"), 23));

			textField.setFont(Font.loadFont(new FileInputStream("res/PixelTakhisis-ZajJ.ttf"), 23));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		nameScores.setLayoutX(500);
		nameScores.setLayoutY(408);
		textField.setLayoutX(600);
		textField.setLayoutY(400);
		scoreScores.setLayoutX(500);
		scoreScores.setLayoutY(460);
		timeScores.setLayoutX(500);
		timeScores.setLayoutY(510);

		GameButton submitButton = new GameButton("submit");
		gamePane.getChildren().add(submitButton);
		submitButton.setLayoutX(400);
		submitButton.setLayoutY(550);
		submitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					isGameover = true;
					Leaderboards scoreBoard = Leaderboards.getInstance();
					scoreBoard.loadScore();
					scoreBoard.addPlayerScore(textField.getText(), player1Controller.getPlayerPoint(),
							time.doubleValue());
					scoreBoard.saveScores();
					gameStage.close();
					menuStage.show();
				} catch (AddLeaderboardScoresFailedException e) {
					Alert alert = new Alert(AlertType.WARNING, "Add leaderboard failed, " + e.message);
					alert.setTitle("Error");
					alert.show();
				}

			}

		});

		GameButton ExitButton = new GameButton("Exit");
		gamePane.getChildren().add(ExitButton);
		ExitButton.setLayoutX(650);
		ExitButton.setLayoutY(550);
		ExitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				isGameover = true;
				gameStage.close();
				menuStage.show();

			}

		});

		gamePane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent key) {

				if (key.getCode() == KeyCode.ENTER) {
					try {
						isGameover = true;
						Leaderboards scoreBoard = Leaderboards.getInstance();
						scoreBoard.loadScore();
						scoreBoard.addPlayerScore(textField.getText(), player1Controller.getPlayerPoint(),
								time.doubleValue());
						scoreBoard.saveScores();
						gameStage.close();
						menuStage.show();
					} catch (AddLeaderboardScoresFailedException e) {
						System.out.println("Add leaderboard failed, " + e.message);
					}
				}

			}
		});

		gamePane.getChildren().addAll(nameScores, textField, scoreScores, timeScores);

	}

	private void createBlackDrop() {
		Rectangle GameoverSubScene = new Rectangle(1280, 720);
		GameoverSubScene.setFill(Color.BLACK);
		GameoverSubScene.setOpacity(0.8);
		gamePane.getChildren().add(GameoverSubScene);
	}

	private void createLogoGameover() {
		ImageView logo = new ImageView("gameover.png");

		logo.setLayoutX(1280 / 2 - 300 * 1.5);
		logo.setLayoutY(50);
		logo.setFitHeight(300 * 1.5);
		logo.setFitWidth(600 * 1.5);
		logo.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(new DropShadow());
				AudioClip mouse_enter_sound = new AudioClip(
						ClassLoader.getSystemResource("mouse_enter_sound.wav").toString());
				mouse_enter_sound.setVolume(0.1);
				mouse_enter_sound.play();

			}
		});

		logo.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				logo.setEffect(null);
			}

		});

		gamePane.getChildren().add(logo);
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

}
