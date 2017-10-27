package seedu.address.model.person.exceptions;

/**
 * Signal that the operation is not able to find the specified relationship.
 */
public class RelationshipNotFoundException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public RelationshipNotFoundException(String message) {
        super(message);
    }
}
