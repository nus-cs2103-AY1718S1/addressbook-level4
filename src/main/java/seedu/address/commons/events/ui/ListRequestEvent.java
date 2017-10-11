package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request for the list of contacts
 */
public class ListRequestEvent extends BaseEvent {

        @Override
        public String toString() {
                return this.getClass().getSimpleName();
        }
}
