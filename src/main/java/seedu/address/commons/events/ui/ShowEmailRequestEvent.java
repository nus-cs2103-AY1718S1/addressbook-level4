package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author jin-ting
/**
 * An event requesting to view the help page.
 */
public class ShowEmailRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
