//@@author heiseish
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a facebook open request
 */
public class ToggleFavoritePersonEvent extends BaseEvent {

    private final String id;

    public ToggleFavoritePersonEvent(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getId() {
        return id;
    }
}
