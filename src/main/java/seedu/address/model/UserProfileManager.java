package seedu.address.model;

import java.util.Objects;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.events.model.UserPersonChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.UserPerson;
/**
 * Represents the user's Profile in the address book.
 *
 */
public class UserProfileManager extends ComponentManager {
    private final UserPerson userPerson;

    public UserProfileManager() throws IllegalValueException {
        this.userPerson = new UserPerson();
    }

    /** Raises an event to indicate the model has changed */
    private void indicateUserPersonChanged() {
        raise(new UserPersonChangedEvent(userPerson));
    }

    /**
     * Updates the UserPerson to another UserPerson
     *
     */
    public void updateUserPerson(UserPerson target) {
        userPerson.update(target);
        indicateUserPersonChanged();
    }

    @Override
    public int hashCode() {
        return Objects.hash(userPerson);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : ");
        sb.append("\nLocal data file location : ");
        sb.append("\nAddressBook name : ");
        return sb.toString();
    }
}
