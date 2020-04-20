package git.shakibrahman.model.utils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * Static classes for "global functions" since Java has no support for those
 */
public class LucyUtils {

    /**
     * Copies string s to system clipboard
     * @param s a string to be copied to clipboard
     */
    public static void copyText(String s) {
        StringSelection stringSelection = new StringSelection(s);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
    }

    /**
     * Translates the boolean from checkboxes to the byte key
     * @param lowercase Boolean for when lowercase box is ticked
     * @param uppercase Boolean for when uppercase box is ticked
     * @param numeric Boolean for when numeric box is ticked
     * @param symbol Boolean for when symbol box is ticked
     * @return a byte key
     */
    public static byte boolToKey(Boolean lowercase, Boolean uppercase, Boolean numeric, Boolean symbol) {
        boolean[] boolArray = {lowercase, uppercase, numeric, symbol};
        int arrLen = 4;
        byte key = 0;

        for (int i = 0; i < arrLen; ++i)
            if (boolArray[i]) key |= 1<<i;

        return key;
    }

    /**
     * Testing boolToKey
     */
    public static void main(String[] args) {
        boolean[] boolArr= {true, false};
        for (boolean w:boolArr) {
            for (boolean x:boolArr) {
                for (boolean y: boolArr) {
                    for (boolean z: boolArr) {
                        System.out.println("Booleans:\nLower: " + w + "\nUpper:" + x + "\nNum: " + y +
                                "\nSymbol: " + z);
                        System.out.println("Key: " + boolToKey(w, x, y, z));
                        System.out.print('\n');
                    }
                }
            }
        }
    }

}
