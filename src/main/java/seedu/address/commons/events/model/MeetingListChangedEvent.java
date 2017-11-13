package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyMeetingList;

//@@author Sri-vatsa
/**
 *  Indicates the MeetingList in the model has changed
 */
public class MeetingListChangedEvent extends BaseEvent {

    public final ReadOnlyMeetingList meetingData;

    public MeetingListChangedEvent(ReadOnlyMeetingList meetingList) {
        this.meetingData = meetingList;
    }

    @Override
    public String toString() {
        return "Number of Meetings: " + meetingData.getMeetingList().size();
    }
}
