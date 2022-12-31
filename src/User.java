import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

    /**
    * The first name of the user.
    */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;

    /**
     * The ID number of the user.
     */
    private String uuid;

    /**
     * The MD5 hash of the user's pin.
     */
    private byte[] pinHash;

    /**
     * The list of accounts for this user.
     */
    private ArrayList<Account> accounts;

    /**
     * Create a new user
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param pin the user's account pin number
     * @param bank the Bank object that user is a customer of
     */
    public User(String firstName, String lastName, String pin, Bank bank) {
        //set user's name
        this.firstName = firstName;
        this.lastName = lastName;

        // store the pin's MD5 hash, rather  than original value, for
        // security reasons
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        // get a new, unique universal ID for the user
        this.uuid = bank.getNewUserUUID();

        // create empty list accounts
        this.accounts = new ArrayList<>();

        // print log message
        System.out.printf("New user %s, %s with ID %s created\n", lastName, firstName, this.uuid);
    }

    /**
     * Return the user's first name
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Return the user's last name
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Add new Acoount for the User
     * @param account the account to add
     */
    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    /**
     * Return the user's UUID
     * @return the uuid
     */
    public String getUUID() {
    return this.uuid;
    }

    /**
     * Check whether a given pin matches the true User pin
     * @param pin the pin to check
     * @return whether the pin is valid or not
     */
    public boolean validatePin(String pin) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Print summaries for the accounts of this user
     */
    public void printAccountsSumary() {

        System.out.printf("\n\n%s accounts summary\n", this.firstName);
        for (int i = 0; i < this.accounts.size(); i++) {
            System.out.printf("(%d) %s \n", i + 1, this.accounts.get(i).getSummaryLine());
        }

    }

    /**
     * Get the of accounts of the user
     * @return the number of accounts
     */
    public int numAccounts() {
        return this.accounts.size();
    }

    /**
     * Print transaction history for a particular account
     * @param theAcctIndex the index of the account to use
     */
    public void printAcctTransactionHistory(int theAcctIndex) {
        this.accounts.get(theAcctIndex).printTransactionHistory();
    }

    /**
     * Get the balance of a particular account
     * @param fromAccountIndex the index of the account to use
     * @return the balance of the account
     */
    public double getAccountBalance(int fromAccountIndex) {
        return this.accounts.get(fromAccountIndex).getBalance();
    }

    /**
     * Get the UUID of a particular account
     * @param accountIndex the index of the account to use
     * @return the UUID of the account
     */
    public String getAccountUUID(int accountIndex) {
        return this.accounts.get(accountIndex).getUUID();
    }

    /**
     * Add a transaction to a particular account
     * @param accountIndex the index of account
     * @param amount the amount of the transaction
     * @param memo the memo of the transaction
     */
    public void addAccountTransaction(int accountIndex, double amount, String memo) {
        this.accounts.get(accountIndex).addTransaction(amount, memo);
    }
}
