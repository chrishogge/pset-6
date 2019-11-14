import java.io.IOException;
import java.util.Scanner;

public class ATM {
    
    private Scanner in;
    private BankAccount activeAccount;
    private Bank bank;
    
    public static final int VIEW = 1;
    public static final int DEPOSIT = 2;
    public static final int WITHDRAW = 3;
    public static final int LOGOUT = 5;
    public static final int TRANSFER = 4;
    
    public static final int INVALID = 0;
    public static final int INSUFFICIENT = 1;
    public static final int SUCCESS = 2;
    
    public static final int FIRST_NAME_WIDTH = 20;
    public static final int LAST_NAME_WIDTH = 30;
    
    ////////////////////////////////////////////////////////////////////////////
    //                                                                        //
    // Refer to the Simple ATM tutorial to fill in the details of this class. //
    // You'll need to implement the new features yourself.                    //
    //                                                                        //
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * Constructs a new instance of the ATM class.
     */
    
    public ATM() {
        this.in = new Scanner(System.in);
        
        try {
			this.bank = new Bank();
		} catch (IOException e) {
			// cleanup any resources (i.e., the Scanner) and exit
		}
    }
    
    public void startup() {
    	System.out.print("\nWelcome to the AIT ATM!");
        
        System.out.print("Account No.: ");
        String accountNoString = in.next();
        
        if(accountNoString.equals("+")) {
        	System.out.print("\nFirst name: ");
        	String newFirstName = in.next();
        	
        	System.out.print("\nLast name: ");
        	String newLastName = in.next();
        	
        	System.out.print("\nPin: ");
        	int newPin = in.nextInt();
        	
        	activeAccount = bank.createAccount(newPin, new User(newFirstName, newLastName));
        	
        	System.out.print("\nThank you. Your account number is " + activeAccount.getAccountNo() + ".");
        	System.out.print("\nPlease login to access your newly created account.");
        }
        long accountNo = 0;	
        System.out.print("PIN        :");
        int pin = in.nextInt();
        
        if(isValidLogin(accountNo, pin)) {
        	System.out.print("\nHello, again " + activeAccount.getAccountHolder().getFirstName() + "!\n");
        	
        	boolean validLogin = true;
        	while(validLogin) {
        		switch(getSelection()) {
        		case VIEW: showBalance();break;
        		case DEPOSIT: deposit();break;
        		case WITHDRAW: withdraw();break;
        		case TRANSFER: transfer();break;
        		case LOGOUT: validLogin = false;break;
        		default: System.out.println("\nInvalid selection.\n");
        		}
        	}
        }else {
        	System.out.print("\nInvalid account number and/or pin.\n");
        }
    }
    
    public boolean isValidLogin(long accountNo, int pin) {
    	return accountNo==activeAccount.getAccountNo() && pin==activeAccount.getPin();
    }
    
    public int getSelection() {
    	System.out.println("[1] View Balance");
    	System.out.println("[2] Deposit Money");
    	System.out.println("[3] Withdraw Money");
    	System.out.println("[4] Transfer Money");
    	System.out.println("[5] Logout");
    	
    	return in.nextInt();
    }
    
    public void showBalance() {
    	System.out.println("\nCurrent balance: " + activeAccount.getBalance());
    }
    
    public void deposit() {
    	System.out.print("\nEnter amount: ");
    	double amount = in.nextDouble();
    	
    	int status = activeAccount.deposit(amount);
    	if(status == ATM.INVALID) {
    		System.out.println("\nDeposit rejected. Amount must be greater than $0.00.\n");
    	}else if(status == ATM.SUCCESS) {
    		System.out.println("\nDeposit accepted.\n");
    	}
    }
    
    public void withdraw() {
    	System.out.print("\nEnter amount: ");
        double amount = in.nextDouble();
            
        int status = activeAccount.withdraw(amount);
        if (status == ATM.INVALID) {
            System.out.println("\nWithdrawal rejected. Amount must be greater than $0.00.\n");
        } else if (status == ATM.INSUFFICIENT) {
            System.out.println("\nWithdrawal rejected. Insufficient funds.\n");
        } else if (status == ATM.SUCCESS) {
            System.out.println("\nWithdrawal accepted.\n");
        }
    }
    
    public void transfer() {
    	System.out.print("\nhaha cool");
    }
    
    public boolean isNum(String inputString) {
    	try {
        	long stringNum = Long.parseLong(inputString);
        } catch(NumberFormatException | NullPointException nfe) {
        	return false;
        }
    	return true;
    }
    
    /*
     * Application execution begins here.
     */
    
    public static void main(String[] args) {
        ATM atm = new ATM();
        
        atm.startup();
    }
}
