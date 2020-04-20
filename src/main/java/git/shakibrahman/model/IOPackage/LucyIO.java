package git.shakibrahman.model.IOPackage;

import git.shakibrahman.model.generator.CoreGenerator;
import git.shakibrahman.model.person.Person;

import java.io.*;

/**
 * Provides IO related functions.
 */
public class LucyIO {
    // the input file name and other information
    private final File inputFile = new File("LucyBinary");

    // an ioObject. Can remain unused
    private IOObject ioObject;

    /**
     * Helper function for writing IOObject type out to the file
     * @param ioObject an ioObject. I'm sorry what do you want from me?
     */
    private void writeToFile(IOObject ioObject) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.inputFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(ioObject);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException ignored) {}
    }

    /**
     * Writes person information into binary file for later use
     * @param p the person object to be remembered
     */
    public void rememberMe(Person p) {
        byte[] bytes = (p.getName() + p.getPassword() + p.getPin()).getBytes();
        IOObject ioObject = new IOObject(bytes, true);
        writeToFile(ioObject);
    }

    /**
     * Checks if the user data is available.
     * If it is: writes the data to this.ioObject and returns true
     * Else: returns false
     * @return a boolean that answers "Is there saved information?"
     */
    public boolean startUpCheck() {
        if (!this.inputFile.exists()) {
            return false;
        } else {
            try {
            FileInputStream fileInputStream = new FileInputStream(this.inputFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            IOObject ioObject = (IOObject) objectInputStream.readObject();
            this.ioObject = ioObject;
            objectInputStream.close();
            fileInputStream.close();
            return ioObject.getABoolean();
            } catch (IOException | ClassNotFoundException e) {
                return false;
            }
        }
    }

    /**
     * Returns a CoreGenerator object from stored information
     * @return a CoreGenerator object
     */
    public CoreGenerator getGenerator() {
        return new CoreGenerator(ioObject.getBytes());
    }

    /**
     * Erases stored information, effectively logging the user out.
     */
    public void logOut() {
        byte[] bytes = {};
        IOObject ioObject = new IOObject(bytes, false);
        writeToFile(ioObject);
    }
}
