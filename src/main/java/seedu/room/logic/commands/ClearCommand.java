package seedu.room.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.room.model.RoomBook;

/**
 * Clears the room book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_ALIAS = "c";
    public static final String MESSAGE_SUCCESS = "Room book has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new RoomBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
