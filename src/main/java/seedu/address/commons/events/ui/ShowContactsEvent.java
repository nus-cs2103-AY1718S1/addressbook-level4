package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author huyuanrong
/**
 * An event requesting to view the contacts page.
 */
public class ShowContactsEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
