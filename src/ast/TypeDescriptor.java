package ast;

/**
 * Class representing a type descriptor in the AST.
 * It contains information about the type, error message, and line number.
 */
public class TypeDescriptor {
    private TypeTd type;
    private String message;
    private int row;

    /**
     * Checks compatibility between this type descriptor and another.
     * Should be overridden in subclasses to implement actual type checking.
     *
     * @param typeD the other TypeDescriptor to compare with
     * @return true if compatible (default: always true)
     */
    public boolean compatibility(TypeDescriptor typeD) {
        return this.getType() == TypeTd.FLOAT && typeD.getType() == TypeTd.INT ? false : true;
    }

    /**
     * Constructor for TypeDescriptor with error information.
     *
     * @param type    the type of this descriptor
     * @param message the error message
     * @param row     the line number associated with the type
     */
    public TypeDescriptor(TypeTd type, String message, int row) {
        this.type = type;
        this.message = message;
        this.row = row;
    }

    /**
     * Constructor for TypeDescriptor without error information.
     *
     * @param type the type of this descriptor
     */
    public TypeDescriptor(TypeTd type) {
        this.type = type;
    }

    /**
     * Returns the type of this descriptor.
     *
     * @return the type
     */
    public TypeTd getType() {
        return type;
    }

    /**
     * Returns the error message associated with this descriptor, if any.
     *
     * @return the error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the line number where this descriptor is located.
     *
     * @return the line number
     */
    public int getRow() {
        return row;
    }
}
