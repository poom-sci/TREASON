package item;

public class Action {
	private boolean action;
	private String name;
	
	
	public Action(String name,boolean action) {
		super();
		this.action = action;
		this.name = name;
	}


	public boolean isAction() {
		return action;
	}
	
	public void setAction(boolean action) {
		this.action = action;
	}

	public String getName() {
		return name;
	}

}
