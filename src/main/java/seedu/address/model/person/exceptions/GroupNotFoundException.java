package seedu.address.model.person.exceptions;

public class GroupNotFoundException extends Exception {
    public GroupNotFoundException () {
        super("Group not in addressbook");
    }
}
