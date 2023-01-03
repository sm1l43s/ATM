package by.brausov.ATM;

import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Bank bank = new Bank("BNB");

        User user = bank.addUser("Denis", "Brausov", "1234");

        Account account = new Account("Checking", user, bank);

        user.addAccount(account);
        bank.addAccount(account);

        User curUser;

        while (true) {
            curUser = ATM.mainMenuPrompt(bank, scanner);
            ATM.printUserMenu(curUser, scanner);
        }
    }

    private static void printUserMenu(User curUser, Scanner scanner) {

        curUser.printAccountsSummary();

        int choice;

        do {
            System.out.printf("Welcome %s %s\n", curUser.getFirstName(), curUser.getLastName());
            System.out.println("(  1) Show account transaction history");
            System.out.println("(  2) Withdrawal");
            System.out.println("(  3) Deposit");
            System.out.println("(  4) Transfer");
            System.out.println("(  5) Quit");
            System.out.println("\nEnter choice: ");

            choice = scanner.nextInt();
            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Please choice 1-5");
            }

        } while (choice < 1 || choice > 5);

        switch (choice) {
            case 1 -> ATM.showTransHistory(curUser, scanner);
            case 2 -> ATM.withdrawFunds(curUser, scanner);
            case 3 -> ATM.depositFunds(curUser, scanner);
            case 4 -> ATM.transferFunds(curUser, scanner);
        }

        if (choice != 5) {
            ATM.printUserMenu(curUser, scanner);
        }

    }

    /**
     * Process a funds deposit to an account
     * @param curUser the logged-in by.brausov.ATM.User object
     * @param scanner the Scanner object used for user input
     */
    private static void depositFunds(User curUser, Scanner scanner) {
        int toAccount;
        double amount;
        double accountBalance;
        String memo;
        do {
            System.out.println("Enter the number (1-%d) of the account\n to transfer from: ");
            toAccount = scanner.nextInt() - 1;
            if (toAccount < 0 || toAccount >= curUser.numAccounts()) {
                System.out.println("Invalid account/ Please try again.");
            }
        } while (toAccount < 0 || toAccount >= curUser.numAccounts());

        accountBalance = curUser.getAccountBalance(toAccount);

        do {
            System.out.printf("Enter the amount to transfer (max $%.02f) : $", accountBalance);
            amount = scanner.nextDouble();

            if (amount < 0) {
                System.out.println("Amount must be greater by zero.");
            } else if (amount > accountBalance) {
                System.out.println("Amount must be greater than\n");
            }
        } while (amount < 0 || amount > accountBalance);

        System.out.println("Enter a memo");
        memo = scanner.nextLine();

        curUser.addAccountTransaction(toAccount, amount, memo);
    }

    /**
     * Process transferring funds from one account to another
     * @param curUser the logged-in by.brausov.ATM.User object
     * @param scanner the Scanner object used for user input
     */
    private static void transferFunds(User curUser, Scanner scanner) {

        int fromAccount;
        int toAccount;
        double amount;
        double accountBalance;

        do {
            System.out.println("Enter the number (1-%d) of the account\n to transfer from: ");
            fromAccount = scanner.nextInt() - 1;
            if (fromAccount < 0 || fromAccount >= curUser.numAccounts()) {
                System.out.println("Invalid account/ Please try again.");
            }
        } while (fromAccount < 0 || fromAccount >= curUser.numAccounts());

        accountBalance = curUser.getAccountBalance(fromAccount);

        do {
            System.out.println("Enter the number (1-%d) of the account\n to transfer from: ");
            toAccount = scanner.nextInt() - 1;
            if (toAccount < 0 || toAccount >= curUser.numAccounts()) {
                System.out.println("Invalid account/ Please try again.");
            }
        } while (toAccount < 0 || toAccount >= curUser.numAccounts());

        do {
            System.out.printf("Enter the amount to transfer (max $%.02f) : $", accountBalance);
            amount = scanner.nextDouble();

            if (amount < 0) {
                System.out.println("Amount must be greater by zero.");
            } else if (amount > accountBalance) {
                System.out.println("Amount must be greater than\n");
            }
        } while (amount < 0 || amount > accountBalance);

        curUser.addAccountTransaction(fromAccount, -1*amount,
                String.format("Transfer to account %s", curUser.getAccountUUID(toAccount)));
        curUser.addAccountTransaction(toAccount, amount,
                String.format("Transfer to account %s", curUser.getAccountUUID(fromAccount)));
    }

    /**
     * Process a fund withdraw from an account
     * @param curUser the logged-in by.brausov.ATM.User object
     * @param scanner the Scanner object for user input
     */
    private static void withdrawFunds(User curUser, Scanner scanner) {

        int fromAccount;
        double amount;
        double accountBalance;
        String memo;
         do {
            System.out.println("Enter the number (1-%d) of the account\n to transfer from: ");
            fromAccount = scanner.nextInt() - 1;
            if (fromAccount < 0 || fromAccount >= curUser.numAccounts()) {
                System.out.println("Invalid account/ Please try again.");
            }
        } while (fromAccount < 0 || fromAccount >= curUser.numAccounts());

        accountBalance = curUser.getAccountBalance(fromAccount);

        do {
            System.out.printf("Enter the amount to transfer (max $%.02f) : $", accountBalance);
            amount = scanner.nextDouble();

            if (amount < 0) {
                System.out.println("Amount must be greater by zero.");
            } else if (amount > accountBalance) {
                System.out.println("Amount must be greater than\n");
            }
        } while (amount < 0 || amount > accountBalance);

        System.out.println("Enter a memo");
        memo = scanner.nextLine();

        curUser.addAccountTransaction(fromAccount, -1 * amount, memo);
    }

    /**
     * Show the transaction history for an account
     * @param curUser the logged-in by.brausov.ATM.User object
     * @param scanner the Scanner object used for user input
     */
    private static void showTransHistory(User curUser, Scanner scanner) {
        int theAcct;
        do {
            System.out.printf("Enter the number (%d) of the account whose transaction you want see" , curUser.numAccounts());
            theAcct = scanner.nextInt() - 1;
            if (theAcct < 0 || theAcct >= curUser.numAccounts()) {
                System.out.println("Invalid account. Please try again");
            }
        } while (theAcct < 0 || theAcct >= curUser.numAccounts());
        curUser.printAcctTransactionHistory(theAcct);
    }

    private static User mainMenuPrompt(Bank bank, Scanner scanner) {
        String userID;
        String pin;
        User authUser;

        do {
            System.out.printf("\n\nWelcome to %s\n\n", bank.getName());
            System.out.println("Enter use ID: ");
            userID = scanner.nextLine();
            System.out.println("Enter pin: ");
            pin = scanner.nextLine();
            
            authUser = bank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect ID or pin. Please try again");
            }
        } while (authUser == null);

        return authUser;
    }
}
