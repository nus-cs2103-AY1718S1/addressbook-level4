package seedu.address.commons.events.ui;

import java.util.List;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Indicates a request to jump to the list of persons
 */
//@@author sidhmads
public class FindLocationRequestEvent extends BaseEvent {

    public final List<ReadOnlyPerson> targetPersons;

    public FindLocationRequestEvent(List<ReadOnlyPerson> targetPersons) {
        this.targetPersons = targetPersons;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
