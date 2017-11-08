package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.UserPerson;

//@@author bladerail
/** Indicates the UserPerson in the model has changed*/
public class UserPersonChangedEvent extends BaseEvent {

    public final UserPerson userPerson;

    public UserPersonChangedEvent(UserPerson data) {
        this.userPerson = data;
    }

    @Override
    public String toString() {
        return "UserPerson changed: " + userPerson.getName();
    }

    public UserPerson getUserPerson() {
        return userPerson;
    }
}
