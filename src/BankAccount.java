import java.text.NumberFormat;

@SuppressWarnings("unused")
public class BankAccount {
	
	@SuppressWarnings("unused")
	private static long prevAccountNo = 100000000L;
        
    private int pin;
    private long accountNo;
    private double balance;
    private User accountHolder;
    @SuppressWarnings("unused")
	private Bank bank;
    
    ////////////////////////////////////////////////////////////////////////////
    //                                                                        //
    // Refer to the Simple ATM tutorial to fill in the details of this class. //
    //                                                                        //
    ////////////////////////////////////////////////////////////////////////////
    
    /*
     * Formats the account balance in preparation to be written to the data file.
     * 
     * @return a fixed-width string in line with the data file specifications.
     */
    
    private String formatBalance() {
        return String.format("%1$15s", balance);
    }
    
    /*
     * Converts this BankAccount object to a string of text in preparation to
     * be written to the data file.
     * 
     * @return a string of text formatted for the data file
     */
    
    @Override
    public String toString() {
        return String.valueOf(accountNo) +
            String.valueOf(pin) +
            accountHolder.serialize() +
            formatBalance();
    }
    
    public BankAccount(int pin, long accountNo, double balance, User accountHolder) {
        this.pin = pin;
        this.accountNo = accountNo;
        this.balance = balance;
        this.accountHolder = accountHolder;
    }
    
    public int getPin() {
    	return pin;
    }
    
    public long getAccountNo() {
    	return accountNo;
    }
    
    public double getBalance() {
    	return balance;
    }
    
    public User getAccountHolder() {
    	return accountHolder;
    }
    
    public int deposit(double amount) {
    	if(amount <= 0) {
    		return ATM.INVALID;
    	}else if((balance + amount) > ATM.BALANCE_MAX) {
    		return ATM.MAXIMUM;
    	}else {
    		balance = balance + amount;
    	}
    	
    	return ATM.SUCCESS;
    }
    
    public int withdraw(double amount) {
    	if(amount <= 0) {
    		return ATM.INVALID;
    	}else if(amount > balance) {
    		return ATM.INSUFFICIENT;
    	}else {
    		balance = balance - amount;
    	}
    	
    	return ATM.SUCCESS;
    }
    
    public int transfer(double accountBalance, double transAccountBalance, double amount) {
    	
    	if((accountBalance - amount) < ATM.BALANCE_MIN) {
    		return ATM.INSUFFICIENT;
    	} else if((transAccountBalance + amount) > ATM.BALANCE_MAX) {
    		return ATM.MAXIMUM;
    	} else if(amount <= ATM.TRANSFER_MIN) {
    		return ATM.INVALID_AMOUNT;
    	}else {
    		return ATM.SUCCESS;
    	}
    }
    
}
