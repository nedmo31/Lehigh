/**
 * Exception class to be thrown when the user doesn't enter
 * an account number made of 6 digits 
 * @author Nathan Edmondson
 * @version 0.1
 * Date modified: 9/25/2021
 */
@SuppressWarnings("serial")
public class InvalidAccountException extends Exception{

	public InvalidAccountException() {
		super("You did not enter a valid account number.");
	}

	public InvalidAccountException(String message) {
		super(message);
	}

}
