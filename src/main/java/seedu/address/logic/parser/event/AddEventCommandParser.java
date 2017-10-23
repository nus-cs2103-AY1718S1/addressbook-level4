package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESLOT;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.Title;
import seedu.address.model.event.timeslot.Timeslot;

/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Returns true if none of th
     * e prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TIMESLOT, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_TIMESLOT, PREFIX_DESCRIPTION)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        try {
            Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_NAME)).get();
            Timeslot timeslot = ParserUtil.parseTimeslot(argMultimap.getValue(PREFIX_TIMESLOT)).get();
            Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION)).get();

            ReadOnlyEvent event = new Event(title, timeslot, description);

            return new AddEventCommand(event);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

}
