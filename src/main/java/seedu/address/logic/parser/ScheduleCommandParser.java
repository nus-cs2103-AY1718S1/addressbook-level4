package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTIVITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.schedule.Activity;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleDate;

/**
 * Parses input arguments and creates a new HelpCommand object
 */
public class ScheduleCommandParser implements Parser<ScheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleCommand
     * and returns an ScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ScheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_ACTIVITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_ACTIVITY) || !isPreamblePresent(argMultimap)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }

        try {
            String indicesOneString = argMultimap.getPreamble();
            String[] indicesInString = indicesOneString.split("\\s+");

            Set<Index> indices = new HashSet<>();

            for (String indexString : indicesInString) {
                Index index = ParserUtil.parseIndex(indexString);
                indices.add(index);
            }

            ScheduleDate date = ParserUtil.parseScheduleDate(argMultimap.getValue(PREFIX_DATE)).get();
            Activity activity = ParserUtil.parseActivity(argMultimap.getValue(PREFIX_ACTIVITY)).get();

            Schedule schedule = new Schedule(date, activity);

            return new ScheduleCommand(indices, schedule);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    private boolean isPreamblePresent(ArgumentMultimap argMultimap) {
        // checks whether preamble is present
        // whether the index is a non-zero unsigned integer is taken care of by ParserUtil.parseIndex
        return !argMultimap.getPreamble().equals("");
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
