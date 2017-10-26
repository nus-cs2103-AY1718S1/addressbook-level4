package seedu.address.commons.util;

import java.time.Duration;
import java.util.ArrayList;

import seedu.address.model.event.EventDuration;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.model.event.MemberList;
import seedu.address.model.person.ReadOnlyPerson;


/**
 * Utility method to output Event attributes in human-readable time
 */
public class EventOutputUtil {

    /**
     * Takes in a Duration and outputs it hours and minutes
     *
     * @param d1 given duration of Event, guaranteed to be less than 23hr59min
     * @return a String of Duration in human-readable form
     */
    public static String toStringDuration(Duration d1) {
        //Assert event duration not more than 23hr 59min
        assert d1.getSeconds() < 86340;

        StringBuilder sb = new StringBuilder();

        int totalSeconds = (int) d1.getSeconds();
        int hoursOutput = totalSeconds / (60 * 60);
        int minutesOutput = (totalSeconds % (60 * 60)) / 60;

        if (hoursOutput > 0) {
            sb.append(hoursOutput + "hr");
        }

        if (minutesOutput > 0) {
            sb.append(minutesOutput + "min");
        }

        if (hoursOutput == 0 && minutesOutput == 0) {
            sb.append(minutesOutput + "min");
        }


        return sb.toString();

    }

    /**
     * Takes in a list of members and output their names in readable form
     *
     * @param members
     * @return a String with members name separated by commas
     */
    public static String toStringMembers(ArrayList<ReadOnlyPerson> members) {
        if (members.isEmpty()) {
            return "none";
        }

        StringBuilder sb = new StringBuilder();

        for (ReadOnlyPerson p : members) {
            sb.append(p.getName().fullName);
            sb.append(", ");
        }

        //Remove trailing ", "
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    /**
     * Outputs an Event details in readable form
     *
     * @param eventName
     * @param eventTime
     * @param eventDuration
     * @param memberList
     * @return
     */
    public static String toStringEvent(EventName eventName, EventTime eventTime,
                                       EventDuration eventDuration, MemberList memberList) {
        return eventName.toString() + " Time: " + eventTime.toString()
                + " Duration: " + eventDuration.toString() + "\n"
                + "Members: " + memberList.toString();
    }
}
