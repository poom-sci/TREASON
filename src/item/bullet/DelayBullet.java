package item.bullet;

public class DelayBullet {
	
	private Double finalTime;
	private int duration;
	private Double time;
	private Bullet bullet;
	
	public DelayBullet(Double time,int duration, Bullet bullet) {
		super();
		this.time = time;
		this.finalTime=time+duration;
		this.duration=duration;
		this.bullet = bullet;
	}

	public Double getFinalTime() {
		return finalTime;
	}

	public Bullet getBullet() {
		return bullet;
	}
	
	
	
	
}
