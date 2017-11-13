//@@author tshradheya
package seedu.address.commons.events.ui;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates selection of a Person Card according to index specified and updates selection
 */
public class UpdatePersonListPanelSelectionEvent extends BaseEvent {

    private static final Logger logger = LogsCenter.getLogger(ShowDefaultPanelEvent.class);

    private Index index;

    public UpdatePersonListPanelSelectionEvent(Index index) {
        this.index = index;
    }

    public Index getIndex() {
        return index;
    }

    @Override
    public String toString() {
        logger.info("Specified index will get selected");
        return this.getClass().getSimpleName();
    }
}
