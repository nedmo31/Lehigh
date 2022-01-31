import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;
/**
 * Class to represent a bank. Has methods to
 * modify the current accounts
 * @author Nathan Edmondson
 * @version 0.1
 * Date modified: 9/25/2021
 */
public class Bank {

	//data members
	private BankAccount[] accounts = new BankAccount[100];
	private int count;
	
	/**
	 * This constructor intializes a bank using a text file.
	 * @param s name of text file
	 */
	public Bank(String s) {
		count = 0;
		try {
			Scanner readFile = new Scanner(new File(s));
			while (readFile.hasNext()) {
				String type = readFile.next();
				int acc = readFile.nextInt();
				String own = readFile.next();
				double bal = readFile.nextDouble();
				switch (type) {
					case "Savings" :
						double ine = readFile.nextDouble();
						accounts[count] = new Savings(acc, bal, own, ine);
						break;
					case "Investment" :
						String typ = readFile.next();
						accounts[count] = new Investment(acc, bal, own, typ);
						break;
					case "Checking" :
						accounts[count] = new Checking(acc, bal, own);
						break;
				}
				count++;
			}
			readFile.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Error finding the file");
		}
	}
	
	/**
	 * Getter method for the array of BankAccounts
	 * @return the accounts array
	 */
	public BankAccount[] getAccounts() {
		return accounts;
	}
	
	/**
	 * Getter method for the number of BankAccounts
	 * @return int value of number of BankAccounts
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * Method searches through the array to find
	 * the account with the entered number
	 * @param target is the entered account number
	 * @return index of the account, else -1
	 */
	public int findAccount(String target) {
		try {
			validateAccountNumber(target);
		}
		catch(InvalidAccountException e) {
			System.out.println(e.getMessage());
			return -1;
		}
		for (int i = 0; i < count; i++) {
			if (accounts[i].getAccountNumber() == Integer.parseInt(target))
				return i;
		}
		return -1; //If it is not found it returns -1
	}
	
	/**
	 * Method to add a bank account to the array
	 * @param b to add to the array 
	 */
	public void addAccount(BankAccount b) {
		accounts[count++] = b;
		
	}
	
	/**
	 * Method to remove an account from the array. 
	 * Makes sure it isCloseable() 
	 * @param account the account number to be closed
	 * @return boolean regarding the success of the operation
	 */
	public boolean removeAccount(String account) {
		try {
			validateAccountNumber(account);
		}
		catch(InvalidAccountException e) {
			System.out.println(e.getMessage());
			return false; //If the entered account number is invalid
		}
		boolean found = false;
		for (int i = 0; i < count; i++) {
			if (accounts[i].getAccountNumber() == Integer.parseInt(account)
					&& accounts[i].isCloseable()) {
				found = true;
				for (int j = i; j < count - 1; j++) {
					accounts[j] = accounts[j+1];
				}
				count--;
				break;
			}
		}
		return found; //If it was found and removed return true
	}
	
	/**
	 * Method to print out all the accounts and their info
	 * to the user. 
	 */
	public void viewAllAccounts() {
		System.out.printf("%-12s %-10s %-30s %-10s Interest/Investment Type\n",
				"Type", "Account #", "Owner", "Balance");
		for (int i = 0; i < count; i++) {
			System.out.println(accounts[i]);
		}
	}
	
	/**
	 * Method to print out all accounts that can be closed
	 * and their info to the user
	 */
	public void viewClosableAccounts() {
		System.out.printf("%-12s %-10s %-30s %-10s Interest/Investment Type\n",
				"Type", "Account #", "Owner", "Balance");
		for (int i = 0; i < count; i++) {
			if (accounts[i].isCloseable())
				System.out.println(accounts[i]);
		}
	}
	
	/**
	 * Method called before the end of the run to 
	 * save the current array to the text file so
	 * that the info will be carried over to the next run
	 */
	public void saveAccountsToFile() {
		try {
			PrintWriter pw = new PrintWriter(new File("accounts.txt"));
			for (int i = 0; i < count; i++) {
				pw.println(accounts[i]);
			}
			pw.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Can't write to the file");
		}
	}

	/**
	 * Method that uses regex to validate that the user inputted
	 * a valid format for an account number
	 * @param account user entry to validate
	 * @throws InvalidAccountException when it doesn't match the format
	 */
	public void validateAccountNumber(String account) throws InvalidAccountException {
		if (!account.matches("\\d{6}")) {
			throw new InvalidAccountException("Account numbers must be 6 digits");
		}
	}
	
}
