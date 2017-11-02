package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyAddressBook previousAddressBook;
    private ReadOnlyPerson selectedPerson;
    private Index index;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#addressBook}.
     */
    private void saveAddressBookSnapshot() {
        requireNonNull(model);
        if (model.getSelectedPerson() != null) {
            this.selectedPerson = new Person(model.getSelectedPerson());
            if (model.getAllPersons().size() > 0) {
                // if the command is not "clear"
                this.index = listObserver.getIndexofPersonInCurrentList(selectedPerson);
            }
        }
        this.previousAddressBook = new AddressBook(model.getAddressBook());
    }

    /**
     * Reverts the AddressBook to the state before this command
     * was executed and updates the filtered person list to
     * show all persons.
     */
    protected final void undo() throws CommandException {
        requireAllNonNull(model, previousAddressBook);
        model.resetData(previousAddressBook);
        listObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS);
        if (index != null) {
            EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
        }
    }

    /**
     * Executes the command and updates the filtered person
     * list to show all persons.
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
        listObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveAddressBookSnapshot();
        return executeUndoableCommand();
    }
}
