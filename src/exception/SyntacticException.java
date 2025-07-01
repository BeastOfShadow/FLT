package exception;

public class SyntacticException extends Exception {
    public SyntacticException(String message) {
        super(message);
    }

    public SyntacticException(String message, Throwable e) {
        super(message, e);
    }
}
