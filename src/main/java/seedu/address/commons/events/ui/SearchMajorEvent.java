//@@author heiseish
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a facebook open request
 */
public class SearchMajorEvent extends BaseEvent {


    private final String major;

    public SearchMajorEvent(String major) {
        this.major = major;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getMajor() {
        return major;
    }
}
