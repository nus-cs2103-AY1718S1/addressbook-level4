package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_BY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_ON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_AT;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.tasks.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DateTimeValidator;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.EventTime;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

///@@author raisa2010
/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    private static final int INDEX_START_TIME = 0;
    private static final int INDEX_END_TIME = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the AddTaskCommand
     * and returns an AddTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DEADLINE_ON, PREFIX_DEADLINE_BY, PREFIX_DEADLINE_FROM,
                        PREFIX_TIME_AT, PREFIX_TAG);

        if (!isDescriptionPresent(argMultimap) | !isSinglePrefixPresent(argMultimap)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        try {
            Description description = ParserUtil.parseDescription(argMultimap.getPreamble()).get();
            Deadline deadline = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE_BY,
                    PREFIX_DEADLINE_FROM, PREFIX_DEADLINE_ON))
                    .orElse(new Deadline(""));
            EventTime[] eventTimes = ParserUtil.parseEventTimes(argMultimap.getValue(PREFIX_TIME_AT))
                    .orElse(new EventTime[]{new EventTime(""), new EventTime("")});
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            if (!DateTimeValidator.isStartTimeBeforeEndTime(eventTimes[INDEX_START_TIME], eventTimes[INDEX_END_TIME])) {
                throw new IllegalValueException(DateTimeValidator.MESSAGE_TIME_CONSTRAINTS);
            }

            if (deadline.isEmpty() && eventTimes[INDEX_END_TIME].isPresent()) {
                deadline = ParserUtil.parseDeadline(Optional.of(new Date().toString())).get();
            }

            ReadOnlyTask task = new Task(description, deadline, eventTimes[INDEX_START_TIME],
                    eventTimes[INDEX_END_TIME], tagList);

            return new AddTaskCommand(task);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if the preamble (string before first valid prefix) is not empty in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isDescriptionPresent(ArgumentMultimap argumentMultimap) {
        return !argumentMultimap.getPreamble().isEmpty();
    }

    /**
     * Returns true if a single deadline prefix has been used in an unquoted string in the given
     * {@code ArgumentMultimap}
     */
    private static boolean isSinglePrefixPresent(ArgumentMultimap argumentMultimap) {
        return !(argumentMultimap.getValue(PREFIX_DEADLINE_BY).isPresent()
                && argumentMultimap.getValue(PREFIX_DEADLINE_ON).isPresent()
                && argumentMultimap.getValue(PREFIX_DEADLINE_FROM).isPresent());
    }
}
