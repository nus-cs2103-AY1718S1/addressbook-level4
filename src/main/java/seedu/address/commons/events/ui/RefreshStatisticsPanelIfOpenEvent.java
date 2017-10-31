package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that there is a need to recalculate statistics
 */
public class RefreshStatisticsPanelIfOpenEvent extends BaseEvent {

    public RefreshStatisticsPanelIfOpenEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
