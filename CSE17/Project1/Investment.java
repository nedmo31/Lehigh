/**
 * Class to represent an Investmenet account
 * in a bank.
 * @author Nathan Edmondson
 * @version 0.1
 * Date Modified: 9/25/2021
 */
public class Investment extends BankAccount {

	//Data member
	String type;
	
	/**
	 * Constructor to initialize an Investment account
	 * from the saved text file
	 * @param a to set the account number to
	 * @param b to set the balance to
	 * @param o to set the owner to
	 * @param s to set the type to
	 */
	public Investment(int a, double b, String o, String s) {
		super(a, b, o); type = s;
	}

	/**
	 * Constructor to initialize a Checking account
	 * when user adds an account. Account number is
	 * auto generated.
	 * @param b to set the balance to
	 * @param o to set the owner to
	 * @param s to set the type to
	 */
	public Investment(double b, String o, String s) {
		super(b, o); type = s;
	}

	/**
	 * Getter method for the type of 
	 * investment account
	 * @return the data member type
	 */
	public String getType() { return type; }
	
	/**
	 * Setter method for the type of 
	 * investment account
	 * @param s to set the type to
	 */
	public void setType(String s) { type = s; }
	
	/**
	 * Method to test how the investment does. 
	 * 50% chance of either gain or loss. Then
	 * deposits/withdraws 0-5% of current balance
	 * @return String with results of the method
	 */
	public String applyRisk() {
		if (Math.random() >= .5) { //Profit
			//Sets a new balance equals to the old balance plus
			//up to 5% of the old balance;
			deposit(getBalance() / 20 * Math.random()); 
			return "Profit - new balance is: "+getBalance();
		}
		else { //Loss
			//Same as adding except change to substraction 
			withdraw(getBalance() / 20 * Math.random());
			//This shouldn't ever withdraw an invalid amount, so 
			//it doesn't need to be checked
			return "Loss - new balance is: "+getBalance();
		}
	}
	
	/**
	 * toString method to get a formatted String 
	 * giving information on this Investment
	 * @return a formatted String with data members
	 */
	public String toString() {
		return super.toString()	+ type;
	}
}
