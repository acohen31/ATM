import java.util.Date;

public class Transaction {
    /**
     * Transaction amount.
     */
    private double amount;
    /**
     * Time and date of transaction.
     */
    private Date timestamp;
    /**
     * Transaction memo.
     */
    private String memo;
    /**
     * Account tied to the transaction.
     */
    private Account inAccount;
    /**
     * New Transaction
     * @param amount    the transaction amount.
     * @param inAccount the account the transaction belongs to.
     */
    public Transaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }
    /**
     * New Transaction with a memo
     * @param amount    the transaction amount.
     * @param memo      the transaction memo.
     * @param inAccount the account the transaction belongs to.
     */
    public Transaction(double amount, String memo, Account inAccount) {
        this(amount, inAccount);
        this.memo = memo;
    }
    /**
     * get transaction amount
     * @return  amount
     */
    public double getAmount() {
        return this.amount;
    }
    /**
     * Get summary of transaction
     * @return summary line
     */
    public String getSummaryLine() {
        if(this.amount >= 0) {
            return String.format("%s: $%.02f : %s", 
                    this.timestamp.toString(), this.amount, this.memo);
        }else {
            return String.format("%s: $%.02f : %s", 
                    this.timestamp.toString(), this.amount, this.memo);
        }
    }
}// Class Transaction
