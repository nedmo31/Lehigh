/***
 * Class to represent a bank account
 * @author Nathan Edmondson
 * @version 0.1
 * Date created: 9/22/2021
 */
public abstract class BankAccount implements Comparable<BankAccount>, Closeable {

	//data members
	private int accountNumber;
	private double balance;
	private String owner;
	
	/***
	 * Constructor to be used when creating 
	 * bank accounts from a text file, meaining
	 * the account number is already made
	 * @param a to set the account number to
	 * @param b to set the balance to 
	 * @param o to set the owner to 
	 */
	public BankAccount(int a, double b, String o) {
		accountNumber = a;
		balance = b;
		owner = o;
	}
	
	/***
	 * Constructor for making a new bank account, 
	 * meaning the account number will be 
	 * randomized.
	 * @param b to set the balance to 
	 * @param o to set the owener to
	 */
	public BankAccount(double b, String o) {
		accountNumber = (int)(Math.random()*1000000); //random int from 0 to 999999
		balance = b;
		owner = o;
	}
	
	/**
	 * Getter method for the account number
	 * @return the data member accountNumber
	 */
	public int getAccountNumber() { return accountNumber; }
	
	/**
	 * Getter method for the balance of the account
	 * @return the data member balance
	 */
	public double getBalance() { return balance; }
	
	/**
	 * Getter method for the owner of the account
	 * @return the data member owner
	 */
	public String getOwner() { return owner; }
	
	/**
	 * Method to add money to the balance
	 * @param b to add to the balance
	 */
	public void deposit(double b) { balance += b; }
	
	/**
	 * Method to withdraw money from account. Method checks
	 * that they have enough money 
	 * @param b to withdraw
	 * @return true if the withdraw was successful, else false
	 */
	public boolean withdraw(double b) { 
		balance -= b;
		if (balance >= 0)
			return true;
		else {
			balance += b;
			return false;
		}
	}
	
	/**
	 * Setter ethod for the owner 
	 * @param s to set the owner to 
	 */
	public void setOwner(String s) { owner = s; }
	
	/**
	 * compareTo method from Comparable interface.
	 * Compares two BankAccounts by their balances
	 * @param b BankAccount to compareTo
	 * @return -1 if b has larger balance, 
	 * 0 if they have an equal balance, else 1
	 */
	public int compareTo(BankAccount o) {
		if (this.balance < o.balance)
			return 1;
		else if (this.balance == o.balance)
			return 0;
		else 
			return -1;
	}
	
	/**
	 * method from Closeable interface. Checks if the
	 * bank account is able to be close.
	 * @return true is balance is less than 200, else false
	 */
	public boolean isCloseable() {
		return balance < 200;
	}
	
	/**
	 * toString method to get a formatted String 
	 * giving information on this BankAccount
	 * @return a formatted String with data members
	 */
	public String toString() {
		return String.format("%-12s %-10d %-30s %-10.2f",
				this.getClass().getSimpleName(), accountNumber, owner, balance);	
	}
	
}
