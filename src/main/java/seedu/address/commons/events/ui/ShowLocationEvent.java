//@@author tshradheya
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Indicates 'location' command's successful execution
 * Trigger's event to show the address of person in `BrowserPanel`
 */
public class ShowLocationEvent extends BaseEvent {

    public final ReadOnlyPerson person;

    public ShowLocationEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
