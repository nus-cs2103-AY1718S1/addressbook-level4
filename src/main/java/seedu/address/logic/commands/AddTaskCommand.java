package seedu.address.logic.commands;
//@@author Esilocke
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;

/**
 * Adds a task to the address book.
 */
public class AddTaskCommand extends AddCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_TASK
            + ": Adds a task to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_DEADLINE + "DEADLINE "
            + PREFIX_PRIORITY + "PRIORITY "
            + PREFIX_ADDRESS + "ADDRESS"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TASK + " "
            + PREFIX_NAME + "Buy pencil "
            + PREFIX_DESCRIPTION + "Buy a new pencil from ABS "
            + PREFIX_DEADLINE + "10-10-2017 "
            + PREFIX_PRIORITY + "4 "
            + PREFIX_ADDRESS + "12 Kent Ridge Crescent, 119275";
    public static final String MESSAGE_SUCCESS = "New task added: \n%1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book";

    private final Task taskToAdd;

    /**
     * Creates an AddTaskCommand to add the specified {@code ReadOnlyTask}
     */
    public AddTaskCommand(ReadOnlyTask task) {
        super();
        taskToAdd = new Task(task);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addTask(taskToAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, taskToAdd));
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof AddTaskCommand)) {
            return false;
        } else {
            assert (taskToAdd != null);
            assert (((AddTaskCommand) other).taskToAdd != null); // The taskToAdd cannot be null
            return taskToAdd.equals(((AddTaskCommand) other).taskToAdd);
        }
    }
}
