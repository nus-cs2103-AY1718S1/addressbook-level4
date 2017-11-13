package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author yewshengkai
/**
 * An event requesting to view the About page.
 */
public class ShowAboutRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
