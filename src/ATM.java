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
    
    public static final int FIRST_NAME_MIN_WIDTH = 1;
    public static final int FIRST_NAME_WIDTH = 20;
    public static final int LAST_NAME_MIN_WIDTH = 1;
    public static final int LAST_NAME_WIDTH = 30;
    
    public static final int PIN_WIDTH = 4;
    public static final int PIN_MIN = 1000;
    public static final int PIN_MAX = 9999;
    
    public static final int ACCOUNT_NO_WIDTH = 9;
    public static final long ACCOUNT_NO_MIN = 100000001;
    public static final long ACCOUNT_NO_MAX = 999999999;
    
    public static final int BALANCE_WIDTH = 15;
    public static final double BALANCE_MIN = 0.00;
    public static final double BALANCE_MAX = 999999999999.99;
    
    
    
    
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
    	
    	boolean newAccount = true;
    	
    	System.out.print("\nAccount No.: ");
    	String accountNoString = in.nextLine();
    	
    	
    	while(newAccount) {
        
        if(accountNoString.equals("+")) {
        	newAccount();
        	System.out.print("\nAccount No.: ");
        	accountNoString = in.nextLine();
        }else if(!(accountNoString.contentEquals("+")) && isNum(accountNoString)) {
        	newAccount = false;
        }
        
    	}
    	
    	long accountNo = Long.parseLong(accountNoString);
    	
    	System.out.print("PIN        : ");
        int pin = in.nextInt();
        boolean loginCycle = true;
        while(loginCycle) {
        		try {
        			activeAccount = bank.login(accountNo, pin);
        			accountNo = activeAccount.getAccountNo();
        		}catch(NullPointerException nfe) {
        			if(accountNo == -1 && pin == -1) {
        				System.out.print("\nGoodbye!");
        				return;
        			}else {
        			System.out.print("\nInvalid account number and/or PIN.\n");
        			System.out.print("\nAccount No.: ");
        	    	accountNoString = in.nextLine();
        	    	
        	    	
        	    	while(newAccount) {
        	        
        	        if(accountNoString.equals("+")) {
        	        	newAccount();
        	        }else if(!(accountNoString.contentEquals("+")) && isNum(accountNoString)) {
        	        	newAccount = false;
        	        }
        	        
        	        System.out.print("\nAccount No.: ");
        	    	accountNoString = in.nextLine();
        	        
        	    	}
        	    	
        	    	accountNo = Long.parseLong(accountNoString);
        	    	
        	    	System.out.print("PIN        : ");
        	        pin = in.nextInt();
        			}
        		}
	     
        		loginCycle = false;
        		activeAccount = bank.login(accountNo,pin);
	            accountNo = activeAccount.getAccountNo();
	            
	        	
	        	System.out.print("\nHello, again, " + activeAccount.getAccountHolder().getFirstName() + "!\n");
	
	        	boolean validLogin = true;
	        	while(validLogin) {
	        		switch(getSelection()) {
	        		case VIEW: showBalance(accountNo);break;
	        		case DEPOSIT: deposit();break;
	        		case WITHDRAW: withdraw();break;
	        		case TRANSFER: transfer();break;
	        		case LOGOUT: validLogin = false;bank.save();break;
	        		default: System.out.println("\nInvalid selection.\n");
	        		}
	        	}
        }
    }
    
    public boolean isValidLogin(long accountNo, int pin) {
    	return accountNo==activeAccount.getAccountNo() && pin==activeAccount.getPin();
    }
    
    public BankAccount accountLogin(long accountNo, int pin) {
    	return bank.login(accountNo, pin);
    }
    
    public int getSelection() {
    	System.out.println("\n[1] View Balance");
    	System.out.println("[2] Deposit Money");
    	System.out.println("[3] Withdraw Money");
    	System.out.println("[4] Transfer Money");
    	System.out.println("[5] Logout");
    	
    	return in.nextInt();
    }
    
    public void showBalance(long accountNo) {
    	System.out.println(activeAccount.getBalance());
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
    	
    	bank.update(bank.getAccount(activeAccount.getAccountNo()));
    	bank.save();
    	
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
        bank.save();
    }
    
    public void transfer() {
    	System.out.print("\nEnter account: ");
    	long transferAccountNo = in.nextLong();
    	System.out.print("Enter amount: ");
    	double transferAmount = in.nextDouble();
    	
    	BankAccount transferAccount = bank.getAccount(transferAccountNo);
    	
    	activeAccount.withdraw(transferAmount);
    	transferAccount.deposit(transferAmount);
    	
    	System.out.print("\nTransfer accepted.");
    	
    	bank.save();
    	
    }
    
    public boolean isNum(String inputString) {
    	try {
        	@SuppressWarnings("unused")
			long stringNum = Long.parseLong(inputString);
        } catch(NumberFormatException | NullPointerException nfe) {
        	return false;
        }
    	return true;
    }
    
    public boolean isntNullLogin(long accountNo, int pin) {
    	try {
    		isValidLogin(accountNo,pin);
    	}catch(NullPointerException nfe) {
    		return false;
    	}
    	return true;
    }
    
    public void newAccount() {
    	System.out.print("\nFirst name: ");
    	String newFirstName = in.nextLine();
    	
    	System.out.print("\nLast name: ");
    	String newLastName = in.nextLine();
    	
    	System.out.print("\nPin: ");
    	int newPin = in.nextInt();
    	
    	activeAccount = bank.createAccount(newPin, new User(newFirstName, newLastName));
    	
    	System.out.print("\nThank you. Your account number is " + activeAccount.getAccountNo() + ".");
    	System.out.print("\nPlease login to access your newly created account.");
    }
    
    /*
     * Application execution begins here.
     */
    
    public static void main(String[] args) {
        ATM atm = new ATM();
        
        atm.startup();
    }
}
