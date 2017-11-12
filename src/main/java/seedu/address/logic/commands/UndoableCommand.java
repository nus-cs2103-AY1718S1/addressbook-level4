package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PARCELS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyAddressBook previousAddressBook;
    private boolean previousActiveListIsAll;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    //@@author fustilio
    /**
     * Stores the current state of {@code model#addressBook}.
     */
    private void saveAddressBookSnapshot() {
        requireNonNull(model);
        this.previousAddressBook = new AddressBook(model.getAddressBook());
        this.previousActiveListIsAll = model.getActiveIsAllBool();
    }

    /**
     * Reverts the AddressBook to the state before this command
     * was executed and updates the filtered parcel list to
     * show all parcels.
     */
    protected final void undo() {
        requireAllNonNull(model, previousAddressBook);
        model.resetData(previousAddressBook);
        model.updateFilteredParcelList(PREDICATE_SHOW_ALL_PARCELS);
        if (previousActiveListIsAll) {
            model.uiJumpToTabAll();
        } else {
            model.uiJumpToTabCompleted();
        }
    }

    /**
     * Executes the command and updates the filtered parcel
     * list to show all parcels.
     */
    protected final void redo() {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }

        model.updateFilteredParcelList(PREDICATE_SHOW_ALL_PARCELS);
    }
    //@@author

    @Override
    public final CommandResult execute() throws CommandException {
        saveAddressBookSnapshot();
        return executeUndoableCommand();
    }
}
