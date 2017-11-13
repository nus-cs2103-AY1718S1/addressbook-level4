// @@author tanchc
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

/**
 * Adds a person to the address book.
 */
public class AddTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtask";
    public static final String COMMAND_ALIAS = "at";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list. "
            + "Parameters: "
            + PREFIX_APPOINTMENT + "APPOINTMENT "
            + PREFIX_DATE + "DATE "
            + PREFIX_STARTTIME + "START_TIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_APPOINTMENT + "Meeting "
            + PREFIX_DATE + "25/11/2017 "
            + PREFIX_STARTTIME + "12:00 " + "\n"
            + "Example: " + COMMAND_ALIAS + " "
            + PREFIX_APPOINTMENT + "Meeting "
            + PREFIX_DATE + "25/11/2017 "
            + PREFIX_STARTTIME + "12:00 ";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";

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
        model.addTask(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));


    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && toAdd.equals(((AddTaskCommand) other).toAdd));
    }
}
