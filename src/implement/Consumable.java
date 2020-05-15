package implement;

import components.character.MainCharacter;
import exception.ConsumeItemFailedException;

public interface Consumable {
	public abstract void consumed(MainCharacter player) throws ConsumeItemFailedException;

	
}
