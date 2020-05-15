package components.consumable;

import components.character.MainCharacter;
import exception.ConsumeItemFailedException;
import implement.Consumable;
import implement.Fireable;

public class Ammo extends ConsumableItem implements Consumable {
	public Ammo(int amount) {
		super("AmmoBox.png", 50, 50, amount);
		hotKey = 'J';
	}

	@Override
	public void consumed(MainCharacter player) throws ConsumeItemFailedException {
		// TODO Auto-generated method stub
		if (this.amount <= 0) {
			throw new ConsumeItemFailedException("item is not enough.");
		}
		if (player.getWeapon().getCurrentBullet() >= player.getWeapon().getMaxBullet()) {
			throw new ConsumeItemFailedException("bullet is full.");
		}
		((Fireable) player.getWeapon()).addBullet(10);
		this.amount -= 1;
	}
}
