package gui;

import com.sun.prism.Image;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;

public class PauseMenu extends AnchorPane {

	private Rectangle background;
	private GameButton resume;
	private GameButton restart;
	private GameButton exit;
	private ImageView sound;
	private boolean isSoundOn;
//	private AudioClip gameThemeSong;
//	private final String FONT_PATH = "PixelTakhisis-ZajJ.ttf";
//	private final String BACKGROUND_IMAGE = "board.png";

	public PauseMenu() {
		super();

		createBackground();
		createResumeButton();
//		createRestartButton();
		createExitButton();
		createSoundOn();

	}

	private void createBackground() {
		 background = new Rectangle(300, 720);
		 background.setOpacity(0.3);
		this.getChildren().add(background);
	

	}

	private void createResumeButton() {
		resume = new GameButton("resume");
		this.getChildren().add(resume);
		
		resume.setTranslateX(50);
		resume.setTranslateY(100);

	}

	private void createRestartButton() {
		restart = new GameButton("restart");
		this.getChildren().add(restart);
		
		restart.setTranslateX(50);
		restart.setTranslateY(200);

	}

	private void createExitButton() {
		exit = new GameButton("exit");
		this.getChildren().add(exit);
		
		exit.setTranslateX(50);
		exit.setTranslateY(200);

	}

	private void createSoundOn() {
		sound = new ImageView("speaker_on.png");
		this.getChildren().add(sound);
//		gameThemeSong=new AudioClip(
//				ClassLoader.getSystemResource("Off_limits.wav").toString());
//		gameThemeSong.setCycleCount(AudioClip.INDEFINITE);
//		gameThemeSong.setVolume(0.5);
//		gameThemeSong.play();
		
		isSoundOn=true;
		sound.setFitWidth(100);
		sound.setFitHeight(64);
		
		sound.setTranslateX(50);
		sound.setTranslateY(300);


		sound.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				sound.setEffect(new DropShadow());
				
				AudioClip mouse_enter_sound = new AudioClip(
						ClassLoader.getSystemResource("mouse_enter_sound.wav").toString());
				mouse_enter_sound.setVolume(0.1);
				mouse_enter_sound.play();

			}
		});
		
		sound.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				sound.setEffect(null);
			}

		});
		
		
	}
	
	public void changeSound() {
		if(isSoundOn) {
			setSoundOff();
		}else {
			setSoundOn();
		}
	}
	
	public void setSoundOff() {
		isSoundOn=false;
		sound.setImage(new javafx.scene.image.Image("speaker_off.png"));
//		gameThemeSong.stop();
	}
	
	public void setSoundOn() {
		isSoundOn=true;
		sound.setImage(new javafx.scene.image.Image("speaker_On.png"));
//		gameThemeSong.setVolume(0.5);
//		gameThemeSong.play();
	}
	

	public GameButton getResume() {
		return resume;
	}

	public GameButton getRestart() {
		return restart;
	}

	public GameButton getExit() {
		return exit;
	}

	public ImageView getSound() {
		return sound;
	}

	public boolean isSoundOn() {
		return isSoundOn;
	}
	
	

}
