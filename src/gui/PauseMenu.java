package gui;


import element.AudioLoader;
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
	private GameButton exit;
	private ImageView sound;
	private boolean isSoundOn;


	public PauseMenu() {
		super();

		createBackground();
		createResumeButton();
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

	private void createExitButton() {
		exit = new GameButton("exit");
		this.getChildren().add(exit);
		
		exit.setTranslateX(50);
		exit.setTranslateY(200);

	}

	private void createSoundOn() {
		sound = new ImageView("speaker_on.png");
		this.getChildren().add(sound);
		
		isSoundOn=true;
		sound.setFitWidth(100);
		sound.setFitHeight(64);
		
		sound.setTranslateX(50);
		sound.setTranslateY(300);


		sound.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				sound.setEffect(new DropShadow());
				
				AudioClip mouse_enter_sound = AudioLoader.Mouse_Enter_Sound;
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
		AudioLoader.setVolume(0.0);
		sound.setImage(new javafx.scene.image.Image("speaker_off.png"));
//		gameThemeSong.stop();
	}
	
	public void setSoundOn() {
		isSoundOn=true;
		AudioLoader.setVolume(0.5);
		sound.setImage(new javafx.scene.image.Image("speaker_on.png"));
	}
	

	public GameButton getResume() {
		return resume;
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
