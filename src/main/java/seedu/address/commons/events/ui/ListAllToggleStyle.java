package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to toggle the style of All tab.
 */
public class ListAllToggleStyle extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
