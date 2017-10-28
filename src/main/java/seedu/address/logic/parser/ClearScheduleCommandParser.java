package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ClearScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.schedule.Day;
import seedu.address.model.schedule.Time;

/**
 *
 * Parses input arguments and creates a new ClearScheduleCommand object
 */
public class ClearScheduleCommandParser implements Parser<ClearScheduleCommand> {



    /**
     * Parses the given {@code String} of arguments in the context of the ClearScheduleCommand
     * and returns an ClearScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClearScheduleCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DAY, PREFIX_START_TIME,
                PREFIX_END_TIME);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearScheduleCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_START_TIME, PREFIX_END_TIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearScheduleCommand.MESSAGE_USAGE));
        }

        try {
            Day day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY)).get();
            Time startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME)).get();
            Time endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_END_TIME)).get();
            return new ClearScheduleCommand(index, day, startTime, endTime);
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


