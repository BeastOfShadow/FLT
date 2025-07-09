package exception;

/**
 * Exception class for syntactic errors in the source code.
 * This class extends Exception and provides constructors for creating
 * exceptions with a message and an optional cause.
 */
public class SyntacticException extends Exception {
    /**
     * Constructs a new SyntacticException with the specified detail message.
     *
     * @param message the detail message
     */
    public SyntacticException(String message) {
        super(message);
    }

    /**
     * Constructs a new SyntacticException with the specified detail message
     * and cause.
     *
     * @param message the detail message
     * @param e       the cause of the exception
     */
    public SyntacticException(String message, Throwable e) {
        super(message, e);
    }
}
