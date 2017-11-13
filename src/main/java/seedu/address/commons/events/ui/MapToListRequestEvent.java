package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author yewshengkai
/**
 * Indicates a request to jump to the list of persons
 */
public class MapToListRequestEvent extends BaseEvent {

    public final ReadOnlyPerson targetPerson;

    public MapToListRequestEvent(ReadOnlyPerson targetPerson) {
        this.targetPerson = targetPerson;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
