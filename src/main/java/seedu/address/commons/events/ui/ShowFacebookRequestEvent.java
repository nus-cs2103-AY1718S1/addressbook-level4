package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author taojiashu
/**
 * Indicates a request to search a name in Facebook.
 */
public class ShowFacebookRequestEvent extends BaseEvent {

    public final String name;

    public ShowFacebookRequestEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
