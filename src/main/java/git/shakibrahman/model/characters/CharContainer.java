package git.shakibrahman.model.characters;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * The character container. Used mainly to return an array of any combination of lowercase, uppercase, numeric and
 * symbol characters.
 */
public class CharContainer {
    // Arrays of characters
    private static final char[] lowerArr = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private static final char[] upperArr = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private static final char[] numArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private static final char[] symbolArr = {'"', ' ', '!', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.',
            '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'};

    /**
     * Converts character array to linked list with characters
     * @param c array of characters
     * @return LinkedList of characters
     */
    private static LinkedList<Character> linkedListArr(char[] c) {
        LinkedList<Character> list= new LinkedList<>();
        byte length = (byte) c.length;

        for(byte i=0; i < length; ++i) list.add(c[i]);

        return list;
    }

    /**
     * Returns a LinkedList of a single type of characters by index i, where 0 = lower, 1 = upper, 2 = numeric, and
     * 3 = symbol
     * @param i the "index"
     * @return a linked list of characters
     */
    private static LinkedList<Character> getLLByIndex(byte i) {
        switch (i) {
            case 0:
                return linkedListArr(lowerArr);
            case 1:
                return linkedListArr(upperArr);
            case 2:
                return linkedListArr(numArr);
            case 3:
                return linkedListArr(symbolArr);
            default:
                throw new RuntimeException("getLLByIndex error: argument out of range.");
        }
    }

    /**
     * Returns a KeyArrayPair of (length of character array, character array)
     * @param i the key. 4 bit switch between 1 (0001) and 15 (1111)
     * @return a KeyArrayPair of (length of character array: character array)
     *
     * "But Shakib", I hear you say "surely, you could just calculate the length of the array later when necessary
     * instead of passing it through!". To which I reply: "don't question the  A S C E N D E D  one."
     */
    public static KeyArrayPair getArr(byte i) {
        LinkedList<Character> list= new LinkedList<>();
        byte loopLen = 4;

        for(byte x = 0; x < loopLen; ++x)
            if ((i & 1<<x) != 0) list.addAll(getLLByIndex(x));

        byte arrLen = (byte) list.size();
        char[] charArr = new char[arrLen];
        byte charArrIndex = 0;

        for (char c: list) {
            charArr[charArrIndex] = c;
            ++charArrIndex;
        }

        return new KeyArrayPair(arrLen, charArr);
    }

    // Used for unit testing newCharContainer
    public static void main(String[] args) {
        for(byte i = 1; i < 16; ++i) {
            System.out.println(Integer.toBinaryString(i));
            byte key = getArr(i).getLen();
            System.out.println(key);
            System.out.println(Arrays.toString(getArr(i).getArr()) + "\n");
        }
    }

}
