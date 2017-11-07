package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author aungmyin23
/**
 * An event requesting to view the psi webpage.
 */
public class ShowPsiRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
