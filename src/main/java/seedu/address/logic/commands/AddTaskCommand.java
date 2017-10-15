package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.Task;

public class AddTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + "DESCRIPTION "
            + PREFIX_START_DATE + "START DATE(dd-MM-yy) "
            + PREFIX_DEADLINE + "DEADLINE DATE(dd-MM-yy) ";
    
    public static final String MESSAGE_SUCCESS = "Task has been added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book";
    
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
