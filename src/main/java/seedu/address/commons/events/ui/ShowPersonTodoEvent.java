//@@author Hailinx
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Indicates a request for displaying todoItems of given person
 */
public class ShowPersonTodoEvent extends BaseEvent {

    public final ReadOnlyPerson person;

    public ShowPersonTodoEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
