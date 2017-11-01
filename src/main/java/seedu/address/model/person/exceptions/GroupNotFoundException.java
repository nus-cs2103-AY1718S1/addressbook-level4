package seedu.address.model.person.exceptions;

/**
 * Exception for group not existing in the unique group list
 */

public class GroupNotFoundException extends Exception {
    public GroupNotFoundException () {
        super("Group not in addressbook");
    }
}
