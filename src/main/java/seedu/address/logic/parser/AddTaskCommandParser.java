package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.StartDate;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTaskCommand
     * and returns an AddTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STARTING_DATE, PREFIX_DEADLINE);
        
        if (!arePrefixesPresent(argMultimap, PREFIX_STARTING_DATE, PREFIX_DEADLINE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Description description = ParserUtil.parseDescription(argMultimap.getPreamble());
            StartDate startDate = new StartDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_STARTING_DATE)).get());
            Deadline deadline = new Deadline(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DEADLINE)).get());

            ReadOnlyTask task = new Person(name, phone, email, address, tagList);

            return new AddTaskCommand(task);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if the start date prefix does not contain empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isDescriptionPresent(ArgumentMultimap argumentMultimap) {
        return argumentMultimap.getValue(PREFIX_STARTING_DATE).isPresent();
    }

    /**
     * Returns true if the start date prefix does not contain empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isStartDatePresent(ArgumentMultimap argumentMultimap) {
        return argumentMultimap.getValue(PREFIX_STARTING_DATE).isPresent();
    }

    /**
     * Returns true if the deadline prefix does not contain empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isDeadlinePresent(ArgumentMultimap argumentMultimap) {
        return argumentMultimap.getValue(PREFIX_DEADLINE).isPresent();
    }

}
