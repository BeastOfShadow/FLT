package symbolTable;

import ast.LangType;

public class Attributes {
    private LangType type;
    private String name;
    private char register;

    public Attributes(LangType type, String name, char register) {
        this.type = type;
        this.name = name;
        this.register = register;
    }

    public LangType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public char getRegister() {
        return register;
    }
}
