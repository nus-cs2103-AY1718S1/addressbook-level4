package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author LuLechuan
/**
 * An event requesting to view the yahoo weather page.
 */
public class ShowWeatherRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
