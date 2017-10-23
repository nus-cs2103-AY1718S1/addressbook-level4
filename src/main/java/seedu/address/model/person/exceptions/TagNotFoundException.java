package seedu.address.model.person.exceptions;


/**
 * Signal that the operation is not able to find the specified tag.
 */
public class TagNotFoundException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public TagNotFoundException(String message) {
        super(message);
    }
}
