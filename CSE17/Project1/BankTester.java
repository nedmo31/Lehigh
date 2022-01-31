import java.util.Scanner;
/**
 * Method with main method to test the bank
 * @author Nathan Edmondson
 * @version 0.1
 * Date modified: 9/25/2021
 */
public class BankTester {

	/**
	 * Main method. Uses a while to stay in the
	 * program as long as the user didn't enter in the 
	 * number to exit. Has a nested while loop for the
	 * menu of operation on a specific account. Uses
	 * switch case to handle each option. Saves the changes
	 * made after the while loop is exited. 
	 */
	public static void main(String[] args) {
		Bank bank = new Bank("accounts.txt");
		Scanner in = new Scanner(System.in);
		int input = 0;
		while (input != 7) {
			System.out.println("Select an Operation: ");
			System.out.println("[1] Find Account");
			System.out.println("[2] Add Account");
			System.out.println("[3] Remove Account");
			System.out.println("[4] View Accounts");
			System.out.println("[5] View Closable Accounts");
			System.out.println("[6] Sort Accounts");
			System.out.println("[7] Exit");
			input = in.nextInt();
			switch (input) {
			case 1 :
				System.out.println("Enter the account number (e.g. 123456):");
				String enteredAccount = in.next();
				int indexOfEnteredAccount = bank.findAccount(enteredAccount);
				if (indexOfEnteredAccount != -1) {
					int input2 = 0;
					while (input2  != 6) { //Menu 2
						System.out.println("Select an Operation: ");
						System.out.println("[1] Check Balance");
						System.out.println("[2] Withdraw");
						System.out.println("[3] Deposit");
						System.out.println("[4] Apply Monthly Interest (Savings)");
						System.out.println("[5] Apply Investment Risk (Investments)");
						System.out.println("[6] Go to Main Menu");
						input2 = in.nextInt();
						switch (input2) {
						case 1 :
							System.out.println(bank.getAccounts()[indexOfEnteredAccount].getBalance());
							break;
						case 2 : 
							System.out.println("Enter amount for withdrawl");
							if (bank.getAccounts()[indexOfEnteredAccount].withdraw(in.nextDouble())) 
								System.out.println("Success. New Balance: " + bank.getAccounts()[indexOfEnteredAccount].getBalance());
							else 
								System.out.println("Withdraw Failed. Current Balance: " + bank.getAccounts()[indexOfEnteredAccount].getBalance());
							break;
						case 3 : 
							 System.out.print("Enter amount for deposit");
							 bank.getAccounts()[indexOfEnteredAccount].deposit(in.nextDouble());
							 System.out.println("New Balance: " + bank.getAccounts()[indexOfEnteredAccount].getBalance());
							 break;
						case 4 : 
							if (bank.getAccounts()[indexOfEnteredAccount] instanceof Savings) {
								((Savings)bank.getAccounts()[indexOfEnteredAccount]).applyMonthlyInterest();			
								System.out.println("Success. New Balance: " + bank.getAccounts()[indexOfEnteredAccount].getBalance());
							}
							else 
								System.out.println("Error - Not a Savings Account!");
							break;
						case 5 : 
							if (bank.getAccounts()[indexOfEnteredAccount] instanceof Investment) {
								System.out.println(((Investment)bank.getAccounts()[indexOfEnteredAccount]).applyRisk());		
							}
							else 
								System.out.println("Error - Not an Investment Account!");
							break;
						}
						
							
					}
				}
				break;
			case 2 :
				System.out.println("Enter in type of account (savings/checking/investment)");
				String type = in.next();
				if (!type.equalsIgnoreCase("savings") && !type.equalsIgnoreCase("checking") && !type.equalsIgnoreCase("investment")) {
					System.out.println("You must enter in savings, checking, or invesment");
				}
				System.out.println("Enter owner's name (first,last)");
				String owner = in.next();
				System.out.println("Enter the balance");
				double balance = in.nextDouble();
				if (type.equalsIgnoreCase("savings")) {
					System.out.println("Enter interest rate");
					bank.addAccount(new Savings(balance, owner, in.nextDouble()));
				}
				else if (type.equalsIgnoreCase("checking")) {
					bank.addAccount(new Checking(balance, owner));

				}
				else {
					System.out.println("Enter investment type: ");
					bank.addAccount(new Investment(balance, owner, in.next()));
				}
				System.out.println("Succesfully added new account");
				break;
			case 3 :
				System.out.println("Enter in the account number to remove");
				if(bank.removeAccount(in.next()))
					System.out.println("Successfully removed the account");
				else 
					System.out.println("Operation failed");
				break;
			case 4 :
				bank.viewAllAccounts();
				break;
			case 5 :
				bank.viewClosableAccounts();
				break;
			case 6 : 
				java.util.Arrays.sort(bank.getAccounts(), 0, bank.getCount());
				break;
			}
		}
		in.close();
		bank.saveAccountsToFile();
		System.out.println("Thank you!");
	}

}
