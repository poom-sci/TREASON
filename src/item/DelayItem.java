package item;

public class DelayItem {
	
	private Double finalTime;
	private Double duration;
	private Double time;
	private Entity item;
	
	public DelayItem(Double time,Double duration, Entity item) {
		super();
		this.time = time;
		this.finalTime=time+duration;
		this.duration=duration;
		this.item = item;
	}

	public Double getFinalTime() {
		return finalTime;
	}

	public Entity getItem() {
		return item;
	}
	
	
	
	
}
