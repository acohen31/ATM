import java.util.ArrayList;
import java.util.Random;

public class Bank {
    /**
     * Name of bank.
     */
    private String name;
    /**
     * List of user's tied to the bank.
     */
    private ArrayList<User> users;
    /**
     * List of accounts tied to the bank.
     */
    private ArrayList<Account> accounts;  
    /**
     * New bank object with empty list of users and accounts.
     * @param name  Bank's name.
     */
    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }
    /**
     * Generate UUI for user
     * @return  uuid
     */
    public String getNewUserUUID() {
        String uuid;
        Random rng = new Random();
        int length = 9;
        boolean nonUnique = false;
        // Generate uuid until it is unique
        do {
            uuid = "";
            for(int i = 0; i < length; i++) {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            nonUnique = false;
            for(User i: this.users) {
                if(uuid.compareTo(i.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while(nonUnique);
        return uuid;
    }
    /**
     * Generate account UUID
     * @return uuid
     */
    public String getNewAccountUUID() {
        String uuid;
        Random rng = new Random();
        int length = 12;
        boolean nonUnique = false;
        //Generate uuid until it is unique
        do {
            uuid = "";
            for(int i = 0; i < length; i++) {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            nonUnique = false;
            for(Account i: this.accounts) {
                if(uuid.compareTo(i.getUUID()) ==0) {
                    nonUnique = true;
                    break;
                }
            }
        } while(nonUnique);
        return uuid;
    }
    /**
     * Add an account
     * @param anAccount the account being added
     */
    public void addAccount(Account anAccount) {
        this.accounts.add(anAccount);
    }
    /**
     * Add a new user to the bank
     * @param firstName user's first name.
     * @param lastName  user's last name.
     * @param pin       user's pin.
     * @return          newUser object.
     */
    public User addUser(String firstName, String lastName, String pin) {
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);
        // Create savings accounts
        Account newAccount = new Account("Savings", newUser, this);
        // Add this account to holder and bank lists
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);
        return newUser;
    }
    /**
     * Get user object associated with a user ID and pin if valid.
     * @param userID    UUID of the user logging in.
     * @param pin       pin of the user.
     * @return          User object if valid, null if not valid.
     */
    public User userLogin(String userID, String pin) {
        for(User i : this.users) {
            if(i.getUUID().compareTo(userID) == 0 && i.validatePin(pin)) {
                return i;
            }
        }
        return null;
    }
    /**
     * Bank get Name
     * @return  name of bank
     */
    public String getName() {
        return this.name;
    }
}// Class Bank
