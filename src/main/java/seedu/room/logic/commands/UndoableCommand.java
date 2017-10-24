package seedu.room.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.room.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.room.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.model.RoomBook;
import seedu.room.model.ReadOnlyRoomBook;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyRoomBook previousRoomBook;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#roomBook}.
     */
    private void saveRoomBookSnapshot() {
        requireNonNull(model);
        this.previousRoomBook = new RoomBook(model.getRoomBook());
    }

    /**
     * Reverts the RoomBook to the state before this command
     * was executed and updates the filtered person list to
     * show all persons.
     */
    protected final void undo() {
        requireAllNonNull(model, previousRoomBook);
        model.resetData(previousRoomBook);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    /**
     * Executes the command and updates the filtered person
     * list to show all persons.
     */
    protected final void redo() {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveRoomBookSnapshot();
        return executeUndoableCommand();
    }
}
