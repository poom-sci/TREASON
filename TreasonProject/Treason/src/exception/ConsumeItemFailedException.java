package exception;

public class ConsumeItemFailedException extends Exception{
	private static final long serialVersionUID = 1L;
	// you CAN add SerialVersionID if eclipse gives you warning
	public String message;
	
	public ConsumeItemFailedException(String message){
		this.message=message;
	}
}
