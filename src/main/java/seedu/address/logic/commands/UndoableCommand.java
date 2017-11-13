package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.ListObserver;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyAddressBook previousAddressBook;
    private ReadOnlyPerson lastSelectedPerson;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#addressBook}.
     */
    private void saveAddressBookSnapshot() {
        requireNonNull(model);
        this.previousAddressBook = new AddressBook(model.getAddressBook());
        lastSelectedPerson = model.getSelectedPerson();
    }

    /**
     * Reverts the {@code model#addressBook} to the state before this command was executed and updates the filtered
     * person list to show all persons. Also reselects the person selected prior to the execution of the command to be
     * undone.
     */
    protected final void undo() throws CommandException {
        requireAllNonNull(model, previousAddressBook);
        model.resetData(previousAddressBook);
        ListObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS);
        if (ListObserver.getCurrentFilteredList().contains(lastSelectedPerson)) {
            EventsCenter.getInstance().post(new
                    JumpToListRequestEvent(ListObserver.getIndexOfPersonInCurrentList(lastSelectedPerson)));
        }
    }

    /**
     * Executes the command and deselects any selected person.
     */
    protected final void redo() throws CommandException {
        requireNonNull(model);
        saveAddressBookSnapshot();
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveAddressBookSnapshot();
        return executeUndoableCommand();
    }
}
