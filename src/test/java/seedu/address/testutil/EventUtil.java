package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEADER;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.person.event.ReadOnlyEvent;

//@@author royceljh
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
        sb.append(PREFIX_HEADER + event.getHeader().value + " ");
        sb.append(PREFIX_DESC + event.getDesc().value + " ");
        sb.append(PREFIX_EVENT_DATE + event.getEventDate().eventLocalDate.toString() + " ");
        return sb.toString();
    }
}
