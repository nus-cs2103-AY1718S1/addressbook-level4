package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/** Indicates the Statistics Panel should be swapped in */
public class ToggleStatisticsPanelEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
