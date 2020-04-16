package view;

import java.util.ArrayList;
import java.util.HashMap;

import gui.GameButton;
import gui.GameSubScene;
import gui.SpriteAnimation;
import item.character.MainCharacter;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

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
	private boolean hasGameStage=false;
	private GameViewManager gameManager;
	private Stage gameStage;
	
	private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();

	public ViewManager() {
		
		
		menuButtons = new ArrayList<GameButton>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);

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
				
				GameButton startNew=new GameButton("new");
				startNew.setLayoutX(50);
				startNew.setLayoutY(200);
				StartSubScene.getPane().getChildren().add(startNew);
				
				startNew.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {

							gameManager= new GameViewManager();
							gameManager.createNewGame(mainStage);
							hasGameStage=true;
							gameStage=gameManager.getGameStage();
						
						
					}
				});
				
				GameButton startContinue=new GameButton("Continue");
				startContinue.setLayoutX(300);
				startContinue.setLayoutY(200);
				StartSubScene.getPane().getChildren().add(startContinue);
				
				
				startContinue.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						if(hasGameStage) {
							gameStage.show();
							mainStage.hide();
							gameManager.getGameTimer().start();
							
						}
						else {
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

			}
		});
	}

	private void createHelpButton() {
		GameButton help=new GameButton("Help");
		addMenuButton(help);
		
		help.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				ShowSubScene(HelpSubScene);
				
				 Text text = new Text();      
			      
			      //Setting the text to be added. 
			      text.setText("Hello how are you"); 
			       
			      //setting the position of the text 
			      text.setX(50); 
			      text.setY(50); 
			      mainPane.getChildren().add(text);
				
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
				
				ImageView imageView = new ImageView(new Image("removebg.png"));
				imageView.setViewport(new Rectangle2D(26, 0, 96, 96));
				Animation poom=new SpriteAnimation(imageView, Duration.millis(8000), 24,8, 26,  0,96,96);
				poom.setCycleCount(Animation.INDEFINITE);
				poom.play();
				
				mainPane.getChildren().add(new Group(imageView));
				
	
			}
		});
	}

	private void createQuitButton() {
		GameButton quit = new GameButton("Quit");
		addMenuButton(quit);

		quit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				mainStage.close();
			}
		});

	}

	private void createBackground() {
		Image backgroundImage = new Image("main_background.jpg", 1000, 780, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
	}
}