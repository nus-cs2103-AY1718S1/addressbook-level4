package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEETINGS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyAddressBook previousAddressBook;
    private ReadOnlyAddressBook addressBookAfterExecution;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#addressBook}.
     */
    private void saveAddressBookSnapshot() {
        requireNonNull(model);
        this.previousAddressBook = new AddressBook(model.getAddressBook());
    }

    /**
     * Reverts the AddressBook to the state before this command
     * was executed and updates the filtered person list and
     * filtered meeting list to show all persons and meetings.
     */
    protected final void undo() {
        requireAllNonNull(model, previousAddressBook);
        this.addressBookAfterExecution = new AddressBook(model.getAddressBook());
        model.resetData(previousAddressBook);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.sortMeeting();
        model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
    }

    /**
     * Executes the command and updates the filtered person
     * list and filtered meeting list to show all persons
     * and meetings.
     */
    protected final void redo() {
        requireAllNonNull(model, addressBookAfterExecution);
        model.resetData(addressBookAfterExecution);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.sortMeeting();
        model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveAddressBookSnapshot();
        return executeUndoableCommand();
    }
}
