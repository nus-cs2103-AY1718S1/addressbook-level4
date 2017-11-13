package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Indicates the AddressBook in the model has changed
 */
public class PersonChangedEvent extends BaseEvent {

    /**
     * Represents the change type of the person
     */
    public enum ChangeType {
        ADD ("added"),
        EDIT ("edited"),
        DELETE ("deleted");

        private final String action;   // in kilograms

        ChangeType(String action) {
            this.action = action;
        }
    }

    public final ReadOnlyPerson person;
    public final ChangeType type;
    public final UserPrefs prefs;

    public PersonChangedEvent(ReadOnlyPerson person, ChangeType type, UserPrefs prefs) {
        this.person = person;
        this.type = type;
        this.prefs = prefs;
    }

    @Override
    public String toString() {
        return String.format("Person %1$s has been %2$s.", person.getName().fullName, type.action);
    }
}
