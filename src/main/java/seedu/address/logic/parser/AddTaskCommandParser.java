package seedu.address.logic.parser;
//@@author Esilocke
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskAddress;
import seedu.address.model.task.TaskName;

/**
 * Parses input arguments and creates a new ReadOnlyTask object in the context of AddTaskCommand.
 */
public class AddTaskCommandParser extends AddCommandParser {
    /**
     * Constructs a ReadOnlyTask from the arguments provided.
     */
    public static ReadOnlyTask constructTask(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DESCRIPTION, PREFIX_DEADLINE, PREFIX_PRIORITY,
                        PREFIX_ADDRESS, PREFIX_TASK);

        if (!(arePrefixesPresent(argMultimap, PREFIX_NAME))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        try {
            TaskName name;
            Description description;
            Deadline deadline;
            Priority priority;
            TaskAddress address;

            name = ParserUtil.parseTaskName(argMultimap.getValue(PREFIX_NAME)).get();

            description = arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION)
                    ? ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION)).get()
                    : new Description(null);

            deadline = arePrefixesPresent(argMultimap, PREFIX_DEADLINE)
                    ? ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE)).get()
                    : new Deadline(null);
            priority = arePrefixesPresent(argMultimap, PREFIX_PRIORITY)
                    ? ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY)).get()
                    : new Priority(null);

            address = arePrefixesPresent(argMultimap, PREFIX_ADDRESS)
                    ? ParserUtil.parseTaskAddress(argMultimap.getValue(PREFIX_ADDRESS)).get()
                    : new TaskAddress(null);

            return new Task(name, description, deadline, priority, address);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
