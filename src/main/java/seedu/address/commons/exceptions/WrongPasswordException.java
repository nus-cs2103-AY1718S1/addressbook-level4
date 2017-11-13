package seedu.address.commons.exceptions;

/**
 * Signals that the given Password do not match the existing one.
 */
public class WrongPasswordException extends Exception {

    private static final String MESSAGE_WRONG_PASSWORD = "Wrong Password";

    public WrongPasswordException() {
        super(MESSAGE_WRONG_PASSWORD);
    }

    /**
     * @param cause   of the main exception
     */
    public WrongPasswordException(Throwable cause) {
        super(MESSAGE_WRONG_PASSWORD, cause);
    }
}
