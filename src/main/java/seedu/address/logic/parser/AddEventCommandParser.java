package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.googlecalendarutil.DateParserUtil;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.schedule.ReadOnlySchedule;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleDate;
import seedu.address.model.schedule.ScheduleName;

//@@author cjianhui
/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    public static final Prefix PREFIX_PERSON = new Prefix("p/");

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERSON, PREFIX_NAME,
                        PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_DETAILS);

        String scheduleDetails;

        if (!arePrefixesPresent(argMultimap, PREFIX_PERSON, PREFIX_NAME, PREFIX_START_DATE, PREFIX_END_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        try {
            Index personIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PERSON).get());
            ScheduleName name = ParserUtil.parseScheduleName(argMultimap.getValue(PREFIX_NAME)).get();
            ScheduleDate sDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_DATE)).get();
            ScheduleDate eDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_END_DATE)).get();
            scheduleDetails = ParserUtil.parseScheduleDetails(argMultimap.getValue(PREFIX_DETAILS)).get();

            if (!DateParserUtil.isAfterCurrentTime(sDate.scheduleDate)) {
                throw new ParseException(String.format(AddEventCommand.MESSAGE_INVALID_START_TIME,
                        DateParserUtil.getCurrentTime()));
            }

            if (!DateParserUtil.isValidTime(sDate.toString()) || !DateParserUtil.isValidTime(eDate.toString())) {
                throw new ParseException(AddEventCommand.MESSAGE_INVALID_TIME);
            }

            if (!DateParserUtil.isValidEventDuration(sDate.scheduleDate, eDate.scheduleDate)) {
                throw new ParseException(AddEventCommand.MESSAGE_INVALID_DURATION);
            }

            ReadOnlySchedule event = new Schedule(name, sDate, eDate,
                    DateParserUtil.getDurationOfEvent(sDate.toString(), eDate.toString()), scheduleDetails);

            return new AddEventCommand(personIndex, event);
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
