package exception;

/**
 * Exception class for errors encountered during code generation.
 * This class extends Exception and provides constructors for creating
 * exceptions with a message and an optional cause.
 */
public class CodeGeneratorException extends Exception {
    /**
     * Constructs a new CodeGeneratorException with the specified detail message.
     *
     * @param message the detail message
     */
    public CodeGeneratorException(String message) {
        super(message);
    }

    /**
     * Constructs a new CodeGeneratorException with the specified detail message
     * and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public CodeGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }
}
