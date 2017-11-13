package seedu.address.commons.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.stream.Collectors;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventDuration;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.model.event.MemberList;
import seedu.address.model.person.ReadOnlyPerson;

//@@author eldriclim

/**
 * Utility method to output Event attributes in human-readable time
 */
public class EventOutputUtil {

    /**
     * Returns a Duration in String format, in days, hours and minutes
     *
     * @param duration given duration of Event
     * @return a String of Duration in human-readable form
     */
    public static String toStringDuration(Duration duration) {

        StringBuilder sb = new StringBuilder();

        int totalSeconds = (int) duration.getSeconds();
        int daysOutput = totalSeconds / (60 * 60 * 24);
        int hoursOutput = (totalSeconds % (60 * 60 * 24)) / (60 * 60);
        int minutesOutput = (totalSeconds % (60 * 60)) / 60;

        if (daysOutput > 0) {
            sb.append(daysOutput + "day");
        }

        if (hoursOutput > 0) {
            sb.append(hoursOutput + "hr");
        }

        if (minutesOutput > 0) {
            sb.append(minutesOutput + "min");
        }

        if (daysOutput == 0 && hoursOutput == 0 && minutesOutput == 0) {
            sb.append(0 + "min");
        }

        return sb.toString();

    }

    /**
     * Returns a String representation of a list of members's name.
     *
     * @param members a list of members
     * @return String with members name separated by commas
     */
    public static String toStringMembers(ArrayList<ReadOnlyPerson> members) {
        if (members.isEmpty()) {
            return "none";
        }

        ArrayList<String> memberNames = new ArrayList<>(
                members.stream().map(p -> p.getName().toString()).collect(Collectors.toList()));

        return StringUtil.multiStringPrint(memberNames, ", ");
    }

    /**
     * Returns a String representation of an Event.
     *
     * @param eventName the name of an Event
     * @param eventTime the time of an Event
     * @param eventDuration the duration of an Event
     * @param memberList the list of members of an Event
     * @return String with details of an Event
     * @see Event#toString()
     */
    public static String toStringEvent(EventName eventName, EventTime eventTime,
                                       EventDuration eventDuration, MemberList memberList) {

        ArrayList<ReadOnlyPerson> members = new ArrayList<>(memberList.asReadOnlyMemberList());

        return "Name: " + eventName.toString() + " Time: " + eventTime.toString()
                + " Duration: " + eventDuration.toString() + "\n"
                + "Members: " + toStringMembers(members);
    }
}
