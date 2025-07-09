package exception;

/**
 * Exception class for lexical errors in the source code.
 * This class extends Exception and provides a constructor for creating
 * exceptions with a message.
 */
public class LexicalException extends Exception {

	/**
	 * Constructs a new LexicalException with the specified detail message.
	 *
	 * @param message the detail message
	 */
	public LexicalException(String message) {
		super(message);
	}
}
