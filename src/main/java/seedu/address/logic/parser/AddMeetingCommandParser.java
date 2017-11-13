package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddMeetingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Meeting;
import seedu.address.model.ReadOnlyMeeting;
import seedu.address.model.person.InternalId;

//@@author Sri-vatsa
/**
 * Parses input arguments and creates a new AddMeetingCommand object
 */
public class AddMeetingCommandParser implements Parser<AddMeetingCommand>  {

    /**
     * Parses the given {@code String} of arguments in the context of the AddMeetingCommand
     * and returns an AddMeetingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMeetingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_TIME, PREFIX_LOCATION, PREFIX_NOTES,
                        PREFIX_PERSON);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_TIME, PREFIX_LOCATION, PREFIX_NOTES)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE));
        }

        try {
            String location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION)).get();
            String notes = ParserUtil.parseNotes(argMultimap.getValue(PREFIX_NOTES)).get();
            ArrayList<InternalId> idList = ParserUtil.parseIds(argMultimap.getAllValues(PREFIX_PERSON));

            String date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE)).get();
            String time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME)).get();
            LocalDateTime localDateTime = ParserUtil.parseDateTime(date, time);

            ReadOnlyMeeting meeting = new Meeting(localDateTime, location, notes, idList);

            return new AddMeetingCommand(meeting);
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
