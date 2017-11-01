package seedu.address.commons.events.ui;

//@@author LeeYingZheng
import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the Facebook Log In page.
 */
public class ShowFacebookRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
