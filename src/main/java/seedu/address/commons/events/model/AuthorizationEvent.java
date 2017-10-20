package seedu.address.commons.events.model;

import java.util.List;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/** Indicates a request for Google account authorization*/
public class AuthorizationEvent extends BaseEvent {

    private List<ReadOnlyPerson> personList;

    public AuthorizationEvent (List<ReadOnlyPerson> personList) {
        this.personList = personList;
    }

    public List<ReadOnlyPerson> getPersonList() {
        return this.personList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
