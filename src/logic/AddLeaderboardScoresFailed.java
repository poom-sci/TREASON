package logic;

public class AddLeaderboardScoresFailed extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// you CAN add SerialVersionID if eclipse gives you warning
	public String message;
	
	public AddLeaderboardScoresFailed(String message){
		this.message=message;
	}
	
}
