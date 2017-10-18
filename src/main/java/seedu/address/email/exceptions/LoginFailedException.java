package seedu.address.email.exceptions;

public class LoginFailedException extends Exception {

    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public LoginFailedException(String message) {
        super(message);
    }
}
