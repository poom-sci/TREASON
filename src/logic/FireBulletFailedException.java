package logic;

//you CAN modify the first line
public class FireBulletFailedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// you CAN add SerialVersionID if eclipse gives you warning
	public String message;
	
	public FireBulletFailedException(String message){
		this.message=message;
	}
	
}
