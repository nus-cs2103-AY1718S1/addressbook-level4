//@@author ShaocongDong
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.TaskBook;

/**
 * Clears the address book.
 */
public class ClearTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clearTask";
    public static final String COMMAND_ALIAS = "ct";
    public static final String MESSAGE_SUCCESS = "Task book has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new TaskBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
