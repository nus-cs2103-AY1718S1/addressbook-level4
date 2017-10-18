package seedu.address.email.exceptions;

public class EmailSendFailedException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public EmailSendFailedException(String message) {
        super(message);
    }
}
