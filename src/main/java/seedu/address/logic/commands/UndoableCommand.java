package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ListingUnit;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.module.predicates.ShowSpecifiedLessonPredicate;

import java.util.List;
import java.util.function.Predicate;


/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyAddressBook previousAddressBook;
    private Predicate previousPredicate;
    private ListingUnit previousListingUnit;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#addressBook}.
     */
    private void saveAddressBookSnapshot() {
        requireNonNull(model);
        this.previousAddressBook = new AddressBook(model.getAddressBook());
        this.previousPredicate = ListingUnit.getCurrentPredicate();
        this.previousListingUnit = ListingUnit.getCurrentListingUnit();
    }

    /**
     * Reverts the AddressBook to the state before this command
     * was executed and updates the filtered lesson list to
     * show all lessons.
     */
    protected final void undo() {
        requireAllNonNull(model, previousAddressBook);
        model.resetData(previousAddressBook);
        model.handleListingUnit();
    }

    /**
     * Redo the previous command only if predicates hasn't changed.
     */
    protected final void redo() {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
    }

    public boolean canRedo() {
        return previousListingUnit.equals(ListingUnit.getCurrentListingUnit()) &&
                previousPredicate.equals(ListingUnit.getCurrentPredicate());
    }


    @Override
    public final CommandResult execute() throws CommandException {
        saveAddressBookSnapshot();
        return executeUndoableCommand();
    }
}
