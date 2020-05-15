package components.consumable;

import components.character.MainCharacter;
import exception.ConsumeItemFailedException;
import implement.Consumable;

public class Potion extends ConsumableItem implements Consumable {

	public Potion(int amount) {
		super("potion.png", 50, 50, amount);
		hotKey = 'H';
	}

	@Override
	public void consumed(MainCharacter player) throws ConsumeItemFailedException {
		// TODO Auto-generated method stub
		if (this.amount <= 0) {
			throw new ConsumeItemFailedException("item is not enough.");
		}

		if (player.getMaxHP() == player.getCurrentHP()) {
			throw new ConsumeItemFailedException("Hp is full.");
		}

		this.amount -= 1;
		player.increaseCurrentHP(100);

	}

}
