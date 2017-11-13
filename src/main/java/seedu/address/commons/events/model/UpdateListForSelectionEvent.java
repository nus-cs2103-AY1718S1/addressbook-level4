//@@author tshradheya
package seedu.address.commons.events.model;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.ui.ShowDefaultPanelEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Updates Person List Panel list for selection to match the Favourites Panel
 */
public class UpdateListForSelectionEvent extends BaseEvent {

    private static final Logger logger = LogsCenter.getLogger(ShowDefaultPanelEvent.class);

    private Index index;
    private ReadOnlyPerson person;

    public UpdateListForSelectionEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    public Index getIndex() {
        return index;
    }

    public void setIndex(Index index) {
        this.index = index;
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }

    @Override
    public String toString() {
        logger.info("List will get updated to show all contacts");
        return this.getClass().getSimpleName();
    }

}
