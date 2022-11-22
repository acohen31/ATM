import java.util.Scanner;

public class ATM {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank theBank = new Bank("Bank of Cohen");
        User aUser = theBank.addUser("Aviv", "Cohen", "1234");
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);
        
        User currentUser;
        while (true) {
            //Stay in main menu until login is successful 
            currentUser = ATM.mainMenuPrompt(theBank, sc);
            //Stay in main menu until user quits
            ATM.printUserMenu(currentUser, sc);
        }
    }
    /**
     * Login Prompt
     * @param theBank   The bank being logged into
     * @param sc        Scanner of System.in
     * @return          authenticated user
     */
    public static User mainMenuPrompt(Bank theBank, Scanner sc) {
        String userID;
        String pin;
        User authUser;
        // Prompt user for ID and Pin until it is correct
        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userID = sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();
            
            // Try to find user that has this ID and pin.
            authUser = theBank.userLogin(userID,  pin);
            if (authUser == null) {
                System.out.println("Incorrect user ID/pin. " + "Please try again");
            }
        }while(authUser == null);
        return authUser;
    }
    /**
     * User Interface
     * @param theUser   The logged in User object
     * @param sc        Scanner of System.in
     */
    public static void printUserMenu(User theUser, Scanner sc) {
        theUser.printAccountSummary();
        int choice;
        do {
            System.out.println("What would you like to do today? \n");
            System.out.println("    1) Show account transaction history");
            System.out.println("    2) Withdraw");
            System.out.println("    3) Deposit");
            System.out.println("    4) Transfer");
            System.out.println("    5) Quit\n");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            if(choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Choose from 1-5");
            }
        }while (choice < 1 || choice > 5);
        //Process choice
        switch(choice) {
        case 1:
            ATM.showTransHistory(theUser, sc);
            break;
        case 2:
            ATM.withdrawFunds(theUser, sc);
            break;
        case 3:
            ATM.depositFunds(theUser,sc);
            break;
        case 4:
            ATM.transferFunds(theUser,sc);
            break;
        case 5:
            sc.nextLine();
            break;
        }
        //redisplay menu unless user quits
        if(choice!=5) {
            ATM.printUserMenu(theUser, sc);
        }
    }
    /**
     * Transaction history of an account tied to the user
     * @param theUser   Logged in user object
     * @param sc        Scanner of System.in      
     */
    public static void showTransHistory(User theUser, Scanner sc) {
        int theAccount;
        // get account to look at
        do {
            System.out.printf("Enter the number (1-%d) of the account who's transactions you want to see: ", 
                    theUser.numAccounts());
            theAccount = sc.nextInt() - 1;
            if(theAccount < 0 || theAccount >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        }while(theAccount < 0 || theAccount >= theUser.numAccounts());
        theUser.printAccountTransHistory(theAccount);
    }
    /**
     * Transfer funds from one account to another
     * @param theUser   The logged in User object
     * @param sc        Scanner of System.in
     */
    public static void transferFunds(User theUser, Scanner sc) {
        
        int fromAccount;
        int toAccount;
        double amount;
        double accountBalance;
        
        // Account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer to: ", theUser.numAccounts());
            fromAccount = sc.nextInt()-1;
            if (fromAccount < 0 || fromAccount >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        }while(fromAccount < 0 || fromAccount >= theUser.numAccounts());
        accountBalance = theUser.getAccountBalance(fromAccount);
        
        // Account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer from: ", theUser.numAccounts());
            toAccount = sc.nextInt()-1;
            if (toAccount < 0 || toAccount >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        }while(toAccount < 0 || toAccount >= theUser.numAccounts());
        accountBalance = theUser.getAccountBalance(toAccount);
        
        // Get amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $", 
                    accountBalance);
            amount = sc.nextDouble();
            if(amount < 0) {
                System.out.println("Cannot enter negative amount");
            }else if(amount > accountBalance) {
                System.out.printf("Amount is greater than account balance of $%.02f \n", accountBalance);
            }
        }while (amount < 0 || amount > accountBalance);
        
        // Transfer
        theUser.addAccountTransaction(fromAccount, amount, String.format(
                "Transfer to account %s", theUser.getAccountUUID(toAccount)));
        theUser.addAccountTransaction(toAccount, -1*amount, String.format(
                "Transfer to account %s", theUser.getAccountUUID(toAccount)));
    }
    /**
     * Withdraw funds from an account
     * @param theUser   Logged in User object
     * @param sc        Scanner of System.in
     */
    public static void withdrawFunds(User theUser, Scanner sc) {
        
        int fromAccount;
        double amount;
        double accountBalance;
        String memo;
        
        // Account to withdraw from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to withdraw from: ", theUser.numAccounts());
            fromAccount = sc.nextInt()-1;
            if (fromAccount < 0 || fromAccount >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        }while(fromAccount < 0 || fromAccount >= theUser.numAccounts());
        accountBalance = theUser.getAccountBalance(fromAccount);
        // Get amount to withdraw
        do {
            System.out.printf("Enter the amount to withdraw (max $%.02f): $", 
                    accountBalance);
            amount = sc.nextDouble();
            if(amount < 0) {
                System.out.println("Cannot enter negative amount");
            }else if(amount > accountBalance) {
                System.out.printf("Amount is greater than account balance of $%.02f \n", accountBalance);
            }
        }while (amount < 0 || amount > accountBalance);
        sc.nextLine();
        // Get memo
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();
        
        // The withdraw
        theUser.addAccountTransaction(fromAccount, -1*amount, memo);
    }
    /**
     * Deposit funds into an account
     * @param theUser   the logged in User object
     * @param sc        Scanner of System.in
     */
    public static void depositFunds(User theUser, Scanner sc) {
        int toAccount;
        double amount;
        double accountBalance;
        String memo;
        
        // Account to withdraw from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to deposit in: ", theUser.numAccounts());
            toAccount = sc.nextInt()-1;
            if (toAccount < 0 || toAccount >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        }while(toAccount < 0 || toAccount >= theUser.numAccounts());
        accountBalance = theUser.getAccountBalance(toAccount);
        // Get amount to withdraw
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $", 
                    accountBalance);
            amount = sc.nextDouble();
            if(amount < 0)
                System.out.println("Cannot enter negative amount");            
        }while (amount < 0);
        sc.nextLine();
        // Get memo
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();
        
        // The withdraw
        theUser.addAccountTransaction(toAccount, amount, memo);
    }
}// Class ATM
