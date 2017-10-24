package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of a meeting list
 */
public interface ReadOnlyMeetingList {

    /**
     * Returns an unmodifiable view of the meetings list.
     * This list will not contain any duplicate meetings.
     */
    ObservableList<Meeting> getMeetingList();
}
