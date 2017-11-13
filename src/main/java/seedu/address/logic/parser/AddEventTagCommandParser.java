package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSONS;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddEventTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.schedule.Day;
import seedu.address.model.schedule.Time;
import seedu.address.model.tag.Tag;

import java.util.stream.Stream;

public class AddEventTagCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the AddEventTagCommand
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventTagCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DAY, PREFIX_START_TIME,
                PREFIX_END_TIME, PREFIX_LOC, PREFIX_PERSONS);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DAY, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_LOC, PREFIX_PERSONS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventTagCommand.MESSAGE_USAGE));
        }

        try {
            String name = argMultimap.getValue(PREFIX_NAME).get();
            String location = argMultimap.getValue(PREFIX_LOC).get();
            Day day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY)).get();
            Time startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME)).get();
            Time endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_END_TIME)).get();
            String indices = argMultimap.getValue(PREFIX_PERSONS).get();
            Index[] listIndex = ParserUtil.parseIndexArr(indices);
            String eventStr = name + "on" + day.toString() + "at" + location;
            Tag event = new Tag(eventStr);
            return new AddEventTagCommand(day, startTime, endTime, listIndex, event);
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
