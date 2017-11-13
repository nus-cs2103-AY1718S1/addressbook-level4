package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.calendarevent.ReadOnlyCalendarEvent;

//@@author yilun-zhu
/**
 * A utility class for CalendarEvent.
 */
public class CalendarEventsUtil {

    /**
     * Returns an addEvent command string for adding the {@code event}.
     */
    public static String getAddEventCommand(ReadOnlyCalendarEvent event) {
        return AddEventCommand.COMMAND_WORD + " " + getEventDetails(event);
    }

    /**
     * Returns the part of command string for the given {@code event}'s details.
     */
    public static String getEventDetails(ReadOnlyCalendarEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + event.getEventName().toString() + " ");
        sb.append(PREFIX_EVENT_START_DATE + event.getStartDate().value + " ");
        sb.append(PREFIX_EVENT_START_TIME + event.getStartTime().value + " ");
        sb.append(PREFIX_EVENT_END_DATE + event.getEndDate().value + " ");
        sb.append(PREFIX_EVENT_END_TIME + event.getEndTime().value + " ");
        return sb.toString();
    }
}
