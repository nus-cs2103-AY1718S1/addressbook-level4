package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Indicates the User Preference has changed
 */
public class ProfilePhotoChangedEvent extends BaseEvent {

    private ReadOnlyPerson person;

    public ProfilePhotoChangedEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return String.format("The profile photo of %1$s has been updated.", person.getName().fullName);
    }
}
