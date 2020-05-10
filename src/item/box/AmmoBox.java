package item.box;

import implement.Interactable;
import item.Entity;
import item.character.GameCharacter;
import item.character.MainCharacter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class AmmoBox extends Entity implements Interactable {

	public AmmoBox(int initX, int initY, int width, int height) {
		super("AmmoBox.png", initX, initY, width, height);

	}
	
	@Override
	public void interact(MainCharacter player) {
		player.getItemsInventory().get(1).addAmount(1);
	}
}