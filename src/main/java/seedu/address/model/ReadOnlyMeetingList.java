package seedu.address.model;

import javafx.collections.ObservableList;
//@@author liuhang0213
/**
 * Unmodifiable view of a meeting list
 */
public interface ReadOnlyMeetingList {

    /**
     * Returns an unmodifiable view of the meetings list.
     * This list will not contain any duplicate meetings.
     */
    ObservableList<ReadOnlyMeeting> getMeetingList();

    /**
     * Returns the next upcoming meeting
     * This is required for nextMeeting command
     */
    ReadOnlyMeeting getUpcomingMeeting();
}
