package gui;

import application.Main;
import components.character.MainCharacter;
import components.consumable.ConsumableItem;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlayerInventoryBox extends AnchorPane {

	private MainCharacter player;
	private ImageView inventory;
	private Label numberItem1;
	private Label numberItem2;
	

	public PlayerInventoryBox(MainCharacter player) {
		super();

		this.player=player;
		createInventoryImage();
		createItemImage();
		createItem1();
		createItem2();

	}
	
	private void createInventoryImage() {

		inventory = new ImageView(new Image("inventory2.png"));
		inventory.setFitHeight(70);
		inventory.setFitWidth(300);
		inventory.setOpacity(0.6);

		this.getChildren().add(inventory);
	}

	private void createItemImage() {

		for (int i = 0; i < player.getItemsInventory().size(); i++) {
			ConsumableItem item = player.getItemsInventory().get(i);
			
			ImageView imageView = item.getImageView();
			imageView.setTranslateX(10+78 * i);
			imageView.setTranslateY(15);
			this.getChildren().add(imageView);

			Label Hotkey = new Label(item.getHotKey() + "");
			Hotkey.setTranslateX(60+78 * i);
			Hotkey.setTranslateY(15);
			this.getChildren().add(Hotkey);
		}

	}

	private void createItem1() {

		numberItem1 = new Label("" + player.getItemsInventory().get(0).getAmount());
		numberItem1.setTranslateX(10);
		numberItem1.setTranslateY(15);
		this.getChildren().add(numberItem1);
	}

	private void createItem2() {

		numberItem2 = new Label("" + player.getItemsInventory().get(1).getAmount());
		numberItem2.setTranslateX(85);
		numberItem2.setTranslateY(15);
		this.getChildren().add(numberItem2);
	}

	public Label getNumberItem1() {
		return numberItem1;
	}

	public Label getNumberItem2() {
		return numberItem2;
	}
	
	
}
