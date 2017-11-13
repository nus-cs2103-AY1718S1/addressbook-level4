package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.joestelmach.natty.DateGroup;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author limcel
/**
 * Parses input arguments and creates a new ScheduleCommand object
 */
public class ScheduleCommandParser implements Parser<ScheduleCommand> {

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private List<DateGroup> scheduleToParse;

    /**t
     * Parses the given {@code String} of arguments in the context of the ScheduleCommand
     * and returns an ScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SCHEDULE);
        Index index;

        try {
            args = args.trim();
            String[] str = args.split(" ");
            // string length should be at least of length three comprising of command, index and scheduled date,time:
            // e.g. Schedule, 1, d/28October2019 3pm
            if (str.length == 0 || str.length < 3) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
            // it is a valid Command
            } else if (str.length >= 3 && isNumeric(str[0])) {
                index = ParserUtil.parseIndex(argMultimap.getPreamble());
                ScheduleCommand scheduleCommand = onlyReturnsSchedulesWithSpecificDate(index, argMultimap);
                return scheduleCommand;
            }
        } catch (IllegalValueException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
    }

    /**
     * Returns true if the string contains numbers, false otherwise
     */
    private static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Checks if date given by user is specific
     * @return ScheduleCommand if date given is specific
     * @throws ParseException if date given is not specific enough
     */
    private ScheduleCommand onlyReturnsSchedulesWithSpecificDate(Index index, ArgumentMultimap argMultimap)
            throws ParseException {
        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
        scheduleToParse = parser.parse(argMultimap.getValue(PREFIX_SCHEDULE).get());
        // Natty returns no date or with more than one date (date entered not specific enough) --> throw exception
        if (scheduleToParse.isEmpty() || scheduleToParse.size() > 1) {
            throw new ParseException("Please enter a more specific date for your student's consultation.");
        }
        Calendar date = Calendar.getInstance();
        date.setTime(scheduleToParse.get(0).getDates().get(0));
        return new ScheduleCommand(index, date);
    }
}
