package git.shakibrahman.model.person;

/**
 * Person class. Makes person object. This application is for people only. Sorry.
 */
public class Person {
    // name of person
    private String name;

    // person's master password
    private String masterPassword;

    // person's PIN
    private int pin;

    /**
     * Constructor for when you only have a name for the person
     * @param name String representation of name of the person
     */
    public Person (String name) {
        this.name = name;
    }

    /**
     * Alternative constructor that you add for when you're too lazy to use getters and setters in a test driver
     * @param name String name of person
     * @param masterPassword String masterPassword of person
     * @param pin int PIN of person
     */
    public Person (String name, String masterPassword, int pin) {
        this.name = name;
        this.masterPassword = masterPassword;
        this.pin = pin;
    }

    /**
     * Setter for masterPassword
     */
    public void setPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    /**
     * Setter for pin
     */
    public void setPin(int pin) {
        this.pin = pin;
    }

    /**
     * Getter for person name
     * @return String name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for person password. Yes it's that easy. No furious typing hackerman sequence necessary.
     * @return String masterPassword
     */
    public String getPassword() {
        return this.masterPassword;
    }

    /**
     * Getter for person PIN.
     * Imagine documenting getters and setters.
     * @return int pin of person
     */
    public int getPin() {
        return this.pin;
    }
}
