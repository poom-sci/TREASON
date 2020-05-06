package item.consumable;

public class Potion extends ConsumableItem {
	
	protected String name;

	public Potion(int amount) {
		super("potion.png",50,50,amount);
		itemType='P';
	}

}
