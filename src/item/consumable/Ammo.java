package item.consumable;

public class Ammo extends ConsumableItem {
	public Ammo(int amount) {
		super("AmmoBox.png",50,50,amount);
		itemType='A';
		hotKey='J';
	}
}
