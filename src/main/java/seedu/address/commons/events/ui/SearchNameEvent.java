//@@author heiseish
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a facebook open request
 */
public class SearchNameEvent extends BaseEvent {


    private final String name;

    public SearchNameEvent(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getName() {
        return name;
    }
}
