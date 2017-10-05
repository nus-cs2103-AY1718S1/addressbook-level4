package seedu.address.model.person.exceptions;

public class TagNotFoundException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public TagNotFoundException(String message) {
        super(message);
    }
}
