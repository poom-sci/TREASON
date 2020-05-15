package components.box;

import components.Entity;
import components.character.GameCharacter;
import components.character.MainCharacter;
import implement.Interactable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class PotionBox extends Box implements Interactable {
	
	
	public PotionBox(int initX,int initY, int width, int height) {
		super("potion.png",initX, initY,width,height);
	}
	
	@Override
	public void interact(MainCharacter player) {
		player.getItemsInventory().get(0).addAmount(1);
		
	}
	

}
