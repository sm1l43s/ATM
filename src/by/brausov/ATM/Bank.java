package by.brausov.ATM;

import java.util.ArrayList;
import java.util.Random;
public class Bank {

    /**
     * The name of bank.
     */
    private final String name;

    /**
     * The list of user's for this bank.
     */
    private final ArrayList<User> users;

    /**
     * The list of accounts for this bank
     */
    private final ArrayList<Account> accounts;

    /**
     * Create a new Bank object with empty lists of users and accounts
     * @param name the bank's name
     */
    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }

    /**
     * Generate a new universally unique ID for a user.
     * @return the uuid
     */
    public String getNewUserUUID() {
        String uuid;
        boolean nonUnique;

        do {
            uuid = generateUUID(6);
            nonUnique = false;

            for (User u: users) {
                if (uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);

        return uuid;
    }

    /**
     * Generate a new universally unique ID for an account
     * @return the uuid
     */
    public String getNewAccountUUID() {
        String uuid;
        boolean nonUnique;

        do {
            uuid = generateUUID(10);
            nonUnique = false;
            for (Account a: accounts) {
                if (uuid.compareTo(a.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);

        return uuid;
    }

    /**
     * Generate a new universally unique ID
     * @param length the length of unique ID
     * @return the uuid
     */
    private String generateUUID(int length) {
        StringBuilder uuid = new StringBuilder();
        for (int i = 0; i < length; i++) {
            uuid.append(((Integer) new Random().nextInt(10)));
        }
        return String.valueOf(uuid);
    }

    /**
     * Add an Account
     * @param account the account to add
     */
    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    /**
     * Create a new @User of the Bank
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param pin the user's pin
     * @return the new User object
     */
    public User addUser(String firstName, String lastName, String pin) {

        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    /**
     * Get logged-in user object
     * @param userID the userID for login
     * @param pin the pin for login
     * @return the logged-in user
     */
    public User userLogin(String userID, String pin) {
        for (User u: this.users) {
            if ( u.getUUID().compareTo(userID) == 0 &&
                 u.validatePin(pin)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Get the name of the bank
     * @return the name of the bank
     */
    public String getName() {
        return name;
    }
}
