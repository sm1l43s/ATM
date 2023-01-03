import java.util.Date;

public class Transaction {

    /**
     * The amount of this transaction.
     */
    private final double amount;

    /**
     * The time and date of this transaction.
     */
    private final Date timestamp;

    /**
     * A memo for this transaction.
     */
    private String memo;

    /**
     * The account in which the transaction was performed.
     */
    private final Account inAccount;

    /**
     * Create a new transaction
     * @param amount the amount transaction
     * @param inAccount the account the transaction belongs to
     */
    public Transaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }

    /**
     * Create a new transaction
     * @param amount the amount transaction
     * @param memo the memo for the transaction
     * @param inAccount the account the transaction belongs to
     */
    public Transaction(double amount, String memo, Account inAccount) {
        this(amount, inAccount);
        this.memo = memo;
    }

    /**
     * Get amount of the transaction
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Get a string summarizing the transaction
     * @return the summary string
     */
    public String getSummaryLine() {

        if (this.amount >= 0) {
            return String.format("%s : $%.02f : %s", this.timestamp.toString(), this.amount, this.memo);
        } else {
            return String.format("%s : $(%.02f) : %s", this.timestamp.toString(), this.amount, this.memo);
        }

    }
}
