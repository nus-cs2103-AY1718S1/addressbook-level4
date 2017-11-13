package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author junming403
/**
 * Indicates the listingUnit in the personListPanel is changed.
 */
public class RefreshPanelEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
