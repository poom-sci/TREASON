package implement;

import exception.ConsumeItemFailedException;
import item.character.MainCharacter;

public interface Consumeable {
	public abstract void consumed(MainCharacter player) throws ConsumeItemFailedException;

	
}
