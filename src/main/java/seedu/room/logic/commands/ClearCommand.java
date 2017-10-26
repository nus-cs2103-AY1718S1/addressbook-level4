package seedu.room.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.room.model.ResidentBook;

/**
 * Clears the resident book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_ALIAS = "c";
    public static final String MESSAGE_SUCCESS = "Resident book has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new ResidentBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
