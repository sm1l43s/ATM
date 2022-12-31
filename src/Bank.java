import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private final String name;

    private final ArrayList<User> users;

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

        // init
        StringBuilder uuid;
        Random random = new Random();
        int length = 6;
        boolean nonUnique;

        // continue looping until we get a unique ID
        do {

            // generate the number
            uuid = new StringBuilder();
            for (int i = 0; i < length; i++) {
                uuid.append(((Integer) random.nextInt(10)));
            }

            // check to make sue it's unique
            nonUnique = false;
            for (User u: users) {
                if (uuid.toString().compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);

        return uuid.toString();
    }

    /**
     * Generate a new universally unique ID for an account
     * @return the uuid
     */
    public String getNewAccountUUID() {
        // init
        StringBuilder uuid;
        Random random = new Random();
        int length = 10;
        boolean nonUnique;

        // continue looping until we get a unique ID
        do {

            // generate the number
            uuid = new StringBuilder();
            for (int i = 0; i < length; i++) {
                uuid.append(((Integer) random.nextInt(10)));
            }

            // check to make sue it's unique
            nonUnique = false;
            for (Account a: accounts) {
                if (uuid.toString().compareTo(a.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);

        return uuid.toString();
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
