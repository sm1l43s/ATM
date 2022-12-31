import java.util.ArrayList;

public class Account {

    /**
     * The name of the account.
     */
    private final String name;

    /**
     * The current balance of the account
     */
    private double balance;

    /**
     * The account ID number.
     */
    private final String uuid;

    /**
     * The User objects that owns this account.
     */
    private final User holder;

    /**
     * The list of transaction for this account.
     */
    private ArrayList<Transaction> transactions;

    /**
     * Create a new Account
     * @param name the name of the account
     * @param holder the User object that holds this account
     * @param bank the Bank that issues the account
     */
    public Account(String name, User holder, Bank bank) {

        // set the account name and holder
        this.name = name;
        this.holder = holder;

        // get a new account UUID
        this.uuid = bank.getNewAccountUUID();

        // init transaction
        this.transactions = new ArrayList<>();
    }

    /**
     * Return the acount UUID
     * @return the uuid
     */
    public String getUUID() {
        return this.uuid;
    }

    /**
     * Get summary line for the account
     * @return the string summary
     */
    public String getSummaryLine() {

        double balance = this.getBalance();

        if (balance >= 0) {
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
        } else {
            return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
        }
    }

    /**
     * Get the balance of this account by adding the amount of the transaction
     * @return the balance value
     */
    public double getBalance() {

        double balance = 0;

        for (Transaction t: this.transactions) {
            balance += t.getAmount();
        }

        return balance;
    }

    /**
     * Print the transaction history of the account
     */
    public void printTransactionHistory() {
        System.out.printf("\nTransaction history for account %s\n", this.uuid);
        for (int i = 0; i < this.transactions.size(); i++) {
            System.out.printf(this.transactions.get(i).getSummaryLine());
        }
        System.out.println();
    }

    /**
     * Add a new transaction in this account
     * @param amount the amount transacted
     * @param memo the transaction memo
     */
    public void addTransaction(double amount, String memo) {
        Transaction transaction = new Transaction(amount, memo, this);
        this.transactions.add(transaction);
    }
}
