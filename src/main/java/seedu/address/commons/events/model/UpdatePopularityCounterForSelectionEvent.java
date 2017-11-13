//@@author tshradheya
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Updates the Popularity Counter by one for the selected person
 * Guarantees: Some person is selected in UI in person List Panel
 */
public class UpdatePopularityCounterForSelectionEvent extends BaseEvent {

    private ReadOnlyPerson person;

    public UpdatePopularityCounterForSelectionEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
