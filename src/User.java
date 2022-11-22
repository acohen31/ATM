import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    /**
     * User's first name.
     */
    private String firstName;
    /**
     * User's last name.
     */
    private String lastName;
    /**
     * User's universally unique ID.
     */
    private String uuid;
    /**
     * MD5 Hash of user's pin
     */
    private byte pinHash[];
    /**
     * User's account list
     */
    private ArrayList<Account> accounts;
    /**
     * User object Constructor
     * @param firstName User's first name
     * @param lastName  User's last name  
     * @param pin       User's account pin number
     * @param theBank   User's bank
     */
    public User(String firstName, String lastName, String pin, Bank theBank) {
        this.firstName = firstName;
        this.lastName = lastName;
        
        // MD5 hash of pin
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithimException");
            e.printStackTrace();
            System.exit(1);
        }
        // get new UUID for user
        this.uuid = theBank.getNewUserUUID();
        // empty account list
        this.accounts = new ArrayList<Account>();
        System.out.printf("New user %s, %s with ID %s created.\n", lastName, firstName, this.uuid);
    }// Constructor User
    /**
     * Add an account for user.
     * @param anAccount the account that is added.
     */
    public void addAccount(Account anAccount) {
        this.accounts.add(anAccount);
    }
    /**
     * Get user uuid
     * @return uuid
     */
    public String getUUID() {
        return this.uuid;
    }
    /**
     * Check if given pin matches the user pin.
     * @param pin   pin to check.
     * @return      true if valid, false if not.
     */
    public boolean validatePin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithimException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }
    public void printAccountSummary() {
        System.out.printf("\n\n%s's accounts summary\n", this.firstName);
        for(int i = 0; i < this.accounts.size();i++) {
            System.out.printf("%d) %s\n", i+1, 
                    this.accounts.get(i).getSummaryLine());
        }
        System.out.println();
    }
    /**
     * Number of accounts tied to user
     * @return number of accounts
     */
    public int numAccounts() {
        return this.accounts.size();
    }
    /**
     * Print transaction history
     * @param accountIndex  History to print
     */
    public void printAccountTransHistory(int accountIndex) {
        this.accounts.get(accountIndex).printTransHistory();        
    }
    public double getAccountBalance(int accountIndex) {
        return this.accounts.get(accountIndex).getBalance();
    }
    /**
     * Get UUID of an account
     * @param accountIndex  index of account
     * @return              UUID of account
     */
    public String getAccountUUID(int accountIndex) {
        return this.accounts.get(accountIndex).getUUID();
    }
    /**
     * Add transaction to an account
     * @param accountIndex  index of account
     * @param amount        transaction amount
     * @param memo          transaction memo
     */
    public void addAccountTransaction(int accountIndex, double amount, String memo) {
        this.accounts.get(accountIndex).addTransaction(amount, memo);        
    }
}// Class User
