package symbolTable;

import java.util.ArrayList;

import exception.CodeGeneratorException;

public class Registers {
    static ArrayList<Character> characters;

    /**
     * Initializes the list of available registers.
     * This method should be called before generating new code to ensure
     * that all registers are available for use.
     */
    public static void init() {
        characters = new ArrayList<>();

        for (char c = 'a'; c <= 'z'; c++)
            characters.add(c);
    }

    /**
     * Resets the list of available registers.
     * This method should be called before generating new code to ensure
     * that all registers are available for use.
     */
    public static char newRegister() throws CodeGeneratorException {
        if (characters.isEmpty())
            throw new CodeGeneratorException("No more registers available");
        return characters.remove(0);
    }
}
