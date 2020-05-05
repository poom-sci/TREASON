package item;

public class DelayItem {
	
	private Double finalTime;
	private Entity item;
	
	public DelayItem(Double time,Double duration, Entity item) {
		super();
		this.finalTime=time+duration;
		this.item = item;
	}

	public Double getFinalTime() {
		return finalTime;
	}

	public Entity getItem() {
		return item;
	}
	
}
