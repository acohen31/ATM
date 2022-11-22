import java.util.ArrayList;

public class Account {
    /**
     * Account name.
     */
    private String name;
    /**
     * Account universally unique ID.
     */
    private String uuid;
    /**
     * The user that owns the account.
     */
    private User holder;
    /**
     * Transaction list for the account.
     */
    private ArrayList<Transaction> transactions;
    /**
     * Account object constructor
     * @param name      name of the account.
     * @param holder    user that holds the account.
     * @param theBank   bank issuing the account.
     */
    public Account(String name, User holder, Bank theBank) {
        this.name = name;
        this.holder = holder;
        this.uuid = theBank.getNewAccountUUID();
        // Initialize transactions
        this.transactions = new ArrayList<Transaction>();
    }
    /**
     * Get account uuid
     * @return uuid
     */
    public String getUUID() {
        return this.uuid;
    }
    /**
     * Get summary line for account
     * @return the string summary
     */
    public String getSummaryLine() {
        double balance = this.getBalance();
        // format summary line if balance is negative
        if(balance >= 0) {
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
        } else {
            return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
        }
    }
    /**
     * Get the balance of this account
     * @return balance
     */
    public double getBalance() {
        double balance = 0;
        for(Transaction i: this.transactions) {
            balance += i.getAmount();
        }
        return balance;
    }
    /**
     * Print transaction history
     */
    public void printTransHistory() {
        System.out.printf("\nTransaction history for account %s\n", this.uuid);
        for(int i = this.transactions.size()-1; i > -1; i--) {
            System.out.printf(this.transactions.get(i).getSummaryLine() + "\n");
        }
        System.out.println();
    }
    /**
     * Add transaction to an account
     * @param amount    transaction amount
     * @param memo      transaction memo
     */
    public void addTransaction(double amount, String memo) {
        Transaction newTransaction = new Transaction(amount, memo, this);
        this.transactions.add(newTransaction);
    }
}
