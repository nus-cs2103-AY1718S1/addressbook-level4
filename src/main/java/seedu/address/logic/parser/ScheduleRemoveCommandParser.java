package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_INDEXES;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ScheduleRemoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author eldriclim
/**
 * Parses input arguments and creates a new ScheduleRemoveCommand object
 */
public class ScheduleRemoveCommandParser implements Parser<ScheduleRemoveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleRemoveCommand
     * and returns an ScheduleRemoveCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleRemoveCommand parse(String args) throws ParseException {
        args.trim();


        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_INDEXES);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_INDEXES)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ScheduleRemoveCommand.MESSAGE_USAGE));
        }


        try {

            ArrayList<Index> indexList = ParserUtil.parseIndexes(argMultimap.getValue(PREFIX_EVENT_INDEXES).get());
            Set<Index> uniqueEventIndexes = new HashSet<>(indexList);

            return new ScheduleRemoveCommand(uniqueEventIndexes);
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
