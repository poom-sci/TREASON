package exception;

public class AddLeaderboardScoresFailedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// you CAN add SerialVersionID if eclipse gives you warning
	public String message;
	
	public AddLeaderboardScoresFailedException(String message){
		this.message=message;
	}
	
}
