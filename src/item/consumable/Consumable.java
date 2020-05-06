package item.consumable;

public abstract class Consumable {
	
	protected int amount = 0;
	
	public Consumable(int amount) {
		this.amount = amount;
	}
	
	public void pickUp(int amount) {
		this.amount += amount;
	}
	
	public boolean consumed(int amount) {
		if(this.amount >= amount) {
			this.amount -= amount;
			return true;
		}else {
			return false;
		}
	}
	
	public int getAmount() {
		return this.amount;
	}
	
}
