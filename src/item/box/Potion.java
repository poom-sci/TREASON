package item.box;

import item.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Potion extends Entity {
	
	private int amount = 0;
	
	public Potion(int initX,int initY, int width, int height) {
		super("potion.png",initX, initY,width,height);
	}
		// TODO Auto-generated constructor stub

}
