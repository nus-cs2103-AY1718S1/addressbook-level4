//@@author tshradheya
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.PopularContactCard;

/**
 * Indicates a selection change in the Popular Contact Panel
 */
public class PopularContactPanelSelectionChangedEvent extends BaseEvent {

    private final PopularContactCard newSelection;
    private final ReadOnlyPerson person;


    public PopularContactPanelSelectionChangedEvent(PopularContactCard newSelection, ReadOnlyPerson person) {
        this.newSelection = newSelection;
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PopularContactCard getNewSelection() {
        return newSelection;
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }
}
