package git.shakibrahman.model.generator;

import git.shakibrahman.model.characters.CharContainer;
import git.shakibrahman.model.characters.KeyArrayPair;
import git.shakibrahman.model.person.Person;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// import org.bouncycastle.jcajce.provider.digest.SHA3;

/**
 * The class that generates the password with the hashing algorithm
 */
public class CoreGenerator {
    // the user for this generator class
    private Person user;

    // the String representation of the Person class for RememberMe feature
    private String remember;

    /**
     * Constructor
     * @param user Person object
     */
    public CoreGenerator(Person user) {
        this.user = user;
    }

    /**
     * Alternate constructor with a string
     * @param remember the byte array representation of the person
     */
    public CoreGenerator(byte[] remember) {
        this.remember = new String(remember);
    }

    /**
     * Create string seed for hashing algorithm
     * @param hashString String name for webservice plus some other characters
     * @return String concatenation of user data and webservice
     */
    private String generateSaltedSeed(String hashString) {
        if (remember == null)
            return this.user.getName() + this.user.getPassword() + this.user.getPin() + hashString;
        else
            return remember + hashString;
    }

    /**
     * Generate a very big number from byte data of salted seed
     * @param hashString The webservice name plus some other characters maybe
     * @return a BigInteger containing the very big number
     * @throws NoSuchAlgorithmException if this is thrown, you're on your own. Good luck!
     */
    private BigInteger generateHash (String hashString) throws NoSuchAlgorithmException {
        String saltedSeed = generateSaltedSeed(hashString);

        // Use these 2 lines if you want to use Java's security module. DOES NOT WORK IN JAVA8
        MessageDigest messageDigest = MessageDigest.getInstance("SHA3-512");
        messageDigest.update(saltedSeed.getBytes(StandardCharsets.UTF_8));

        // Use this if you want to use BouncyCastle's SHA3-512 implementation. REQUIRES BouncyCASTLE
        // SHA3.Digest512 messageDigest = new SHA3.Digest512();
        // messageDigest.update(saltedSeed.getBytes(StandardCharsets.UTF_8));

        byte[] digest = messageDigest.digest();

        return new BigInteger(1, digest);
    }

    /**
     * Takes a very big number and generates an appropriate password with specified length and "key"
     * @param hashString The string representation of the service e.g. Gmail, twitter plus some other characters
     * @param len the length spec of output password
     * @param key a 4-bit switch between 1 (0001) and 15 (1111)
     * @return a String containing the password generated
     */
    private String hashToPass (String hashString, int len, byte key) throws NoSuchAlgorithmException {
        // get hash number
        BigInteger num = generateHash(hashString);

        // get array and number of characters that conform to key-specification
        KeyArrayPair KVPair = CharContainer.getArr(key);
        char[] chars = KVPair.getArr();
        StringBuilder returnStr = new StringBuilder();
        int sizeList = KVPair.getLen();

        // uses modulus to break the hash into array-indexes and concatenate the characters onto StringBuilder
        int x = 0;
        for(BigInteger i = BigInteger.ONE; x < len; i = i.multiply(BigInteger.valueOf(sizeList)), ++x)
            returnStr.append(chars[(num.divide(i)).mod(BigInteger.valueOf(sizeList)).intValue()]);

        return returnStr.toString();
    }

    /**
     * Appends the character 1 to the end of an input string
     * @param seed input string
     * @return input string with an additional '1' character in the end
     */
    private String iterateSeed (String seed) {
        return seed + '1';
    }

    /**
     * Checks if password follows the strict requirements of key
     * @param pass the password generated
     * @param key the key
     * @return true if requirements are met, false otherwise.
     */
    private Boolean passCheck(String pass, byte key) {
        String[] regexArray = {".*[a-z].*", ".*[A-Z].*", ".*[0-9].*",
                ".*[\" !#$%&'()*+,-./:;<=>?@\\[\\\\\\]^_`{|}~].*"};
        int len = regexArray.length;

        for(int i = 0; i < len; ++i) {
            if (((key & 1<<i) != 0) && !pass.matches(regexArray[i]))
                return false;
        }
        return true;
    }

    /**
     * Generates a password until it satisfies key specifications
     * @param service the input webpage name e.g. twitter, git
     * @param passNum the password number. Different nums produce different passwords
     * @param len the length of the password
     * @param key the 4 panel bit switch
     * @return a String password
     * @throws NoSuchAlgorithmException Throws this if SHA3-512 is not available
     */
    public String getPassword(String service, int passNum, int len, byte key) throws NoSuchAlgorithmException {
        String hashString = service + passNum;
        String pass;
        do {
            pass = hashToPass(hashString, len, key);
            hashString = iterateSeed(hashString);
        } while (!passCheck(pass, key));

        return pass;
    }
}
