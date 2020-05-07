package item.Effect;

public class Warning extends Effect {

	public Warning(int initX, int initY) {

		super("warning.png",initX, initY, 60, 60);

		this.boundX=15;

//		sprite = new SpriteAnimation(imageView, Duration.millis(3000), 30, 5, 0, 0, 192, 192);
//		sprite.setCycleCount(Animation.INDEFINITE);
//		sprite.play();

	}

}
