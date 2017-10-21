package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.UserPerson;

/** Indicates the UserProfileManager in the model has changed*/
public class UserPersonChangedEvent extends BaseEvent {

    public final UserPerson data;

    public UserPersonChangedEvent(UserPerson data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserPerson changed: " + data.getName();
    }
}
