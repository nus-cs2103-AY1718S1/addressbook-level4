package seedu.room.testutil;

import static seedu.room.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.room.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.room.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.room.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.room.logic.commands.AddEventCommand;
import seedu.room.model.event.ReadOnlyEvent;

//@@author sushinoya
/**
 * A utility class for Event.
 */
public class EventUtil {

    /**
     * Returns an add command string for adding the {@code event}.
     */
    public static String getAddEventCommand(ReadOnlyEvent event) {
        return AddEventCommand.COMMAND_WORD + " " + getEventDetails(event);
    }

    /**
     * Returns the part of command string for the given {@code event}'s details.
     */
    public static String getEventDetails(ReadOnlyEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + event.getTitle().value + " ");
        sb.append(PREFIX_LOCATION + event.getLocation().value + " ");
        sb.append(PREFIX_DESCRIPTION + event.getDescription().value + " ");
        sb.append(PREFIX_DATETIME + event.getDatetime().value + " ");

        return sb.toString();
    }

    public static String getEventDetailsForEdit(ReadOnlyEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + event.getTitle().value + " ");
        sb.append(PREFIX_LOCATION + event.getLocation().value + " ");
        sb.append(PREFIX_DESCRIPTION + event.getDescription().value + " ");
        sb.append(PREFIX_DATETIME + event.getDatetime().value + " ");

        return sb.toString();
    }
}
