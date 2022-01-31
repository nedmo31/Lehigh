/**
 * Class to represent a Checking account
 * in a bank
 * @author Nathan Edmondson
 * @version 0.1
 * Date Modified: 9/25/2021
 */
public class Checking extends BankAccount {

	/**
	 * Constructor to initialize a Checking account
	 * from the saved text file
	 * @param a to set the account number to
	 * @param b to set the balance to
	 * @param o to set the owner to
	 */
	public Checking(int a, double b, String o) {
		super(a, b, o);
	}

	/**
	 * Constuctor used when user creates a Checking
	 * account. Randomly generated account number
	 * @param b to set the balance to 
	 * @param o to set the owner to 
	 */
	public Checking(double b, String o) {
		super(b, o);
	}

	/**
	 * toString method to get a formatted String 
	 * giving information on this Checking
	 * @return a formatted String with data members
	 */
	public String toString() {
		return super.toString();
	}
	
}
