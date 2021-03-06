package gui;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;

public class GameSubScene extends SubScene {

	private static final int HEIGHT=600;
	private static final int WIDTH=800;
	private final String BACKGROUND_IMAGE = "board_0001.png";
	
	private boolean isHidden;
	
	public GameSubScene() {
		super(new AnchorPane(), WIDTH, HEIGHT);
		prefHeight(HEIGHT);
		prefWidth(WIDTH);
		
		BackgroundImage image =new BackgroundImage(new Image(BACKGROUND_IMAGE,700,500,false,true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		AnchorPane root2=(AnchorPane) this.getRoot();
		
		root2.setBackground(new Background(image));
		setLayoutX(1024);
		setLayoutY(200);
		isHidden=true;
	}
	
	public void moveSubScene() {

		TranslateTransition transition=new TranslateTransition();
		transition.setDuration(Duration.millis(1500));
		transition.setNode(this);
		if(isHidden) {
			transition.setToX(-720);
			isHidden=false;
		}else {
			transition.setToX(0);
			isHidden=true;
		}
		
		transition.play();
	}
	
	public AnchorPane getPane() {
		return (AnchorPane) this.getRoot();
	}
	
}
