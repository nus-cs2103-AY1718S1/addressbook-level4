package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUEDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;

//@@author zhangshuoyang
/**
 * Adds a task to the address book.
 */
public class AddTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "task";

    public static final String AUTOCOMPLETE_FORMAT = COMMAND_WORD + " "
            + PREFIX_DESCIPTION + " "
            + PREFIX_PRIORITY + " "
            + PREFIX_DUEDATE;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the address book."
            + "Parameters: "
            + PREFIX_DESCIPTION + "DESCRIPTION "
            + PREFIX_PRIORITY + "PRIORITY(0/1/2) "
            + PREFIX_DUEDATE + "DUE DATE(dd/MM/yyyy) \n"
            + "EXAMPLE: " + COMMAND_WORD + " "
            + PREFIX_DESCIPTION + "CS ASSIGNMENT "
            + PREFIX_PRIORITY + "2 "
            + PREFIX_DUEDATE + "25/10/2017";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

    private final Task toAdd;

    /**
     * Creates an AddTaskCommand to add the specified {@code ReadOnlyTask}
     */
    public AddTaskCommand(ReadOnlyTask task) {
        toAdd = new Task(task);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && toAdd.equals(((AddTaskCommand) other).toAdd));
    }

}





