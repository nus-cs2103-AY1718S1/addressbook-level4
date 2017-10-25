package seedu.address.email.exceptions;

/**
 * Represents an error when an login fails
 */
public class LoginFailedException extends Exception {

    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public LoginFailedException(String message) {
        super(message);
    }
}
