package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import element.AudioLoader;
import gui.GameButton;
import gui.GameSubScene;
import gui.PauseMenu;
import gui.PlayerInfoBox;
import gui.SpriteAnimation;
import item.character.MainCharacter;
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

	ArrayList<GameButton> menuButtons;

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
		
		MenuThemeSong = AudioLoader.Star_Commander;
		MenuThemeSong.setCycleCount(AudioClip.INDEFINITE);
		MenuThemeSong.play();

		menuButtons = new ArrayList<GameButton>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);

		CreateLogo();
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
				startNew.setLayoutX(50);
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
				startContinue.setLayoutX(300);
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
								gameManager.continueThemeSond();
								MenuThemeSong.stop();
							}
							else {
								System.out.println("no save");
							}
						}else {
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

				Label header = new Label("Player   : Score   : time");
				header.setTextFill(Color.DARKRED);
				try {
					header.setFont(Font.loadFont(new FileInputStream("res/PixelTakhisis-ZajJ.ttf"), 23));
				} catch (FileNotFoundException e) {
//					 TODO Auto-generated catch block
					e.printStackTrace();
				}
//				 setting the position of the text
				header.setLayoutX(100);
				header.setLayoutY(100);

				ScoreSubScene.getPane().getChildren().add(header);

				for (int i = 0; i < scoreBoard.getTopPlayer().size(); i++) {
					Label text = new Label();
					// Setting the text to be added.
					text.setText((i + 1) + ". " + scoreBoard.getTopPlayer(i));
					text.setTextFill(Color.LIGHTCYAN);
					try {
						text.setFont(Font.loadFont(new FileInputStream("res/PixelTakhisis-ZajJ.ttf"), 23));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//					 setting the position of the text
					text.setLayoutX(70);
					text.setLayoutY(130 + i * 20);
					ScoreSubScene.getPane().getChildren().add(text);
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

	private void CreateLogo() {
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

	public AudioClip getMenuThemeSong() {
		return MenuThemeSong;
	}
	
	
}


