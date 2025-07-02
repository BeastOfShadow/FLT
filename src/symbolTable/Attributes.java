package symbolTable;

import ast.LangType;

public class Attributes {
    private LangType type;
    private String name;
    private char register;

    /**
     * Constructor for Attributes.
     * Initializes the type and name of the attribute.
     * 
     * @param type the type of the attribute
     * @param name the name of the attribute
     */
    public Attributes(LangType type, String name) {
        this.type = type;
        this.name = name;

    }

    /**
     * Constructor for Attributes with a register.
     * Initializes the type, name, and register of the attribute.
     * 
     * @param type     the type of the attribute
     * @param name     the name of the attribute
     * @param register the register character for the attribute
     */
    public LangType getType() {
        return type;
    }

    /**
     * Sets the type of this attribute.
     * 
     * @param type the LangType to set
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the type of this attribute.
     * 
     * @param type the LangType to set
     */
    public char getRegister() {
        return register;
    }

    /**
     * Sets the register for this attribute.
     * 
     * @param registro the register character to set
     */
    public void setRegister(char registro) {
        this.register = registro;
    }
}
