package git.shakibrahman.model.IOPackage;

import java.io.Serializable;

/**
 * An object that stores key information for the remmeber me feature
 */
public class IOObject implements Serializable {
    // boolean that represents if readable information is present
    private boolean aBoolean;

    // a byte array represented in floats for obfuscation
    // an int/byte array is easily readable by humans from the binary file. This prevents that
    private float[] floats;

    /**
     * Constructor for IOObject. Takes in a byte array and a boolean
     * @param bytes a byte array that represents a string
     * @param aBoolean a boolean that represents if information is stored
     */
    public IOObject(byte[] bytes, boolean aBoolean) {
        int byteLen = bytes.length;
        floats = new float[byteLen];
        for(int i = 0; i < byteLen; ++i) {
            this.floats[i] = bytes[i];
        }
        this.aBoolean = aBoolean;
    }

    /**
     * Getter method. Extracts an array of bytes from the stored float array
     * @return an array of bytes
     */
    public byte[] getBytes() {
        int byteLen = this.floats.length;
        byte[] bytes = new byte[byteLen];
        for (int i = 0; i < byteLen; ++i) {
            bytes[i] = (byte) this.floats[i];
        }
        return bytes;
    }

    /**
     * getter method for the boolean
     * @return the stored boolean: this.aBoolean
     */
    public boolean getABoolean() {
        return this.aBoolean;
    }
}
