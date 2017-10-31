package seedu.address.commons.events.model;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents an event where the user has changed between person mode and task mode
 */
public class ModelToggleEvent extends BaseEvent {

    /**
     * Toggle markers for each mode in the application
     */
    public enum Toggle {
        personEnabled,
        taskEnabled
    }

    private final Toggle toggle;

    public ModelToggleEvent(Toggle toggle) {
        requireNonNull(toggle);

        this.toggle = toggle;
    }

    @Override
    public String toString() {
        return "Toggle action: " + toggle.toString();
    }

    public Toggle getToggle() {
        return this.toggle;
    }
}
