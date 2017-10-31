package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;

import java.util.Optional;
import java.util.stream.Stream;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AbortedCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
// @@author HuWanqing
/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME, PREFIX_EVENT_DESCRIPTION,
                        PREFIX_EVENT_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_NAME, PREFIX_EVENT_DESCRIPTION, PREFIX_EVENT_TIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddEventCommand.MESSAGE_USAGE));
        }

        try {
            EventName name = ParserUtil.parseEventName(argMultimap.getValue(PREFIX_EVENT_NAME)).get();
            EventDescription description = ParserUtil.parseEventDescription(
                    argMultimap.getValue(PREFIX_EVENT_DESCRIPTION)).get();
            EventTime time = ParserUtil.parseEventTime(argMultimap.getValue(PREFIX_EVENT_TIME)).get();

            Event event = new Event(name, description, time);

            if (time.getDays() < 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("This event date is outdated");
                alert.setContentText("Do you still want to add this event to Planno?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    return new AddEventCommand(event);
                } else {
                    return new AbortedCommand(event);
                }
            }

            return new AddEventCommand(event);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
