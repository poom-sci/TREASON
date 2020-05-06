package item.Effect;

import item.Entity;

public class Effect extends Entity {
	protected boolean isInUsed;

	public Effect(String image_Path, int initX, int initY, int width, int height) {
		super(image_Path, initX, initY, width, height);
		// TODO Auto-generated constructor stub
		isInUsed=true;
		System.out.println(99999999);
	}

	public boolean isInUsed() {
		return isInUsed;
	}

	public void setInUsed(boolean isInUsed) {
		this.isInUsed = isInUsed;
	}
	
	
	

}
