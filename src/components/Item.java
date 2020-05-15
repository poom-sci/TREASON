package components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Item {
	protected String image_Path;
	protected Image image;
	protected ImageView imageView;
	
	protected int width;
	protected int height;
	
	protected int boundX;
	protected int boundY;
	
	public Item(String image_Path, int width, int height) {

		image = new Image(image_Path);
		imageView = new ImageView(image);
		imageView.setFitHeight(height);
		imageView.setFitWidth(width);
		this.width = width;
		this.height = height;

	}

	public String getImage_Path() {
		return image_Path;
	}

	public void setImage_Path(String image_Path) {
		this.image_Path = image_Path;
	}

	public Image getImage() {
		return image;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	
	
}
