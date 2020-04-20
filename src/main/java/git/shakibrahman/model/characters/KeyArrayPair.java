package git.shakibrahman.model.characters;

/**
 * Because Oracle cannot be bothered to let Java have a pair object
 * I'm not mad I'm just disappointed.
 */
public class KeyArrayPair {
    // Length of the array. It is pre-calculated to avoid errors
    private byte len;

    // The character array itself
    private char[] arr;

    /**
     * Creates a KeyArrayPair, taking them as arguments
     * @param len the length
     * @param arr the character array
     */
    public KeyArrayPair(byte len, char[] arr) {
        this.len = len;
        this.arr = arr;
    }

    /**
     * Getter for the length
     * @return the length
     */
    public byte getLen() {
        return this.len;
    }

    /**
     * Getter for the array
     * @return the array
     */
    public char[] getArr() {
        return this.arr;
    }
}
