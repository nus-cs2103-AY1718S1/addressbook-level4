package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates the User Preference has changed
 */
public class PrefChangedEvent extends BaseEvent {

    public final String key;
    public final String oldValue;
    public final String newValue;

    public PrefChangedEvent(String key, String oldValue, String newValue) {
        this.key = key;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public String toString() {
        return String.format("Preference %1$s has been changed from '%2$s' to '%3$s'.",
                key, oldValue, newValue);
    }
}
