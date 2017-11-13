//@@author jshoung
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the venue page.
 */
public class VenueRequestEvent extends BaseEvent {
    public final String venue;

    public VenueRequestEvent(String venue) {
        this.venue = venue;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
