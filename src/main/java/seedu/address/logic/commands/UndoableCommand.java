package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BLACKLISTED_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WHITELISTED_PERSONS;

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
                switch (model.getCurrentList()) {
                case "blacklist":
                    if (model.getFilteredBlacklistedPersonList().contains(selectedPerson)) {
                        this.index = Index.fromZeroBased(model.getFilteredBlacklistedPersonList()
                                .indexOf(selectedPerson));
                    } else {
                        this.index = null;
                    }
                    break;
                case "whitelist":
                    if (model.getFilteredWhitelistedPersonList().contains(selectedPerson)) {
                        this.index = Index.fromZeroBased(model.getFilteredWhitelistedPersonList()
                                .indexOf(selectedPerson));
                    } else {
                        this.index = null;
                    }
                    break;
                default:
                    if (model.getFilteredPersonList().contains(selectedPerson)) {
                        this.index = Index.fromZeroBased(model.getFilteredPersonList().indexOf(selectedPerson));
                    } else {
                        this.index = null;
                    }
                }
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
        updateCurrentDisplayedList(model.getCurrentList());
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
        updateCurrentDisplayedList(model.getCurrentList());
    }

    /**
     * Updates the current list in Person List Panel to reflect latest changes done.
     *
     * @param currentList cannot be null.
     */
    private void updateCurrentDisplayedList(String currentList) {

        switch (currentList) {

        case "blacklist":
            model.updateFilteredBlacklistedPersonList(PREDICATE_SHOW_ALL_BLACKLISTED_PERSONS);
            break;
        case "whitelist":
            model.updateFilteredWhitelistedPersonList(PREDICATE_SHOW_ALL_WHITELISTED_PERSONS);
            break;

        default:
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveAddressBookSnapshot();
        return executeUndoableCommand();
    }
}
