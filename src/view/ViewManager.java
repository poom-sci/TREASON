package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import components.character.MainCharacter;
import element.AudioLoader;
import gui.GameButton;
import gui.GameSubScene;
import gui.PauseMenu;
import gui.PlayerInfoBox;
import gui.SpriteAnimation;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.Leaderboards;
import logic.LevelData;

public class ViewManager {

	private static final int HEIGHT = 780;
	private static final int WIDTH = 1000;
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;

	private int MENU_BUTTONS_START_X = 100;
	private int MENU_BUTTONS_START_Y = 150;

	private ArrayList<GameButton> menuButtons;

	private GameSubScene HelpSubScene;
	private GameSubScene CreditSubScene;
	private GameSubScene ScoreSubScene;
	private GameSubScene StartSubScene;

	private GameSubScene NowShowing;
	private boolean hasGameStage = false;
	private GameViewManager gameManager;
	private Stage gameStage;
	private AudioClip MenuThemeSong;

	public ViewManager() {

		AudioLoader.setVolume(0.5);

		MenuThemeSong = AudioLoader.Star_Commander;
		MenuThemeSong.setCycleCount(AudioClip.INDEFINITE);
		MenuThemeSong.play();

		menuButtons = new ArrayList<GameButton>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);

		createLogo();
		createButtons();
		createBackground();
		CreateSubScenes();

		keyboardListener();

		mainStage = new Stage();
		mainStage.setScene(mainScene);
		mainStage.setTitle("Game Project");

	}

	public Stage getMainStage() {
		return mainStage;
	}

	private void keyboardListener() {
		mainPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.ESCAPE) {
					mainStage.close();
				}
			}
		});
	}

	private void CreateSubScenes() {
		StartSubScene = new GameSubScene();
		mainPane.getChildren().add(StartSubScene);

		ScoreSubScene = new GameSubScene();
		mainPane.getChildren().add(ScoreSubScene);

		HelpSubScene = new GameSubScene();
		mainPane.getChildren().add(HelpSubScene);

		CreditSubScene = new GameSubScene();
		mainPane.getChildren().add(CreditSubScene);

	}

	private void ShowSubScene(GameSubScene scene) {
		if (NowShowing != null) {
			NowShowing.moveSubScene();
		}
		scene.moveSubScene();
		NowShowing = scene;
	}

	private void addMenuButton(GameButton button) {
		button.setLayoutX(MENU_BUTTONS_START_X);
		button.setLayoutY(MENU_BUTTONS_START_Y + (menuButtons.size() * 100));
		menuButtons.add(button);
		mainPane.getChildren().add(button);
	}

	private void createButtons() {
		createStartButton();
		createScoreButton();
		createHelpButton();
		createCreditButton();
		createQuitButton();
	}

	private void createStartButton() {
		GameButton start = new GameButton("Start");
		addMenuButton(start);

		start.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				ShowSubScene(StartSubScene);

				GameButton startNew = new GameButton("new");
				startNew.setLayoutX(110);
				startNew.setLayoutY(200);
				StartSubScene.getPane().getChildren().add(startNew);

				startNew.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						if (gameManager != null) {
							gameManager.removeAll();
							System.gc();
						}
						AudioClip MenuThemeSong = AudioLoader.Star_Commander;
						MenuThemeSong.stop();

						gameManager = new GameViewManager();
						gameManager.createNewGame(mainStage);
						hasGameStage = true;
						gameStage = gameManager.getGameStage();

					}
				});

				GameButton startContinue = new GameButton("Continue");
				startContinue.setLayoutX(360);
				startContinue.setLayoutY(200);
				StartSubScene.getPane().getChildren().add(startContinue);

				startContinue.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						if (hasGameStage) {
							if (!gameManager.getIsGameover()) {
								gameStage.show();
								mainStage.hide();
								gameManager.getGameTimer().start();
								gameManager.continueThemeSong();
								MenuThemeSong.stop();
							} else {
								System.out.println("no save");
							}
						} else {
							System.out.println("no save");
						}
					}

				});

			}
		});

	}

	private void createScoreButton() {
		GameButton score = new GameButton("Score");
		addMenuButton(score);

		score.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				ShowSubScene(ScoreSubScene);
				ScoreSubScene.getPane().getChildren().clear();

				Leaderboards scoreBoard = Leaderboards.getInstance();
				scoreBoard.loadScore();

				Label header = new Label("Player     : Score    :  time");
				header.setTextFill(Color.DARKRED);
				header.setFont(Font.loadFont(
						getClass().getClassLoader().getResource("PixelTakhisis-ZajJ.ttf").toExternalForm(), 23));
				header.setLayoutX(150);
				header.setLayoutY(100);

				ScoreSubScene.getPane().getChildren().add(header);

				for (int i = 0; i < scoreBoard.getTopPlayer().size(); i++) {
					Label name = new Label();
					name.setText((i + 1) + ". " + scoreBoard.getTopPlayerName(i));
					name.setTextFill(Color.LIGHTCYAN);
					name.setFont(Font.loadFont(
							getClass().getClassLoader().getResource("PixelTakhisis-ZajJ.ttf").toExternalForm(), 23));
					name.setLayoutX(150);

					Label score = new Label();
					score.setText("" + scoreBoard.getTopPlayerScore(i));
					score.setTextFill(Color.LIGHTCYAN);
					score.setFont(Font.loadFont(
							getClass().getClassLoader().getResource("PixelTakhisis-ZajJ.ttf").toExternalForm(), 23));
					score.setLayoutX(355);

					Label time = new Label();
					time.setText("" + scoreBoard.getTopPlayerTime(i));
					time.setTextFill(Color.LIGHTCYAN);
					time.setFont(Font.loadFont(
							getClass().getClassLoader().getResource("PixelTakhisis-ZajJ.ttf").toExternalForm(), 23));
					time.setLayoutX(510);

					name.setLayoutY(130 + i * 30);
					score.setLayoutY(130 + i * 30);
					time.setLayoutY(130 + i * 30);
					ScoreSubScene.getPane().getChildren().addAll(name, score, time);
					scoreBoard.saveScores();
				}

			}
		});
	}

	private void createHelpButton() {
		GameButton help = new GameButton("Help");
		addMenuButton(help);

		help.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				ShowSubScene(HelpSubScene);

				HelpSubScene.getPane().getChildren().clear();

				Label credit = new Label(
						"pressed \"A\" to move left \n"
						+ "pressed \"D\" to move right \n"
						+ "pressed \"W\" to jump \n"
						+ "pressed \"SPACEBAR\" to fired bullet \n"
						+ "pressed \"H\" to use healing potion \n"
						+ "pressed \"J\" to use ammo pack \n"
						+ "to win the game, \n"
						+ "you must kill the boss "
						+ "at the level 3");
				credit.setTranslateX(100);
				credit.setTranslateY(150);
				credit.setTextFill(Color.DARKRED);
				credit.setFont(Font.loadFont(
						getClass().getClassLoader().getResource("PixelTakhisis-ZajJ.ttf").toExternalForm(), 16));

				HelpSubScene.getPane().getChildren().addAll(credit);

			}
		});

	}

	private void createCreditButton() {
		GameButton credit = new GameButton("Credit");
		addMenuButton(credit);

		credit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				ShowSubScene(CreditSubScene);
				CreditSubScene.getPane().getChildren().clear();

				Label credit = new Label(
						"           Game Editor\n"
						+ "    Poom Suchao-in 6231349621\n"
						+ "Napat Maneeratpongsuk 6231318121");
				credit.setTranslateX(100);
				credit.setTranslateY(150);
				credit.setTextFill(Color.FLORALWHITE);
				credit.setFont(Font.loadFont(
						getClass().getClassLoader().getResource("PixelTakhisis-ZajJ.ttf").toExternalForm(), 23));

				CreditSubScene.getPane().getChildren().addAll(credit);

			}
		});
	}

	private void createQuitButton() {
		GameButton quit = new GameButton("Quit");
		addMenuButton(quit);

		quit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-dgenerated method stub
				mainStage.close();
			}
		});

	}

	private void createLogo() {
		ImageView logo = new ImageView("treason_logo.png");
		logo.setLayoutX(420);
		logo.setLayoutY(0);
		logo.setFitHeight(200 * 1.5);
		logo.setFitWidth(300 * 1.5);

		logo.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(new DropShadow());
				AudioClip mouse_enter_sound = AudioLoader.Mouse_Enter_Sound;
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

		mainPane.getChildren().add(logo);
	}

	private void createBackground() {
		Image backgroundImage = new Image("main_background.jpg", 1000, 780, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
	}

}
