package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_MEMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ScheduleAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventDuration;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;

//@@author eldriclim
/**
 * Parses input arguments and creates a new ScheduleAddCommand object
 */
public class ScheduleAddCommandParser implements Parser<ScheduleAddCommand> {

    static final String ERROR_INVALID_DATE = "Invalid date detected.";
    static final String ERROR_PARSING_DURATION = "Duration has to be in the following format: #d#h#m\n"
            + "d:day, h:hour, m:minute";

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleAddCommand
     * and returns an ScheduleAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleAddCommand parse(String args) throws ParseException {
        args.trim();
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME, PREFIX_EVENT_TIME,
                        PREFIX_EVENT_DURATION, PREFIX_EVENT_MEMBER);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_NAME, PREFIX_EVENT_TIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleAddCommand.MESSAGE_USAGE));
        }


        try {
            String durationInput = argMultimap.getValue(PREFIX_EVENT_DURATION).isPresent()
                    ? argMultimap.getValue(PREFIX_EVENT_DURATION).get().trim()
                    : "";

            if (!durationInput.matches("^|((?!$)(?:(\\d+)d)?(?:(\\d+)h)?(?:(\\d+)m)?)$")) {
                throw new ParseException(ERROR_PARSING_DURATION);
            }

            EventName eventName = ParserUtil.parseEventName(argMultimap.getValue(PREFIX_EVENT_NAME)).get();



            EventTime eventTime = ParserUtil.parseEventTime(
                    argMultimap.getValue(PREFIX_EVENT_TIME), durationInput).get();

            EventDuration eventDuration = ParserUtil.parseEventDuration(
                    durationInput);


            ArrayList<Index> indexList = argMultimap.getValue(PREFIX_EVENT_MEMBER).isPresent()
                    ? ParserUtil.parseIndexes(argMultimap.getValue(PREFIX_EVENT_MEMBER).get())
                    : new ArrayList<>();

            Set<Index> uniqueMemberIndexes = new HashSet<>(indexList);

            return new ScheduleAddCommand(eventName, eventTime, eventDuration, uniqueMemberIndexes);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        } catch (DateTimeParseException dve) {
            throw new ParseException(ERROR_INVALID_DATE);
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
