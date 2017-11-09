package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.room.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.room.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.room.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.room.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.logic.commands.AddEventCommand;
import seedu.room.logic.parser.exceptions.ParseException;
import seedu.room.model.event.Datetime;
import seedu.room.model.event.Description;
import seedu.room.model.event.Event;
import seedu.room.model.event.Location;
import seedu.room.model.event.ReadOnlyEvent;
import seedu.room.model.event.Title;


//@@author sushinoya
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {

        Title title;
        Location location;
        Description description;
        Datetime datetime;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_LOCATION, PREFIX_DESCRIPTION, PREFIX_DATETIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_LOCATION, PREFIX_DATETIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        try {
            //Gets the title of the event being added
            title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE)).get();

            //Gets the location of the event being added. If no location is provided, it creates a location with null
            // as value
            Optional<Location> optionalLocation = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION));
            if (optionalLocation.isPresent()) {
                location = optionalLocation.get();
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
            }

            //Gets the description of the event being added. If no description is provided, it creates an description
            // with null as value
            Optional<Description> optionalDescription = ParserUtil.parseDescription(
                                                                            argMultimap.getValue(PREFIX_DESCRIPTION));
            if (optionalDescription.isPresent()) {
                description = optionalDescription.get();
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
            }

            //Gets the datetime of the event being added. If no datetime is provided, it creates an datetime with null
            //as value
            Optional<Datetime> optionalDatetime = ParserUtil.parseDatetime(argMultimap.getValue(PREFIX_DATETIME));
            if (optionalDatetime.isPresent()) {
                datetime = optionalDatetime.get();
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
            }

            ReadOnlyEvent event = new Event(title, description, location, datetime);

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
