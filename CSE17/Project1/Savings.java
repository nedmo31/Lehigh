/**
 * Class to represent a savings account 
 * in a bank
 * @author Nathan Edmondson
 * @version 0.1
 * Date Modified: 9/25/2021
 */
public class Savings extends BankAccount {

	//data members 
	private double interest;
	
	/**
	 * Constructor for creating a savings account from 
	 * data stored in the text file. 
	 * @param a to set accountNumber to
	 * @param b to set balance to
	 * @param o to set owner to 
	 * @param i to set the interest rate to
	 */
	public Savings(int a, double b, String o, double i) {
		super(a, b, o); interest = i;
	}

	/**
	 * Constructor for creating a brand new savings account
	 * @param b to set the balance to
	 * @param o to set the owner to
	 * @param i to set the interest rate to
	 */
	public Savings(double b, String o, double i) {
		super(b, o); interest = i;
	}
	
	/**
	 * Getter method for the interest on 
	 * a savings account
	 * @return the data member interest
	 */
	public double getInterest() { return interest; }

	/**
	 * Setter method for the intersest rate of the 
	 * savings account
	 * @param i to set the interest rate to
	 */
	public void setInterest(double i) { interest = i; }

	/**
	 * Method to add the money earned from interest
	 * to the savings account
	 */
	public void applyMonthlyInterest() {
		deposit(getBalance() * ((interest/100.0)/12.0));
	}
	
	/**
	 * toString method to get a formatted String 
	 * giving information on this Savings
	 * @return a formatted String with data members
	 */
	public String toString() {
		return super.toString()	+ interest;
	}
	
}
